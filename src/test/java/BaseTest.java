import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    @BeforeTest
    public void setUp() {
        DriverManager.setUpDriver();
    }

    @AfterTest
    public void tearDown() {
        DriverManager.closeDriver();
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
