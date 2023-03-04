import { useEffect, useMemo, useState } from "react";
import { GoogleMap, useLoadScript, Marker} from "@react-google-maps/api";
import React from "react";
import HumanMarker from "./HumanMarker";

interface MapControlsProps {
    center: google.maps.LatLngLiteral;
    onCenterChange: (center: google.maps.LatLngLiteral) => void;
  }
  
  const MapControls = ({ center, onCenterChange }: MapControlsProps) => {
    const handleClick = () => {
      onCenterChange(center);
    };
  
    return (
      <div className="map-controls">
        <button className="button-with-shadow" onClick={handleClick}>Center Map</button>
      </div>
    );
  };
  
interface Coordinate {
    latitude: number;
    longitude: number;
  }

const MapView = (prop: Coordinate) => {
    const [interestLocation, setinterestLocation] = useState<google.maps.LatLngLiteral | null>(null);

    const [markers, setMarkers] = useState([]);

    useEffect(() => {
      fetch("http://localhost:9999/reports?reportType=PERSON")
        .then(response => response.json())
        .then(data => {
          console.log("reach")
          const newMarkers = data.map((person, index) => (
            <HumanMarker key={index} lat={person.lastKnownLocation.latitude} lng={person.lastKnownLocation.longitude} />
          ));
          setMarkers(newMarkers);
        })
        .catch(error => console.error(error));
    }, []);
  

    const { isLoaded, loadError } = useLoadScript({
        googleMapsApiKey:  "AIzaSyB9kymd14djtap86pyy5jNeweDcDvHeuX0",
        libraries: ["places"],
    });

    const mapContainerStyle = {
        width: "90vw",
        height: "400px",
        borderRadius: "20px",
    };

    const options = {
        disableDefaultUI: true,
        zoomControl: true,
        styles: [
        {
            featureType: "poi",
            stylers: [
            {
                visibility: "off",
            },
            {
                featureType: "transit.station.bus",
                stylers: [{ visibility: "off" }],
            },
            {
                featureType: "transit.station.train",
                stylers: [{ visibility: "off" }],
            },
            {
                featureType: "transit.station.tram",
                stylers: [{ visibility: "off" }],
            },
            ],
        },
        ],
        fullscreenControl: true,
    };


    const mapRef = React.useRef<google.maps.Map | null>(null);
    const onMapLoad = React.useCallback((map: google.maps.Map) => {
      mapRef.current = map;
      setTimeout(() => {
        map.setZoom(14);
      }, 500);
    }, []);
  
    const handleCenterChange = (center: google.maps.LatLngLiteral) => {
      if (mapRef.current) {
        mapRef.current.panTo(center);
      }
    };

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
    
    const center = useMemo(() => (interestLocation ? interestLocation : { lat: 37.7749, lng: -122.4194 }), [interestLocation]);
    console.log(interestLocation)
    return (
        <div>
          {loadError && <div>Map cannot be loaded right now, sorry.</div>}
          {isLoaded && (
            <div style={{ position: "relative"}}>
                <GoogleMap
                    mapContainerStyle={mapContainerStyle}
                    center={center}
                    zoom={10}
                    options={options}
                    onLoad={onMapLoad}
                    >
                    {/* Marker for the location going to  */}
                    <Marker
                    position={center}     
                    />

                    {/* Create for loop for all Human Mark based on get request from localhost:9999/reports?reportType=PERSOn where the
                    response would be a list of json indicating location data */}
                    {markers}
                    
                    {/* Map markers and shapes */}
                </GoogleMap>
                {/* Button container */}
                <div style={{ position: "absolute", bottom: "10px", left: "50%", transform: "translateX(-50%)" }}>
                <button onClick={() => {
                    const map = mapRef.current;
                    map.setCenter(center);
                    map.setZoom(14);
                }}
                style={{ 
                    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.25)",
                }}>Center</button>
                </div>
            </div>
          )}
        </div>
      );
    };
    

export default MapView;