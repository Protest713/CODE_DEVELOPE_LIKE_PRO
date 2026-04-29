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

import java.util.ArrayList;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "tests",
        tags = "@CG",
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class TestRunnerTest extends AbstractTestNGCucumberTests {

    private PropertyFileReader localReader = new PropertyFileReader("local.properties");
    static ArrayList<String> listOfScenarios = new ArrayList<>();

    // ✅ IMPORTANT: This makes Gradle detect tests
    @Override
    @Test(dataProvider = "scenarios")
    public void runScenario(io.cucumber.testng.PickleWrapper pickle,
                            io.cucumber.testng.FeatureWrapper feature) {
        super.runScenario(pickle, feature);
    }

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

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @After
    public void endScenario(Scenario scenario) {

        listOfScenarios.add(
                scenario.getStatus().name().toUpperCase() + " - " + scenario.getName()
        );

        if (scenario.isFailed()) {
            // keep browser open for debugging
            System.out.println("❌ Test failed - keeping browser open");
        } else {
            ApplicationManager.stop();
        }
    }
}