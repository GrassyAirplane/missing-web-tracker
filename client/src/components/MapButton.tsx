import './MapButton.css'

const MapButton = (props: any) => {
    return (
        <div className="map-btn">
            <button className="map-btn">Show {props.page}</button>
        </div>
    )
}

export default MapButton