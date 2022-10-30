Feature: Ability to report rude users
  Scenario: Report fraud in publications
    Given that the user is in the “Publication” section
    And click on “the See Posts”
    When you notice there is a artist post you think is wrong.
    And press the fanatic “flag” button
    And write to report description
    And hit the “report” button
    Then the fanatic report is created.
  Scenario: Report fraud in the forums
    Given that the user is in the "Fanatic Forum" section
    And enter a fanatic forum
    When notice there is a fanatic forum that seems wrong to you.
    And press the fanatic “flag” button
    And write to report description
    And hit the “report” button
    Then the fanatic report is created.
  Scenario: Report fraud in forum comments
    Given that the user is in the "Fanatic Forum" section
    And enter a forum
    When you notice there is a fanatic comment that seems wrong to you.
    And press the fanatic “flag” button
    And write to report description
    And hit the “report” button
    Then the fanatic report is created.