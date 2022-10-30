Feature: Ability to report fraud
  Scenario: Report fraud in publications
    Given that the user is in the “Posts” section
    And click on “See Posts”
    When you notice there is a post you think is wrong.
    And press the “flag” button
    Then the report is created.
  Scenario: Report fraud in the forums
    Given that the user is in the “Forum” section
    And enter a forum
    When notice there is a forum that seems wrong to you.
    And press the “flag” button
    Then the report is created.
  Scenario: Report fraud in forum comments
    Given that the user is in the “Forum” section
    And enter a forum
    When you notice there is a comment that seems wrong to you.
    And press the “flag” button
    Then the report is created of forum