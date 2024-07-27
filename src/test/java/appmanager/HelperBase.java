package appmanager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static appmanager.ApplicationManager.getWebDriver;

public class HelperBase {

    public static String loggedInUser = "";
    PropertyFileReader reader = new PropertyFileReader("local.properties");
    public static boolean screenShotSwitch = false;

    public boolean checkLogInUser(String user) {
        loggedInUser = user;
        WebDriver wd = getWebDriver();
        // Add logic to check if the user is logged in using WebDriver
        return false;
    }

    public void checkLogInUser() {
        getWebDriver().get(reader.get("web.Url"));
        getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static void captureScreenshot(WebDriver driver, String filename) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("Screenshots" + File.separator + filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenShot() {
        String fileName = "screenshot" + System.currentTimeMillis();
        captureScreenshot(getWebDriver(), fileName);
    }

    public synchronized void highlightAndTakeScreenShot(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background: default; border: 5px solid red;');", element);
        if (!screenShotSwitch) {
            takeScreenShot();
        }
    }
}
