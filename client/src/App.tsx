import { useState } from 'react'
import reactLogo from './assets/react.svg'
import Home from './pages/Home'
import Login from './pages/Login'
import Card from './components/Card'
import MapView from './components/MapView'
import Account from './pages/Account'
import NavBar from './components/NavBar'
// import './App.css'

const ex_info = {
  name: "John Doe",
  lastKnownLocation: "Melbourne CBD",
  appearanceDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus nec sapien tortor. Pellentesque cursus fringilla mattis. Praesent cursus turpis enim, ac lacinia augue sodales sit amet. Donec tempus vehicula turpis, eu varius turpis feugiat et. Maecenas vel molestie velit. Aenean mattis, orci at condimentum ornare, eros diam luctus nisl, nec ultrices elit metus et turpis. Aenean varius enim ut nunc volutpat, sit amet pulvinar dui dapibus. Mauris fermentum tempus lectus, in lobortis turpis ultrices ut.",
  sourceOfReport: "Official",
  uuid: "88abdd139e",
  age: 19,
  gender: "Male",
  imgSrc: "https://source.unsplash.com/WLUHO9A_xik/900x900"
}

function App() {
  // Change the return statement as you wish, this is only for demo purpose
  return (
    <div>
      <NavBar />
      <h1>Hello App</h1>
      <MapView latitude={0} longitude={0} />
      {/* <Login /> */}

      {/* I'm using flex rn, but grid probably works better. Also, add vertical margin between cards for better ui */}
      {/* <div className="flex flex-row flex-wrap p-2 justify-evenly">
        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />

        <Card name={ex_info.name} 
          lastKnownLocation={ex_info.lastKnownLocation} 
          appearanceDescription={ex_info.appearanceDescription}
          sourceOfReport={ex_info.sourceOfReport}
          uuid={ex_info.uuid}
          age={ex_info.age}
          gender={ex_info.gender}
          imgSrc={ex_info.imgSrc}
        />
      </div> */}

      <Account />
      
    </div>
  )
}

export default App
