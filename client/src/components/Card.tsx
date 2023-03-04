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

const Card = (props) => {
    return (
            <div className="card-container">
              <img src={props.imgSrc} alt="" />
              <h2>{props.name}</h2>
              <p className="age">{props.age}</p>
              <p className="last-seen">{props.lastSeen}</p>
              <p className="distance">{props.distance} away</p>
              <p className="description">{props.description}</p>
              <button className="btn" onClick={props.handleClick}>
                View Details
              </button>
            </div>
          
    )
}

export default Card


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