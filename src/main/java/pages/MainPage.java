package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

public class MainPage extends BasePage {
    By loginButtonLocator = By.className("header__login_button");
    By alternativeMethodButton = By.xpath("//div[contains(@class, 'password-button')]");
    By loginInputContainer = By.xpath("//div[contains(@class, 'with-password__input')]");
    By passwordInputContainer = By.xpath("//div[contains(@class, 'with-password__password')]");
    By popupLoginButton = By.className("base-main-button");
    By avatarImg = By.xpath("//img[contains(@class, 'header-profile__avatar')]");
    By userMenu = By.xpath("//div[contains(@class, 'header-profile__menu')]");
    By userNameSpan = By.xpath("//span[@class='header-profile__username']");

    public MainPage() {
        super("https://www.dns-shop.ru/");
    }

    public MainPage openLoginPopup() {
        $(loginButtonLocator).shouldBe(Condition.visible).click();
        $(alternativeMethodButton).shouldBe(Condition.visible).click();
        return this;
    }

    public MainPage setLogin(String login) {
        var container = $(loginInputContainer);
        container.click();

        var input = container.$(By.tagName("input"));
        input.sendKeys(login);

        return this;
    }

    public MainPage setPassword(String password) {
        var container = $(passwordInputContainer);
        container.click();

        var input = container.$(By.tagName("input"));
        input.sendKeys(password);

        return this;
    }

    public MainPage login() {
        $(popupLoginButton).click();
        return this;
    }

    public MainPage login(String login, String password) {
        setLogin(login);
        setPassword(password);
        login();

        return this;
    }

    public boolean isUserLoggedIn(String userName) {
        var avatar = $(avatarImg).should(Condition.exist).shouldBe(Condition.visible);
        $(userMenu).should(Condition.exist);
        var userSpan = $(userNameSpan).should(Condition.exist);

        actions().moveToElement(avatar).perform();
        userSpan.shouldBe(Condition.visible);
        return userSpan.innerText().equals(userName);
    }
}
