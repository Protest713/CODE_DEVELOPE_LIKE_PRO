package appmanager;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;

import static appmanager.ApplicationManager.driver;
import static appmanager.ApplicationManager.getWebDriver;

public class HelperBase {

    public static String loggedInUser= "";
    PropertyFileReader reader = new PropertyFileReader("local.properties");
    public static boolean screenShotSwitch = false;

    public boolean checkLogInUser(String user){
        ApplicationManager app = null;
        if (reader.get("browser.type").toLowerCase().contains("chrome")){
            app = new ApplicationManager((System.getProperty("browser","chrome")));
        } else if (reader.get("browser.type").toLowerCase().contains("edge")) {
            app = new ApplicationManager((System.getProperty("browser","edge")));
        }else {
            app = new ApplicationManager((System.getProperty("browser","internet explorer")));
        }
        boolean blnLohIn = false;
        loggedInUser = user;
        WebDriver wd = driver;
        if (wd!=null){
//            if(isElementPre)
        }
        return false;
    }

    public void checkLogInUser() {
        ApplicationManager app = null;
        if (reader.get("browser.type").toLowerCase().contains("chrome")) {
            app = new ApplicationManager((System.getProperty("browser", "chrome")));
        } else if (reader.get("browser.type").toLowerCase().contains("edge")) {
            app = new ApplicationManager((System.getProperty("browser", "edge")));
        } else {
            app = new ApplicationManager((System.getProperty("browser", "internet explorer")));
        }
        app.initUrl();
    }


//    public static void captureScreenShot(String filename) {
//        if (!screenShotSwitch){
//            File scrFile = null;
//            String srcPath = "Users\\dell\\IdeaProjects\\code_like_pro\\src"+"Screenshots";
//            File file = new File(srcPath);
//            file.mkdir();
//            try {
//                Shutterbug.shootPage(driverManager.getWebDriver(), ScrollStrategy.BOTH_DIRECTIONS).withName(filename)
//                        .save(srcPath);
//            }catch (Exception ex){
//                testReporter("Red", ex.toString());
//            }
//        }
//    }

    public static void captureScreenshot(WebDriver driver, String filename) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("Screenshots" + File.separator + filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenShot(){
        String fileName = "screenshot"+System.currentTimeMillis();
        captureScreenshot(driver,fileName);
    }
//    public WebElement getWebElement(String object){ return getWebElement(By.xpath(object));}
    public synchronized void highlightAndTakeScreenShot(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript("argument[0].setAttribute('style', 'background: default; border: 5px solid red;');", element);
        if (!screenShotSwitch){
            takeScreenShot();
        }
    }
}
