const express = require("express");
const cors = require('cors');
const app = express();
const bodyParser = require('body-parser');
const multer = require('multer');
const upload = multer();
const moment = require('moment');
const axios = require('axios');

const PORT = 8080;

const user_database = {}

app.use(
    cors({
      origin: [
        "http://127.0.0.1:5173",
        "http://127.0.0.1:5174",
        "http://localhost:5173",
        "http://127.0.0.1:9545/",
        "http://localhost:5173/%27",
        "http://localhost:5173/#",
      ],
    })
);

// parse application/json requests
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use((req, res, next) => {
  res.setHeader('Access-Control-Allow-Origin', 'http://localhost:5173/%27');
  next();
});

app.listen(
    PORT,
    () => console.log(`it's alive on ${PORT}`)
)

app.get('/users', (req, res) => {
  res.send(JSON.stringify(user_database))
})

// Get user info
app.get('/user/:email', (req, res) => {
  const {email} = req.params;
  if (!user_database.hasOwnProperty(email)) {
    res.status(400).send("No such email exist in the database.")
  }

  res.status(200).send(user_database[email]);
})

// Get user's missing people uuid's
app.post('/user/missing_people', (req, res) => {
  const email = req.body.email;
  if (!user_database.hasOwnProperty(email)) {
    res.status(400).send("No such email exist in the database.")
  }

  const incident_uuid = req.body.uuid;

  user_database[email].missing_people.push(incident_uuid);
  
  res.status(200).send("Incident (Person) added successfully");
})

// Get user's missing pets' uuid's
app.post('/user/missing_pets', (req, res) => {
  const email = req.body.email;
  if (!user_database.hasOwnProperty(email)) {
    res.status(400).send("No such email exist in the database.")
  }

  const incident_uuid = req.body.uuid;

  user_database[email].missing_pets.push(incident_uuid);
  
  res.status(200).send("Incident (Pet) added successfully");
})

// Login
app.post('/login', upload.none(), (req, res) => {
  const email = req.body.email;
  const password = req.body.password;

  if (user_database[email].password === password) {
    res.status(200).send("Login successful");
  }
  res.status(400).send("Login failed");
})

// Register a new user
app.post('/register', upload.none(), (req, res) => {

  const email = req.body.email;
  const username = req.body.username;
  const password = req.body.password;
  console.log(email)

  if (user_database.hasOwnProperty(email)) {
    console.log("The user is already registered");
  } else {
    user_database[email] = {
      password: password,
      username: username,
      missing_people: [],
      missing_pets: []
    }
    console.log("successful")
  }
  res.status(200).sendStatus(200)
})

app.post('/reports/human', (req, res) => {
  const { name, age, 'last-seen-date': lastSeenDate, 'last-known-location': lastKnownLocation, gender, 'report-source-type': reportSourceType } = req.body;

  const epochTimestamp = moment(lastSeenDate, 'DD/MM/YY').valueOf();

  const geocodeUrl = `https://maps.googleapis.com/maps/api/geocode/json?address=${lastKnownLocation}&key=${"AIzaSyB9kymd14djtap86pyy5jNeweDcDvHeuX0"}`;

  axios.get(geocodeUrl)
    .then((response) => {
      const location = response.data.results[0].geometry.location;
      const latitude = location.lat;
      const longitude = location.lng;
      const report = { name,'last-seen-epoch-milli': epochTimestamp, 'last-known-location': [latitude, longitude], gender, 'report-source-type': reportSourceType };
      console.log(report);
      axios.put('http://localhost:9999/reports', report)
        .then(() => res.sendStatus(200))
        .catch((error) => {
          console.error(error);
          res.sendStatus(500);
        });
    })
    .catch((error) => {
      console.error(error);
      res.sendStatus(500);
    });
});

app.post('/reports/pet', (req, res) => {
  console.log(req.body)
  const { name, 'last-seen-date': lastSeenDate, 'last-known-location': lastKnownLocation, 'pet-type': petType, 'pet-breed': petBreed, 'report-source-type': reportSourceType } = req.body;

  const epochTimestamp = moment(lastSeenDate, 'DD/MM/YY').unix();

  const report = { name, 'last-seen-date': epochTimestamp, 'last-known-location': lastKnownLocation, 'pet-type': petType, 'pet-breed': petBreed, 'report-source-type': reportSourceType };

  console.log(report);

  

});



app.use((req, res, next) => {
  res.setHeader('Access-Control-Allow-Origin', 'http://localhost:5173');
  next();
});

app.get('/reports', async (req, res) => {
  const reportType = req.query.reportType;
  const uuid = req.body.uuid;
  const url = `http://localhost:9999/reports`; //?reportType=PERSON

  try {
    const response = await axios.get(url);
    res.json(response.data);
  } catch (error) {
    console.error(error);
    res.status(500).send('Internal Server Error');
  }
});

