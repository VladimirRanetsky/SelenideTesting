package pages;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class BookmarksPage extends BasePage {
    By item = By.xpath("//div[@data-id='product']");
    By itemTitleLink = By.xpath(".//a[contains(@class, 'catalog-product__name')]");

    public BookmarksPage() {
        super("");
    }

    public BookmarksPage firstItemHaveText(String text) {
        var itemEl = $(item).should(Condition.exist).shouldBe(Condition.visible);
        itemEl.$(itemTitleLink).$(By.tagName("span")).shouldHave(Condition.text(text));
        return this;
    }
}
