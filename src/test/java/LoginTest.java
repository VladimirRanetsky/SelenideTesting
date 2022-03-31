import data.LoginTestData;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MainPage;

public class LoginTest extends BaseTest {
    @Test(groups = {"a"})
    @Description("Login into personal account")
    public void login() {
        var page = new MainPage();
        page.openPage();
        page.openLoginPopup();
        page.login(LoginTestData.LOGIN, LoginTestData.PASSWORD);
        Assert.assertTrue(page.isUserLoggedIn(LoginTestData.USER_NAME));
    }
}
