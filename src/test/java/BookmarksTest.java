import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CatalogPage;

public class BookmarksTest extends BaseTest {
    @Test
    @Description("Check bookmarks")
    public void actionCameraTest() {
        var page = new CatalogPage();
        page.openPage();

        String[] path = {"Смартфоны и гаджеты", "Фототехника", "Экшн-камеры и аксессуары", "Экшн-камеры"};
        var listPage = page.openCategory(path);
        listPage.enableOutOfStock().submitFilter();

        listPage.selectBrand("sony").selectMenuOption("Беспроводные интерфейсы", "NFC").submitFilter();
        var bookmarksPage = listPage.addBookmark("HDR-AS300").goToBookmarks();

        bookmarksPage.firstItemHaveText("HDR-AS300");
    }
}
