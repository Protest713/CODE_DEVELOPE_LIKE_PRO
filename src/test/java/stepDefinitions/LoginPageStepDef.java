package stepDefinitions;

import appmanager.HelperBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginPageStepDef extends HelperBase {

    @Given("^user launch the application$")
    public void user_launch_the_application() {
        checkLogInUser();
    }
}
