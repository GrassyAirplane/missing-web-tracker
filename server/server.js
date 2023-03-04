const express = require("express");
const app = express();

const PORT = 8080;

const user_database = {}

app.use(
    cors({
      origin: [
        "http://127.0.0.1:5173",
        "http://127.0.0.1:5174",
        "http://localhost:5173",
        "http://127.0.0.1:9545/",
      ],
    })
);

app.listen(
    PORT,
    () => console.log(`it's alive on ${PORT}`)
)


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
app.post('/login', (req, res) => {
  const email = req.body.email;
  const password = req.body.password;

  if (user_database[email].password === password) {
    res.status(200).send("Login successful");
  }
  res.status(400).send("Login failed");
})

// Register a new user
app.post('/register', (req, res) => {
  const email = req.body.email;
  const username = req.body.username;
  const password = req.body.password;

  if (user_database.hasOwnProperty(email)) {
    console.log("The user is already registered");
  } else {
    user_database[email] = {
      password: password,
      username: username,
      missing_people: [],
      missing_pets: []
    }
  }
  res.status(200).send("Registration successful");
})

