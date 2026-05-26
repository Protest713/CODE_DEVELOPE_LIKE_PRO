package utils;

import appmanager.ApplicationManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    @Attachment(value = "Failure Screenshot",
            type = "image/png")

    public static byte[] takeScreenshot() {

        try {

            WebDriver driver =
                    ApplicationManager.getWebDriver();

            if (driver == null) {
                return new byte[0];
            }

            return ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);

        }
        catch (Exception e) {

            System.out.println(
                    "Screenshot capture failed: "
                            + e.getMessage());

            return new byte[0];
        }
    }
}