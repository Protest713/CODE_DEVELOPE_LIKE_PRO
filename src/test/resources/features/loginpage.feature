Feature: Login Functionality

  @CG
  Scenario Outline: validate home page functionality
    Given user launch the application
#    Then user navigates to home screen
    Examples:
      | UserName | Password |
      | test     | test     |