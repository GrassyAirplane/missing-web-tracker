import Swal from 'sweetalert2';
import './SearchBar.css'
import { NavLink } from "react-router-dom"
import { useEffect, useState } from 'react';

const SearchBar = () => {

    const [interestLocation, setinterestLocation] = useState<google.maps.LatLngLiteral | null>(null);


    const handleAddClick = () => {
            Swal.fire({
                title: 'Not Logged In',
                text: 'You need to be logged in to view your profile.',
                icon: 'warning',
                confirmButtonText: 'OK'
            });
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