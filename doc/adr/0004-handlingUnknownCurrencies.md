# 4. Handling unknown currencies

Date: 2020-06-03

## Status

Accepted

## Context

As of now, we don't want to handle list of supported currencies in the application and rely on external service for that. 

## Decision

Requests for currencies rates unknown by external service will be ignored. 

## Consequences

User might not get some rates that he/she requested.
