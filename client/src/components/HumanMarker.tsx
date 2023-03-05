import { Marker} from "@react-google-maps/api";
import Swal from 'sweetalert2';
import maleIcon from "../assets/male-svgrepo-com.svg"
import femaleIcon from "../assets/female-svgrepo-com.svg"
import dogIcon from "../assets/dog-5-svgrepo-com.svg"
import catIcon from "../assets/cat-svgrepo-com.svg"

// interface Person {
//     lat: number;
//     lng: number;
//   }

const HumanMarker = (props) => {
    const handleMarkerClick = () => {

        if (props.person["report-type"] === "PERSON") {
            Swal.fire({
                title: 'Details',
                text: 'You clicked on the marker!',
                icon: 'info',
                html: `<div>Name: ${props.person.name}</div>
                       <div>Age: ${props.person.age[0]}</div>
                       <div>Last Seen: ${formattedDate} </div>
                       <div>Desc: ${props.person.appearance}</div>
                       <div>Sex: ${props.person.extension.gender.toLowerCase()}</div>`,
                confirmButtonText: 'OK',
              });
        }
        else {
            Swal.fire({
                title: 'Details',
                text: 'You clicked on the marker!',
                icon: 'info',
                html: `<div>Name: ${props.person.name}</div>
                       <div>Age: ${props.person.age[0]}</div>
                       <div>Last Seen: ${formattedDate} </div>
                       <div>Type: ${props.person.extension["animal-type"]}</div>
                       <div>Breed: ${props.person.extension["breed"]}</div>
                       `,
                confirmButtonText: 'OK',
              });
        }
        
      };

      const lastSeenEpoch = props.person["last-seen-epoch-milli"];
      const lastSeenDate = new Date(lastSeenEpoch);
      const formattedDate = lastSeenDate.toLocaleDateString();
  

    return (
        <>
            <Marker position={{ lat: props.person["last-known-location"][0], lng: props.person["last-known-location"][1] }} 
                icon={{
                    url: props.person["report-type"] === "PERSON"
                    ? props.person.extension.gender === "MALE"
                        ? maleIcon
                        : femaleIcon
                    : props.person.extension["animal-type"].toLowerCase() === "dog"
                    ? dogIcon
                    : catIcon,
                    scaledSize: new window.google.maps.Size(40, 40),
                    }}
                onClick={handleMarkerClick}/>
        </>
    )
}

export default HumanMarker