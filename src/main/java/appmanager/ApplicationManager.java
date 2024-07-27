package appmanager;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ApplicationManager extends HelperBase {

    public static WebDriver driver;
    private static String browser;
    static String target = System.getProperty("target", "local");
    static PropertyFileReader reader = new PropertyFileReader(String.format("local.properties", target));

    HelperBase helperBase = new HelperBase();

    public String LoggedInUser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public ApplicationManager() {

    }

    public static WebDriver getWebDriver() {
        try {
            if (driver == null) {
                if ("".equals(reader.get("selenium.server"))) {
                    if ("chrome".equalsIgnoreCase(browser)) {
//                        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dell\\IdeaProjects\\code_like_pro\\msedgedriver.exe");
                        driver = new ChromeDriver();
                    } else if ("edge".equalsIgnoreCase(browser)) {
//                        System.setProperty("webdriver.edge.driver", "C:\\Users\\dell\\IdeaProjects\\code_like_pro\\msedgedriver.exe");
                        driver = new EdgeDriver();
                    }
                }else {
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName(browser);
                    try {
                        driver = new RemoteWebDriver(new URL(reader.get("selenium.server")), capabilities);
                    } catch (MalformedURLException e){
                        e.printStackTrace();
                    }
                }
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                return driver;
            } else {
                return driver;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public void initUrl(String userName) {
        try {
            getWebDriver().get(reader.get("web.Url"));
            String pwdDecode = new String(Base64.decodeBase64(reader.get(""+userName+"")));
            Robot robot = new Robot();
            robot.delay(3000);
            robotType(robot, userName);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.delay(3000);
            robotType(robot,pwdDecode);
            robot.keyPress(KeyEvent.VK_ENTER);
            getWebDriver().manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void initUrl() {
        try {
            getWebDriver().get(reader.get("web.Url"));
            getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void robotType(Robot robot, String characters){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(characters);
        clipboard.setContents(stringSelection,null);
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1000);
    }
}
