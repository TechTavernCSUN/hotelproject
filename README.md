# Hotel Reservation System

Welcome to the Hotel Reservation System project! This system allows clients to search for available rooms, reserve them, and manage their reservations. Additionally, the system provides administrative features such as generating reports based on the reservation database.

## Features

- **Room Availability**: Clients can search for available rooms.
- **Reservation**: Clients can reserve rooms based on availability.
- **Cancellation**: Clients can cancel their reservations.
- **Payment**: Payment is determined based on the room type (single, double, suite).
- **Database Integration**: Data is stored in a database to manage reservations and room availability.
- **Report Generation**: Administrators can generate reports using the reservation database.

## Merging to Main

To merge changes from a test branch to the main branch, follow these steps:

1. Switch to the test branch:

git checkout test

2. Pull the latest changes from the remote repository:

git pull

3. Switch back to the main branch:

git checkout master

4. Pull the latest changes from the main branch:

git pull

5. Merge changes from the test branch to the main branch with a non-fast-forward merge and without committing:

git merge --no-ff --no-commit test

Feel free to customize and expand upon this template as needed for your project.


