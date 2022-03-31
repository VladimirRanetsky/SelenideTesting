package data;

import org.testng.annotations.DataProvider;

public class PriceRangeDataProvider {
    @DataProvider(name = "maxPriceDataProvider")
    public static Object[] generateMaxPriceData() {
        return new Object[]{6000, 16000};
    }
}
