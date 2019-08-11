# KayakProject

Run directions:

The test case is contained within src/main/test/resources/features/kayaktest.feature file.

PLEASE NOTE: Kayak site may display a CAPTCHA verification once search is submitted. For such scenarios, I added a 65 second sleep so reviewer can manually confirm CAPTCHA verification for script to continue.

Posting scenario from feature file:

<<
@kayaktest
Feature: Testing Kayak.com site

  @kayaktest1
  Scenario Outline: Verify flight reservation
    Given I open kayak page
    When I remove pre-selected origin airport
    And I set origin airport as "<origin>"
    And I set origin to Include Nearby Airports as "<originIncludeNearby>"
    And I set destination airport as "<destination>"
    And I set destination to Include Nearby Airports as "<destinationIncludeNearby>"
    And I set depart date as "<departDate>", return date as "<returnDate>"
    And I click Search
    Then I select search result <searchToCheck> and verify Origin and Destination match input
    Examples:
      | origin | originIncludeNearby | destination | destinationIncludeNearby | departDate | returnDate | searchToCheck |
      | JFK    | yes                 | MIA         | no                       | 10/10/2019 | 10/15/2019 | 2             |
      | ORD    | no                  | OAK         | yes                      | 09/20/2019 | 10/01/2019 | 1             |
      | HNL    | yes                 | SFO         | no                       | 11/15/2019 | 11/17/2019 | 3             |
>>

Assignment as given:

Use Case to Automate

1.	  Go to Kayak.com
2.    Enter Origin and Destination Cities
3.    Select Departure and Return Dates
4.    Select Near by airports for both origin and destination - this is optional - if the data is provided, you will select, if not you will not select
5.    Click Search
6.    On search results page, select the search result N, where N is the input data
7.    Assert the Origin and Destination Details are same as the one entered in the main screen
8.    Log all critical information of the selected flight option, for the reporting purpose

Things to consider
1.    Parameterize the test input as much as possible
2.    Possible to iterate multiple test inputs - script should be running for at least 3 iterations
3.    Any specific design pattern
4.    Minimal impact of object property changes - eg. if the ID / XPath of any object changes, it should be changeable in one place

Reporting
1.    Detailed Log Messages
2.    Assertion messages
 

