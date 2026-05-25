package utils;

import appmanager.ApplicationManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtil {

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public static byte[] takeScreenshot() {

        return ((TakesScreenshot)
                ApplicationManager.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }
}