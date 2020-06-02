# 3. currencyApiSelection

Date: 2020-06-02

## Status

Accepted

## Context

We need to choose an API for fetching currency rates.

## Decision
[Coin API](https://www.coinapi.io/) was chosen.

## Consequences

API key is required, we need a way to provide it in the runtime, without putting it to the source control (e.g. env variable).

Pros:
- nice API
- concentrated on what we are interested in
- sandbox

Cons:
- limit of 100 requests/day (but there is a sandbox)
- only one rate at the time what will cause multiple requests for one query

# Alternatives
Several other alternatives were considered, mostly [CoinGeco API](https://www.coingecko.com/en/api) (even initial 
implementation was done). 

It has a lot of pros - it's free, doesn't require API key and allows to fetch multiple rates in one query. 
Unfortunately it looks like querying for rates is mostly for fiat money (+ some cryptocurrencies)

