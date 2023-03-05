// interface CardProps {
//     name: string;
//     lastKnownLocation: string;
//     appearanceDescription: string;
//     sourceOfReport: string;
//     uuid: string;
//     imgSrc?: string;

//     // For missing human
//     age?: number;
//     gender?: string;

//     // For missing pets  
//     animal?: string;
//     breed?: string;

//     // For handling click and jumping to a map view
//     handleClick?: () => void;
// }
import "./Card.css"
import maleIcon from "../assets/male-svgrepo-com.svg"
import femaleIcon from "../assets/female-svgrepo-com.svg"
import dogIcon from "../assets/dog-5-svgrepo-com.svg"
import catIcon from "../assets/cat-svgrepo-com.svg"
import { useEffect, useState } from "react"
import Swal from "sweetalert2"
import { useHistory } from 'react-router-dom';

const Card = (props) => {
    const [distance, setDistance] = useState(null);
    
    useEffect(() => {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const { latitude: lat1, longitude: lon1 } = position.coords;
                const [lat2, lon2] = props.person["last-known-location"];
    
                const R = 6371; // Earth's radius in km
                const dLat = deg2rad(lat2 - lat1);
                const dLon = deg2rad(lon2 - lon1);
                const a = Math.sin(dLat/2) * Math.sin(dLat/2) + 
                          Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
                          Math.sin(dLon/2) * Math.sin(dLon/2);
                const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
                let distance = R * c; // Distance in km
    
                if (distance === 0) {
                    distance = distance.toFixed(1); // Round to 1 decimal point if distance is 0
                } else {
                    distance = Math.round(distance); // Round to nearest whole number otherwise
                }
    
                setDistance(distance); // Set the distance in state
            },
            (error) => console.log(error),
            { enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 }
        );
    }, [props.person]);

    const handleClick = () => {
        const lat = props.person["last-known-location"][0];
        const long = props.person["last-known-location"][1];
        Swal.fire({
          title: 'Details',
          text: 'You clicked on the marker!',
          icon: 'info',
          html: `<div>Name: ${props.person.name}</div>
                 <div>Age: ${props.person.age[0]}</div>
                 <div>Desc: ${props.person.appearance}</div>
                 <div>Last Seen: ${formattedDate} </div>
                 <div>Dist: ${distance} KM away</div>`,
          cancelButtonText: 'OK',
          showCancelButton: true,
          confirmButtonText: 'Map',
          showCloseButton: true,
          preConfirm: () => {
            window.location.assign(`http://localhost:5173/map?lat=${lat}&lng=${long}`);
          },
          iconColor: 'teal' 
        });
      };

    // Helper function to convert degrees to radians
    const deg2rad = (deg) => {
        return deg * (Math.PI/180);
    };

    const lastSeenEpoch = props.person["last-seen-epoch-milli"];
    const lastSeenDate = new Date(lastSeenEpoch);
    const formattedDate = lastSeenDate.toLocaleDateString();

    return (
        <div className="card-container">
            <img
                src={
                    props.person["report-type"] === "PERSON"
                        ? props.person.extension.gender === "MALE"
                            ? maleIcon
                            : femaleIcon
                        : props.person.extension["animal-type"].toLowerCase() === "dog"
                        ? dogIcon
                        : catIcon
                }
                alt=""
                style={{ width: "10rem", height: "10rem" }}
            />
            <h2>{props.person.name}</h2>
            {/* <p className="age">
                {props.person["report-type"] === "PERSON"
                    ? props.person.age[0]
                    : props.person.age[1]}
            </p> */}
            <p className="last-seen">{formattedDate}</p>
            {distance !== null && (
                <p className="distance">{distance} KM away</p>
            )}
            <p className="description">{props.person.description}</p>
            <button className="btn" onClick={handleClick}>
                View Details
            </button>
        </div>
    );
};

export default Card;


//   {/* The div below ensures that the card holds its shape even before the img loads
//             <div className="w-80 h-80 bg-white missing-img">
//                 <img src={props.imgSrc} alt="" className="min-w-xs min-h-xs missing-img"/>
//             </div>
//             <div style={{ backgroundColor: '#FFFFFF' }} className="p-3">
//                 <h1 className="font-bold text-lg">
//                     {props.name} {props.age ? <span>({props.age})</span> : null }, {props.gender ? <span>{props.gender}</span> : null }
//                 </h1>
//                 <div className="missing-entity-info">
//                     <p>{props.animal} {props.breed}</p>
//                 </div>
//                 <p className="font-thin">Last Seen: {props["last-known-location"]}</p>
//                 <p className="truncate">{props.appearanceDescription}</p>
//                 <div className="text-xs flex flex-row justify-between mt-2">
//                     <p>Case Id: {props.uuid}</p>
//                     <p>Source: {props.sourceOfReport}</p>
//                 </div>
//             </div> */}
//             </div>