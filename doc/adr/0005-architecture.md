# 6. Architecture style

Date: 2020-06-03

## Status

Accepted

## Context

We need to choose architecture for the application. 

## Decision

I try to follow Hexagonal/Onion/Ports and adapters architecture. 

This might be an overkill for such small and simple application, but it's mostly for the sake of practice.

# Consequences

Example consequences:

- No frameworks in the domain, 
- API and its responses separate from the domain, 
- When necessary (e.g. to call external service) domain exposes interfaces to be implemented by infrastructure. 
