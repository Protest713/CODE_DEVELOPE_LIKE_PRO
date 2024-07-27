package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private static WebDriver driver;
    private static String browser;
    private static PropertyFileReader reader = new PropertyFileReader("local.properties");

    private ApplicationManager() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getWebDriver() {
        if (driver == null) {
            synchronized (ApplicationManager.class) {
                if (driver == null) {
                    initializeWebDriver();
                }
            }
        }
        return driver;
    }

    private static void initializeWebDriver() {
        browser = reader.get("browser.type").toLowerCase();
        if ("chrome".equalsIgnoreCase(browser)) {
            driver = new ChromeDriver();
        } else if ("edge".equalsIgnoreCase(browser)) {
            driver = new EdgeDriver();
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            try {
                driver = new RemoteWebDriver(new URL(reader.get("selenium.server")), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
