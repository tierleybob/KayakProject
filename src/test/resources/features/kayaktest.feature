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