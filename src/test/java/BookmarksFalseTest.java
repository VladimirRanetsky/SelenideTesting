import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.CatalogPage;

public class BookmarksFalseTest extends BaseTest {
    @Test
    @Description("Test with error")
    public void actionCameraFalseTest() {
        var page = new CatalogPage();
        page.openPage();

        String[] path = {"Смартфоны и гаджеты", "Фототехника", "Экшн-камеры и аксессуары", "Экшн-камеры"};
        var listPage = page.openCategory(path);
        listPage.enableOutOfStock().submitFilter();

        listPage.selectBrand("sony").selectMenuOption("Беспроводные интерфейсы", "NFC").submitFilter();
        var bookmarksPage = listPage.addBookmark("HDR-AS300").goToBookmarks();

        bookmarksPage.firstItemHaveText("HDR-error");
    }
}
