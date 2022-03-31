import data.PriceRangeDataProvider;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CatalogPage;

public class PriceRangeTest extends BaseTest {
    @Test(dataProvider = "maxPriceDataProvider", dataProviderClass = PriceRangeDataProvider.class, groups = {"b"})
    @Description("Check maximum price correct filtering")
    public void maxPriceCheck(int maxPrice) {
        var page = new CatalogPage();
        page.openPage();

        String[] path = {"Смартфоны и гаджеты", "Планшеты, электронные книги", "Планшеты"};
        var listPage = page.openCategory(path);
        listPage.setMaxPrice(maxPrice);
        listPage.submitFilter();
        Assert.assertTrue(listPage.checkPriceValues(0, maxPrice));
    }
}
