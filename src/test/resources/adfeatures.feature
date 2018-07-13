Feature: AdsOfHouses
  Scenario: Using ikman.lk to search and filter houses in  CMB
    Given Navigate to "https://ikman.lk" page
    When Click on Property link
    And Click on Houses link
    And Click on Colombo link
    And Enter min_price of house as "<5000000>" and a max_price of house as "<7500000>"
    And Click Apply Button
    And Click on Beds link
    And Select the number of Beds
    Then Get the number of ads
    And Get all the prices of the filtered ads