import MapView from './MapView'
import MapButton from './MapButton'
import './MapButton.css'

const MapPage = (props: any) => {
    return (
        <div className="map-page">
            <MapView latitude={props.lat} longitude={props.lng}/>
            {/* <MapButton page="list"/> */}
        </div>
    )
}

export default MapPage