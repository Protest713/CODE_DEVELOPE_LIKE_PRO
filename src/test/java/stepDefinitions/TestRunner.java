package stepDefinitions;

import appmanager.ApplicationManager;
import appmanager.HelperBase;
import appmanager.PropertyFileReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import java.util.ArrayList;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json", "json:target/cucumber-reports/cucumber.json"},
        monochrome = true,
        tags = "@CG")
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


//    public void startScenario(Scenario scenario){
//    String[] tagsToBeRun = localReader.get("tagsForVideoCapture").replaceAll("\\s+","").split(",");
//    for (String tag : scenario.getSourceTagNames()){
//        for (String tagToBeRun : tagsToBeRun){
//            if (tag.equalsIgnoreCase(tagToBeRun)){
//                System.out.println("==============================="+tag+"===============================");
//            }else {
//                HelperBase.screenShotSwitch = false;
//            }
//        }
//    }
//}

@After
public void endScenario(Scenario scenario){
    ApplicationManager.stop();
    listOfScenarios.add(scenario.getStatus().name().toUpperCase()+" - "+scenario.getName());
}
}
