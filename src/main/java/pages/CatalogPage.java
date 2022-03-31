package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CatalogPage extends BasePage {
    By labelElement = By.xpath("//a[contains(@class, 'subcategory__item')]//label");

    public CatalogPage() {
        super("https://www.dns-shop.ru/catalog/");
    }

    public ListPage openCategory(String[] path) {
        openMainCategory(path[0]);
        for (int i = 1; i < path.length; ++i)
            openSubCategory(path[i]);

        return new ListPage();
    }

    private boolean withChildren(SelenideElement categoryItem) {
        var classAttr = categoryItem.getAttribute("class");
        return classAttr != null && classAttr.contains("with-childs");
    }

    private void openMainCategory(String name) {
        var xpath = String.format("//div[contains(@class, 'subcategory__item')]//label[.='%s']", name);
        var categoryItem = $(By.xpath(xpath)).$(By.xpath("./.."));

        if (withChildren(categoryItem)) {
            categoryItem.hover();
            for (var el : categoryItem.$$(By.tagName("li"))) {
                if (el.innerText().equals(name)) {
                    var linkEl = el.$(By.linkText(name));
                    linkEl.click();
                    break;
                }
            }
        } else {
            categoryItem.click();
        }
    }

    private void openSubCategory(String name) {
        var labels = $$(labelElement);
        for (var l : labels) {
            var forAttr = l.getAttribute("for");
            if (forAttr != null && forAttr.equals(name)) {
                l.$(By.xpath("./..")).click();
                break;
            }
        }
    }
}
