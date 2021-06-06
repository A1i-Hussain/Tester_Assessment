Feature:
  Verify different GET operations

  Scenario: As a user I want to verify that the city Frankfurt is in Germany and that we can return the
    city's corresponding latitude and longitude.
    Given I am sending a json request
    When I perform GET operation for id "visa-frankfurt"
    Then The country code returned will be "DE"
    And The correct latitude "50.1072" and longitude "8.66375" have been returned

  Scenario: As a user I would like to filter results by fields
    Given I am sending a json request
    When I perform GET operation for fields "id" and "name"
    Then Results will only return the id and name fields

  Scenario: As a user I want to locate the exact location of city bikes
    Given I am sending a json request
    When I perform GET operation for id "visa-frankfurt"
    Then A stations ID "7b3db2d648355057dd53c21e11092e71" and name "Panoramabad" is returned
    And The correct number of free bikes "2", latitude "50.1317" and longitude "8.7171" have been returned




