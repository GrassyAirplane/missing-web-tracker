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
          title: 'Marker Clicked',
          text: 'You clicked on the marker!',
          icon: 'success',
          confirmButtonText: 'OK',
        });
      };

    return (
        <>
            <Marker position={{ lat: props.lat, lng: props.lng }} 
                icon={{
                    url: maleIcon,
                    scaledSize: new window.google.maps.Size(40, 40),
                    }}
                onClick={handleMarkerClick}/>
        </>
    )
}

export default HumanMarker