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

import java.util.ArrayList;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "tests",
        tags = "@CG",
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    private PropertyFileReader localReader = new PropertyFileReader("local.properties");
    static ArrayList<String> listOfScenarios = new ArrayList<>();

    @Before
    public void startScenario(Scenario scenario) {
        String[] tagsToBeRun = localReader.get("tagsForVideoCapture").replaceAll("\\s+", "").split(",");
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
        if (shouldCapture) {
            System.out.println("===============================" + scenario.getName() + "===============================");
        }
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @After
    public void endScenario(Scenario scenario) {
        ApplicationManager.stop();
        listOfScenarios.add(scenario.getStatus().name().toUpperCase() + " - " + scenario.getName());
    }
}
