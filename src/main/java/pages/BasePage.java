package pages;

import com.codeborne.selenide.Selenide;

public class BasePage {
    private final String url;

    protected BasePage(String url) {
        this.url = url;
    }

    public void openPage() {
        Selenide.open(url);
    }
}
