package appmanager;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

import static appmanager.ApplicationManager.getWebDriver;

public class HelperBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public static String loggedInUser = "";
    public static boolean screenShotSwitch = false;

    PropertyFileReader reader = new PropertyFileReader("local.properties");

    public HelperBase() {
        this.driver = ApplicationManager.getWebDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ==========================
    // Login Methods
    // ==========================

    public boolean checkLogInUser(String user) {
        loggedInUser = user;
        return false;
    }

    public void checkLogInUser() {
        getWebDriver().get(reader.get("web.Url"));
    }

    // ==========================
    // Screenshot Methods
    // ==========================

    public static void captureScreenshot(WebDriver driver, String fileName) {
        try {
            File screenshot =
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotDir = new File("Screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            FileUtils.copyFile(
                    screenshot,
                    new File(screenshotDir + File.separator + fileName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenShot() {
        captureScreenshot(
                getWebDriver(),
                "Screenshot_" + System.currentTimeMillis());
    }

    public synchronized void highlightAndTakeScreenShot(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript(
                "arguments[0].style.border='3px solid red'",
                element);
        if (!screenShotSwitch) {
            takeScreenShot();
        }
    }

    // ==========================
    // Wait Methods
    // ==========================

    public WebElement waitForVisibility(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForInvisibility(By locator) {
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForPageLoad() {
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    public static void testStepPassed(String msg) {
        System.out.println("[PASSED] " + msg);
        Allure.step("[PASSED] " + msg);
    }

    public static void testStepFailed(String msg) {
        System.out.println("[FAILED] " + msg);
        Allure.step("[FAILED] " + msg);
    }

    public static void testStepInfo(String msg) {
        System.out.println("[INFO] " + msg);
        Allure.step("[INFO] " + msg);
    }

    // ==========================
    // Click Methods
    // ==========================

    public void clickOn(String obj, String element) {
        try{
            WebElement we = getWebelement(obj);
            we.click();
            testStepPassed("Clicked on Element-" + element);
        }catch (Exception ex){
            testStepFailed("Unable to Click on Element- "+element);
        }

    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void jsClick(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public WebElement getWebelement(By locator) {
        return waitForVisibility(locator);
    }

    public WebElement getWebelement(String object) {
        return getWebelement(By.xpath(object));
    }

    // ==========================
    // Text Methods
    // ==========================

    public void enterText(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void enterText(String locator, String text) {
        try{
            WebElement element = getWebelement(locator);
            element.clear();
            element.sendKeys(text);
            testStepInfo("Entered the value in Text Field");
        } catch (Exception e) {
            testStepFailed("Exception cought while entering the value in text field, Message is->" +e.getMessage());
        }
    }

    public void clearAndType(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    public String getAttribute(By locator, String attribute) {
        return waitForVisibility(locator).getAttribute(attribute);
    }

    // ==========================
    // Validation Methods
    // ==========================

    public boolean isDisplayed(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEnabled(By locator) {
        try {
            return waitForVisibility(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSelected(By locator) {
        try {
            return waitForVisibility(locator).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Scroll Methods
    // ==========================

    public void scrollToElement(By locator) {
        WebElement element = waitForVisibility(locator);
        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        element);
    }

    // ==========================
    // Browser Methods
    // ==========================

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }
}