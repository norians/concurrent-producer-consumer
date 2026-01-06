# Concurrent Producer-Consumer System (FIFO)
## Overview
This project implements the classic producer–consumer concurrency pattern using a First-In, First-Out (FIFO) buffer.
Multiple producer threads generate data and insert it into a shared buffer, while multiple consumer threads retrieve and process that data. The core goal of the project is to ensure correct synchronization, safe shared-state access, and deterministic behavior in a concurrent environment.

## Approach
- The buffer follows a First-In, First-Out policy.
