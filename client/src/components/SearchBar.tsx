import Swal from 'sweetalert2';
import './SearchBar.css'
import { NavLink } from "react-router-dom"
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';

const SearchBar = () => {

    var targetUrl = "http://" + window.location.host;

    const [interestLocation, setinterestLocation] = useState<google.maps.LatLngLiteral | null>(null);
    const isLookingForPerson = useSelector((state: RootState) => state.toggler.isLookingForPerson);
    
    
    const handleAddClick = () => {
        if (!isLookingForPerson) {
          Swal.fire({
            title: 'Add Missing Person',
            html: `
              <form id="add-form">
                <input type="text" id="name" name="name" placeholder="Enter name"><br>
                <input type="text" id="last-seen-date" name="last-seen-date" placeholder="(dd/mm/yyyy)"><br>
                <input type="text" id="last-known-location" name="last-known-location" placeholder="Enter last known location"><br>
                <input type="text" id="gender" name="gender" placeholder="Enter gender"><br>
                <div>
                User 
                  <input type="radio" id="USER" name="report-source" value="USER" checked>
                  <input type="radio" id="OFFICIAL" name="report-source" value="OFFICIAL">
                    Source
                </div>
              </form>
            `,
            showCancelButton: true,
            confirmButtonText: 'Submit',
            preConfirm: () => {
              const name = Swal.getPopup().querySelector('#name').value;
              const lastSeenDate = Swal.getPopup().querySelector('#last-seen-date').value;
              const lastKnownLocation = Swal.getPopup().querySelector('#last-known-location').value;
              const gender = Swal.getPopup().querySelector('#gender').value;
              const reportSourceType = Swal.getPopup().querySelector('input[name="report-source"]:checked').value;
              const formData = {
                name,
                "last-seen-date": lastSeenDate,
                "last-known-location": lastKnownLocation,
                gender,
                "report-source-type": reportSourceType
              };
              fetch(targetUrl + ':9999/reports', {
                method: 'POST',
                body: JSON.stringify(formData),
                headers: {
                  'Content-Type': 'application/json'
                }
              })
              .then(response => {
                if (!response.ok) {
                  throw new Error(response.statusText)
                }
                return response.json()
              })
              .then(data => {
                Swal.fire({
                  title: 'Success',
                  text: 'Report added successfully',
                  icon: 'success'
                })
              })
              .catch(error => {
                Swal.fire({
                  title: 'Error',
                  text: error.message,
                  icon: 'error'
                })
              })
            },
            iconColor: 'teal'
          });
        }
        else {
            Swal.fire({
                title: 'Add Missing Pet',
                html: `
                  <form id="add-form">
                    <input type="text" id="name" name="name" placeholder="Enter name"><br>
                    <input type="text" id="last-seen-date" name="last-seen-date" placeholder="(dd/mm/yyyy)"><br>
                    <input type="text" id="last-known-location" name="last-known-location" placeholder="Enter last known location"><br>
                    <input type="text" id="type" name="type" placeholder="Enter pet type"><br>
                    <input type="text" id="breed" name="breed" placeholder="Enter pet breed"><br>
                    <div>
                      User 
                      <input type="radio" id="USER" name="report-source" value="USER" checked>
                      <input type="radio" id="OFFICIAL" name="report-source" value="OFFICIAL">
                      Source
                    </div>
                  </form>
                `,
                showCancelButton: true,
                confirmButtonText: 'Submit',
                preConfirm: () => {
                  const name = Swal.getPopup().querySelector('#name').value;
                  const lastSeenDate = Swal.getPopup().querySelector('#last-seen-date').value;
                  const lastKnownLocation = Swal.getPopup().querySelector('#last-known-location').value;
                  const petType = Swal.getPopup().querySelector('#type').value;
                  const petBreed = Swal.getPopup().querySelector('#breed').value;
                  const reportSourceType = Swal.getPopup().querySelector('input[name="report-source"]:checked').value;
                  const formData = {
                    name,
                    "last-seen-date": lastSeenDate,
                    "last-known-location": lastKnownLocation,
                    "pet-type": petType,
                    "pet-breed": petBreed,
                    "report-source-type": reportSourceType
                  };
                  fetch(targetUrl + ':9999/reports', {
                    method: 'POST',
                    body: JSON.stringify(formData),
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  })
                  .then(response => {
                    if (!response.ok) {
                      throw new Error(response.statusText)
                    }
                    return response.json()
                  })
                  .then(data => {
                    Swal.fire({
                      title: 'Success',
                      text: 'Report added successfully',
                      icon: 'success'
                    })
                  })
                  .catch(error => {
                    Swal.fire({
                      title: 'Error',
                      text: error.message,
                      icon: 'error'
                    })
                  })
                },
                iconColor: 'teal'
              });
        }
      }
        
    

    // THIS IS THE GEO LOCATION, CHANGE POTENTIALY
    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
        (position) => {
            const { latitude, longitude } = position.coords;
            setinterestLocation({ lat: latitude, lng: longitude });
        },
        (error) => console.log(error),
        { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
        );
    }, []);

    let lat = interestLocation?.lat
    let lng = interestLocation?.lng

    console.log("search bar")
    console.log(lat)
    console.log(lng)

    return (
        <div className="search-bar">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
            <ul>
                <li className="search-bar-btn"><NavLink to={`/map?lat=${lat}&lng=${lng}`}>Near Me</NavLink></li>
                <li className="search-bar-btn" onClick={handleAddClick}>Add missing</li>
                <li>
                    <form>
                        <input type="text" placeholder="Find missing"></input>
                        <button type="submit"><i className="fa fa-search"></i></button>
                    </form>
                </li>
            </ul>
        </div>
    )
}

export default SearchBar