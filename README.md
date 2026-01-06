# Concurrent Producer-Consumer System (FIFO)
## Overview
This project implements the classic producer–consumer concurrency pattern using a First-In, First-Out (FIFO) buffer.
Multiple producer threads generate data and insert it into a shared buffer, while multiple consumer threads retrieve and process that data. The core goal of the project is to ensure correct synchronization, safe shared-state access, and deterministic behavior in a concurrent environment.

## Approach
- The buffer follows a First-In, First-Out policy
- ReentrantLock and Condition for explicit synchronization
- StructuredTaskScope for structured concurrency
- Poison Pill protocol for coordinated and deterministic shutdown
- Support for multiple producers and consumers

## Architecture
- **Producer**
  - Generates integer values
  - After producing a finite number of elements, sends a `POISON_PILL`
  - Terminates naturally by exiting `run()`
- **Consumer**
  - Continuously consumes values from the buffer
  - Terminates when it receives a `POISON_PILL`
- **Buffer**
  - Acts as a bounded FIFO queue (`ArrayDeque`)
  - Uses `ReentrantLock` + `Condition` (`notFull`, `notEmpty`)
  - Does not interpret business logic, except for minimal coordination required by multiple producers
- **Main**
  - Launches producers and consumers using `StructuredTaskScope`
  - Ensures tasks are scoped, joined, and terminated together

## Design Decisions
I used `ArrayDeque` because it matches the conceptual model of a bounded queue. `BlockingQueue` has not been used because the main 
goal is to implement the synchronization explicitly and understand the workflow.

I've also used the Poison Pill protocol instead of interrupting the threads because I wanted a deterministic shutdown, so the 
shutdown is part of the protocol, not a side effect.

## Limitations
- No fairness guarantees (`ReentrantLock` is non-fair).
- Logging (`System.out.println`) is mixed with logic for visibility during execution.
- Producer termination condition is artificial (finite counter).
