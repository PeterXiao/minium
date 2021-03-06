# language: en
# ------------------------------------------------------------------------------
Feature: SSend Emails
  
  Scenario Outline: Send simple emails
    When I click on button "Compose"
    And I fill:
      | Recipients | <to>      |
      | Subject    | <subject> |
      | Message    | <message> |
    And I click on button "Send"
    And I navigate to section "Sent"
    Then I should see an email with:
      | Recipients | <to>      |
      | Subject    | <subject> |
      | Message    | <message> |

    Examples: 
      | to             | subject      | message                                                 |
      | Rui Figueira   | Minium Test  | My New messages                                         |
      | Mario Lameiras | BDD + Minium | Egestas morbi at. Curabitur aliquet et commodo nonummy  |
      | Mario Lameiras | Lorem ipsum  | Lorem ipsum dolor sit amet, consectetur adipiscing elit |
   
   Scenario: Send an Email
    When I click on button "Compose"
    ##############################
    # COMMENTSasdasd
    ##############################
    And I fill:
      | Recipients | Rui Figueira   |
      | Subject    | Minium Test    |
      | Message    | My new Message |
    And I click on button "Send"
    And I navigate to section "Sent"
    Then I should see an email with:
      | Recipients | Rui Figueira   |
      | Subject    | Minium Test    |
      | Message    | My new Message |
   