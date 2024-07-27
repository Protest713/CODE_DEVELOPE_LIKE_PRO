package tests;

import appmanager.HelperBase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginPageStepDef extends HelperBase {

    LoginPage loginPage = new LoginPage();

    @Given("^user launch the application$")
    public void user_launch_the_application(){
        checkLogInUser();
    }

    @When("^user login to the application:\"([^\\\"]*)\",\"([^\\\"]*)\"$")
    public void user_Login_To_The_Application(String userName, String password) {

    }

    @Then("^user able to view home page$")
    public void user_Able_To_View_Home_Page() {
    }
}
