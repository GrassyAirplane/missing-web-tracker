import { useState } from 'react'
import Home from './pages/Home'
import Login from './pages/Login'
import Account from './pages/Account'
import RootLayout from './layouts/RootLayout'
import { createBrowserRouter, Route, createRoutesFromElements, RouterProvider } from 'react-router-dom'

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

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<RootLayout />}>
      <Route index element={<Home />}/>
      <Route path="login" element={<Login />}/>
      <Route path="account" element={<Account />}/>
      <Route path="login" element={<Login />}/>
    </Route>), {
      basename: ""
    }
)

function App() {
  // Change the return statement as you wish, this is only for demo purpose
  return (
    <div>
        <div className="App">
            <RouterProvider router={router} />
        </div>
    </div>
  )
}

export default App
