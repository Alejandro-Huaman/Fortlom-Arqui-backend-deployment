Feature: Fanatic account creation
  Scenario: Successful fan account creation
    Given the fan to enter the platform
    When press “Register yourself as a fan”
    And complete the form with your fanatic data
    And hit the “Register as Fanatic” button
    Then your fanatic account was successfully created
  Scenario: Wrong fan account creation
    Given the fan to enter the platform
    When press “Register yourself as a fan”
    And incorrectly complete the fanatic form
    And hit the “Register as Fanatic” button
    Then will display a message that your fanatic account was not created
  Scenario: Login with the fan account created
    Given the fan to enter the platform
    And already has an fanatic account created on the platform
    When complete your corresponding fanatic data in the form
    And fanatic hit the “Enter” button
    Then will enter the platform with the fanatic account created