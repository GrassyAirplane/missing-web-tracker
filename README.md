# missing-web-tracker (UniHack 2023)
## Inspiration
We were inspired to create Missing-Web-Tracker after realizing that data for missing people and pets is often incomplete and difficult to access. We wanted to create a tool that would provide comprehensive and geographical clues to aid in the search for missing individuals and pets.

## What it does
Missing-Web-Tracker is a web application that offers a one-stop website for tracking missing people and pets. It provides users with comprehensive and geographical clues, allowing investigators to get a clearer understanding of the situation. Users and officials can create accounts and post new information to keep the search up-to-date.

## How to run locally
1. Download or clone this repo to your local machine
1. Run `npm install` in the `client` and `server` folders
1. `cd` into `backend` folder, and run `./gradlew build` to compile (Java 17 required)
1. Run `java -jar core/build/libs/core-0.0.1-SNAPSHOT.jar` in the `backend` directory to run the Spring Boot backend.
1. Run `npm run start` in the `server` directory to begin the Express.js server
1. Run `npm run dev` in the `client` directory to begin the React front-end. 

## How we built it


## Challenges we ran into


## Accomplishment we are proud of 


## What we learned
Throughout the project, we learned how to integrate APIs into our application, how to work collaboratively on a project, and how to implement data validation techniques to ensure that the data on our platform is accurate and trustworthy.

## What's next for Missing-Web-Tracker
In the future, we plan to implement web scraping to automatically gather data from missing people and pet websites. This will help keep our platform up-to-date with the latest information, and it will make it easier for users to find what they need without having to manually search multiple websites.
