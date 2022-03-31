package pages;

import com.codeborne.selenide.*;
import driver.DriverManager;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class ListPage extends BasePage {
    By priceInput = By.xpath("//input[contains(@class, 'small__input_list')]");
    By submitButton = By.xpath("//button[contains(@class, 'left-filters__button')]");
    By mainContainer = By.className("products-list__content");
    By listContainer = By.xpath("//div[contains(@class, 'catalog-products')]");
    By listItem = By.xpath("//div[@data-id='product']");
    By priceItem = By.className("product-buy__price");

    By outOfStockInput = By.xpath("//input[@value='out_of_stock']");
    By brandContainer = By.xpath("//div[@data-id='brand']");
    By showAllBrandsLink = By.xpath("//a[contains(@class, 'ui-list-controls__link_fold')]");
    By menuBlockTitle = By.xpath("//span[@class='ui-collapse__link-text']");
    By menuOptionsContainer = By.className("ui-list-controls__content");
    By productNameItem = By.xpath(".//a[contains(@class, 'catalog-product__name')]");
    By wishListBtn = By.xpath(".//button[contains(@class, 'wishlist-btn')]");
    By wishListModalContainer = By.className("wishlist-login-modal__buttons");
    By closeModalLink = By.xpath(".//a[contains(@class, 'ui-link')]");
    By wishListPageLink = By.xpath("//a[contains(@class, 'wishlist-link')]");

    public ListPage() {
        super("");
    }

    public ListPage setMaxPrice(int price) {
        $(priceInput).should(Condition.exist).shouldBe(Condition.visible).shouldHave(Condition.attribute("placeholder"));
        for (var in : $$(priceInput)) {
            var pAttr = in.attr("placeholder");
            if (pAttr != null && pAttr.contains("до")) {
                actions().moveToElement(in).perform();
                in.sendKeys(String.valueOf(price));
            }
        }
        return this;
    }

    public ListPage submitFilter() {
        var btn = $(submitButton).should(Condition.exist);
        actions().moveToElement(btn).perform();
        btn.shouldBe(Condition.visible).click();
        Selenide.refresh();
        return this;
    }

    public boolean checkPriceValues(int min, int max) {
        int page = 1;
        while (!$(mainContainer).should(Condition.exist).findElements(listContainer).isEmpty()) {
            Selenide.refresh();
            for (var li : $$(listItem)) {
                var priceEl = li.$(priceItem).shouldBe(Condition.visible);
                var price = parsePrice(priceEl.getText());
                if (price < min || price > max) return false;
            }

            setPage(++page);
        }

        return true;
    }

    public ListPage enableOutOfStock() {
        var inputEl = $(outOfStockInput).should(Condition.exist);
        var boxEl = inputEl.parent().$(By.className("ui-checkbox__box")).should(Condition.exist).shouldBe(Condition.visible);

        boxEl.click();

        return this;
    }

    public ListPage selectBrand(String value) {
        var linkEl = $(showAllBrandsLink).should(Condition.exist, Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
        linkEl.scrollIntoView(false).click();
        linkEl.shouldHave(Condition.attribute("data-expanded"));

        var container = $(brandContainer).should(Condition.exist).scrollIntoView(true);
        var sonyInputEl = container.$(By.xpath(String.format(".//input[@value='%s']", value))).should(Condition.exist);

        for (var spanEl : sonyInputEl.parent().$$(By.tagName("span"))) {
            var classAttr = spanEl.attr("class");
            if (classAttr != null && classAttr.isEmpty()) {
                spanEl.scrollIntoView(true).shouldBe(Condition.visible).shouldBe(Condition.enabled).click(ClickOptions.usingJavaScript());
            }
        }

        return this;
    }

    public ListPage selectMenuOption(String menuTitle, String option) {
        for (var titleEl : $$(menuBlockTitle)) {
            if (titleEl.getText().equals(menuTitle)) {
                var containerEl = titleEl.parent().parent();
                titleEl.scrollIntoView(true).shouldBe(Condition.visible).click(ClickOptions.usingJavaScript());

                var optionsContainer = containerEl.$(menuOptionsContainer).should(Condition.exist);
                optionsContainer.$(By.tagName("span")).should(Condition.exist);
                for (var spanEl : optionsContainer.$$(By.tagName("span"))) {
                    if (spanEl.getText().contains(option)) {
                        spanEl.parent().scrollIntoView(true).shouldBe(Condition.visible).click(ClickOptions.usingJavaScript());
                        break;
                    }
                }
                break;
            }
        }

        return this;
    }

    public ListPage addBookmark(String titleContains) {
        var productListItem = findProduct(titleContains);
        var btn = productListItem.$(wishListBtn).should(Condition.exist, Duration.ofSeconds(10))
                .scrollIntoView(false).shouldBe(Condition.visible);
        btn.doubleClick();

        var buttonsContainer = $(wishListModalContainer).should(Condition.exist, Duration.ofSeconds(10))
                .shouldBe(Condition.visible);
        buttonsContainer.$(closeModalLink).should(Condition.exist).shouldBe(Condition.visible)
                .click();


        return this;
    }

    public BookmarksPage goToBookmarks() {
        $(wishListPageLink).should(Condition.exist).scrollIntoView(true).shouldBe(Condition.visible).click();
        return new BookmarksPage();
    }

    private int parsePrice(String priceString) {
        var sb = new StringBuilder();
        for (var ch : priceString.strip().toCharArray()) {
            if (Character.isSpaceChar(ch)) continue;

            if (!Character.isDigit(ch)) break;

            sb.append(ch);
        }

        return Integer.parseInt(sb.toString());
    }

    private void setPage(int page) {
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        var startIndex = currentUrl.indexOf("p=");
        if (startIndex == -1) {
            if (currentUrl.endsWith("/")) {
                currentUrl += "?p=" + page;
            } else {
                currentUrl += "&p=" + page;
            }
        } else {
            boolean digit = false;
            int lastIndex;
            for (int i = startIndex; ; i++) {
                if (i == currentUrl.length()) {
                    lastIndex = i;
                    break;
                }

                if (Character.isDigit(currentUrl.charAt(i)) && digit == false) {
                    digit = true;
                    startIndex = i;
                }

                if (!Character.isDigit(currentUrl.charAt(i)) && digit == true) {
                    lastIndex = i + 1;
                    break;
                }
            }

            currentUrl = currentUrl.substring(0, startIndex) + page + currentUrl.substring(lastIndex);
        }

        Selenide.open(currentUrl);
    }

    private SelenideElement findProduct(String titleContains) {
        int page = 1;
        while (!$(mainContainer).should(Condition.exist, Duration.ofSeconds(10))
                .findElements(listContainer).isEmpty()) {
            Selenide.refresh();
            for (var li : $$(listItem)) {
                var nameEl = li.$(productNameItem).should(Condition.exist).shouldBe(Condition.visible);
                var descriptionEl = nameEl.$(By.tagName("span")).should(Condition.exist);
                if (descriptionEl.getText().contains(titleContains)) {
                    return li;
                }
            }

            setPage(++page);
        }

        return null;
    }
}
