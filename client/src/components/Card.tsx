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

const Card = (props) => {
    return (
        <div className="card bg-slate-50 rounded-3xl max-w-xs" onClick={props.handleClick}>
            {/* The div below ensures that the card holds its shape even before the img loads */}
            <div className="w-80 h-80 bg-slate-200 missing-img">
                <img src={props.imgSrc} alt="" className="min-w-xs min-h-xs missing-img"/>
            </div>
            <div className="p-3">
                <h1 className="font-bold flex text-lg">
                    {props.name} &nbsp; {props.age ? <p>({props.age})</p> : null }, &nbsp; {props.gender ? <p>{props.gender}</p> : null }
                </h1>
                <div className="missing-entity-info">
                    <p>{props.animal} {props.breed}</p>
                </div>
                <p className="font-thin">Last Seen: {props["last-known-location"]}</p>
                <p className="truncate">{props.appearanceDescription}</p>
                <div className="text-xs flex flex-row justify-between mt-2">
                    <p>Case Id: {props.uuid}</p>
                    <p>Source: {props.sourceOfReport}</p>
                </div>
            </div>
        </div>
    )
}

export default Card