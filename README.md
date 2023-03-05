# missing-web-tracker (UniHack 2023)
## Inspiration
We were inspired to create Missing-Web-Tracker after realizing that data for missing people and pets is often incomplete and difficult to access. We wanted to create a tool that would provide comprehensive and geographical clues to aid in the search for missing individuals and pets.

## What it does
Missing-Web-Tracker is a web application that offers a one-stop website for tracking missing people and pets. It provides users with comprehensive and geographical clues, allowing investigators to get a clearer understanding of the situation. It scrapes the web to collect the latest data. Users and officials can create accounts and post new information to keep the search up-to-date as well.

## How to run locally
1. Download or clone this repo to your local machine
1. Run `npm install` in the `client` and `server` folders
1. `cd` into `backend` folder, and run `./gradlew build` to compile (Java 17 required)
1. Run `java -jar core/build/libs/core-0.0.1-SNAPSHOT.jar` in the `backend` directory to run the Spring Boot backend.
1. Run `npm run start` in the `server` directory to begin the Express.js server
1. Run `npm run dev` in the `client` directory to begin the React front-end. 

## Techs we used
- React, Redux, Tailwind CSS, CSS (front-end)
- Express.js, Spring Boot, Google Map API (back-end)

## Challenges we ran into
- Learning new framework (Spring Boot)
- Cross platform support
- Web scraping

## Accomplishment we are proud of 
We're proud of creating a web application that has the potential to help reunite missing people and pets with their families. We're also proud of the user-friendly interface we've created, which makes it easy for investigators and other users to find the information they need.

## What we learned
- Spring Boot
- Web scraping 

## What's next for Missing-Web-Tracker
- Add a search feature
- Complete the add a new report feature
- Improve the web scraping capability
