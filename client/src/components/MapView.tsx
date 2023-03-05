import { useEffect, useMemo, useState } from "react";
import { GoogleMap, useLoadScript, Marker} from "@react-google-maps/api";
import React from "react";
import HumanMarker from "./HumanMarker";
import { useLocation } from 'react-router-dom';

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

    const location = useLocation();
    const lati = parseFloat(new URLSearchParams(location.search).get('lat'));
    const long = parseFloat(new URLSearchParams(location.search).get('lng'));

    var targetUrl = "http://" + window.location.host;

    useEffect(() => {
      fetch(targetUrl + '/reports')
        .then(response => response.json())
        .then(data => {
          console.log("reach")
          const newMarkers = data.map((person, index) => (
            <HumanMarker person={person} />
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
        width: "80vw",
        height: "65vh",
        borderRadius: "20px",
    };

    const bounds = {
      north: 41.390205,
      south: 41.327423,
      east: 2.228215,
      west: 2.088889,
    };

    const options = {
        disableDefaultUI: true,
        zoomControl: true,
        bounds: bounds,
        minZoom: 2,
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
    
    let center = interestLocation ? interestLocation : { lat: 37.7749, lng: -122.4194 }

    if (lati && long) {
      center = {lat: lati, lng: long}
    }
  
    return (
        <div>
          {loadError && <div>Map cannot be loaded right now, sorry.</div>}
          {isLoaded && (
            <div style={{ position: "relative", marginTop: "3.25rem"}}>
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
                    // boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.25)",
                    // padding: "0.5rem",
                    // borderRadius: "30px",
                    color: "#FFF",
                    border: "1px solid rgb(132, 172, 113)",
                    backgroundColor: "rgb(132, 172, 113)",
                    padding: "0.5rem 1rem",
                    fontWeight: 700,
                    fontSize: "0.9rem",
                    textTransform: "uppercase",
                    marginBottom: "1rem",
                }}>Center</button>
                </div>
            </div>
          )}
        </div>
      );
    };
    

export default MapView;