import { Marker} from "@react-google-maps/api";
import Swal from 'sweetalert2';
import maleIcon from "../assets/male-svgrepo-com.svg"
import femaleIcon from "../assets/female-svgrepo-com.svg"

// interface Person {
//     lat: number;
//     lng: number;
//   }

const HumanMarker = (props) => {
    const handleMarkerClick = () => {
        Swal.fire({
          title: 'Details',
          text: 'You clicked on the marker!',
          icon: 'info',
          html: `<div>Name: ${props.person.name}</div>
                 <div>Age: ${props.person.age[0]}</div>
                 <div>Desc: ${props.person.appearance}</div></br>`,
          confirmButtonText: 'OK',
        });
      };

    return (
        <>
            <Marker position={{ lat: props.person["last-known-location"][0], lng: props.person["last-known-location"][1] }} 
                icon={{
                    url: props.person.extension.gender == "MALE" ? maleIcon : femaleIcon,
                    scaledSize: new window.google.maps.Size(40, 40),
                    }}
                onClick={handleMarkerClick}/>
        </>
    )
}

export default HumanMarker