Feature: Login Functionality

  @CG
  Scenario Outline: validate login functionality to the application
    Given user launch the application
#    Then user navigates to home screen
    Examples:
      | UserName | Password |
      | test     | test     |