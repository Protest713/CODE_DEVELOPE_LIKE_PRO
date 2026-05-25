package tests;

import appmanager.ApplicationManager;
import appmanager.HelperBase;
import appmanager.PropertyFileReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.AllureUtils;
import utils.ScreenshotUtil;

import java.util.ArrayList;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "tests",
        tags = "@CG",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class TestRunnerTest extends AbstractTestNGCucumberTests {

    private PropertyFileReader localReader = new PropertyFileReader("local.properties");
    static ArrayList<String> listOfScenarios = new ArrayList<>();

    // ✅ Required for Gradle + TestNG
    @Override
    @Test(dataProvider = "scenarios")
    public void runScenario(io.cucumber.testng.PickleWrapper pickle,
                            io.cucumber.testng.FeatureWrapper feature) {
        super.runScenario(pickle, feature);
    }

    // ✅ Scenario start
    @Before
    public void startScenario(Scenario scenario) {

        System.out.println("🚀 Scenario started: " + scenario.getName());

        ApplicationManager.getWebDriver();

        String[] tagsToBeRun = localReader.get("tagsForVideoCapture")
                .replaceAll("\\s+", "").split(",");

        boolean shouldCapture = false;

        for (String tag : scenario.getSourceTagNames()) {
            for (String tagToBeRun : tagsToBeRun) {
                if (tag.equalsIgnoreCase(tagToBeRun)) {
                    shouldCapture = true;
                    break;
                }
            }
        }

        HelperBase.screenShotSwitch = shouldCapture;
    }

    // ✅ Scenario end
//    @After
//    public void endScenario(Scenario scenario) {
//
//        if (scenario.isFailed()) {
//            AllureUtils.takeScreenshot();   // 🔥 Screenshot in Allure
//        }
//
//        ApplicationManager.stop();
//
//        listOfScenarios.add(
//                scenario.getStatus().name().toUpperCase() + " - " + scenario.getName()
//        );
//    }

    @After
    public void endScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            ScreenshotUtil.takeScreenshot();
        }

        ApplicationManager.stop();

        listOfScenarios.add(
                scenario.getStatus().name().toUpperCase() + " - " + scenario.getName()
        );
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}