# 4. Default values for missing filter and handling unknown currencies

Date: 2020-06-03

## Status

Accepted

## Context

In the /currencies/{currency} endpoint filters are optional, we need a way to handle a scenario, when filters are not provided.

Additionally, we don't want to handle list of supported currencies in the application and rely on external service for that. 

## Decision

As we don't keep list of supported currencies in the application, and we don't want to fetch data for ever possible currency, we will narrow down list to few selected currencies (USD, BTC, ETH).

Requests for currencies unknown by external service or exchange rate between the same currency will be ignored. 


## Consequences

If user does not provide filters, by default he/she will see only few rates.
