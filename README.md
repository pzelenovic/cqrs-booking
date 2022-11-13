## CQRS Booking

This repository is an attempt to solve https://codingdojo.org/kata/CQRS_Booking/ kata.

It says that I need to implement two user stories:

* As a user i want to see all free rooms.
* As a user i want to book a room.

So, there are `rooms` like in a hotel, or something, and there are `users`, or rather `clients` who are looking for rooms to `book`.

Hence,
* a `client` should be able to query `all free rooms`.
* a `client` should be able to `book` a `room`.

### Thoughts at the beginning

1. It would make sense to separate commands and queries in this business case, since I suppose the bookings would happen less often than queries for free rooms.
2. As far as I've learned so far, when doing CQRS you can benefit from having separate infrastructure for writes and reads, so you can optimize/grow/shrink those separately. You can have a relational DB for writes, and a NoSQL for reads, or whatever... In this case I'm not going to do that, mostly because I don't want to bother with setup of separate DBs, but also because I just want to implement the separation in the core of the application, for practice purposes.
3. Forming a mental model, I suppose I'll need a collection of rooms, and a collection of clients. Rooms have bookings, each of which is bound to a specific client, who commanded the booking to the system. Prior to that he has probably queried the system to show him a collection of rooms that are free, for the specified time interval in the future.
4. At some point in time, while writing this, I thought to myself I want to do this in Hexagonal Architecture. I'm probably going to make many mistakes, and might not even fully comply with it in the end, but the lessons along the journey is what matters.
5. I also want to TDD this thing, we'll see if I manage, and what mistakes I'll make on the way.
6. I'm considering to even BDD this, using Cypress.

Here's some reasoning about the structure of the thing I am supposed to build:

#### Domain
- Room
- User
- Booking

#### Commands
- book a room

#### Queries
- find all free rooms

#### Use cases
- the one when a client views all free rooms
- the one when a client books a room

#### Interfaces
- viewingFreeRooms
- roomBooking

#### Adapters
- room repository
- user repository
- book repository
- room controller
- room booking controller
