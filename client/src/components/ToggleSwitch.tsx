import './ToggleSwitch.css'
import { useOutletContext } from "react-router-dom";
import { RootState } from '../redux/store';
import { useSelector, useDispatch } from 'react-redux';
import { toggle } from '../redux/isLookingForPersonSlice';
import { useEffect } from 'react';

const ToggleSwitch = () => {
    const isLookingForPerson = useSelector((state: RootState) => state.toggler.isLookingForPerson);
    const dispatch = useDispatch();

    console.log("STATE")
    console.log(isLookingForPerson)
    console.log("STATE")

    useEffect(() => {
        console.log(isLookingForPerson)
    }, [isLookingForPerson])

    const handleToggle = (e: React.MouseEvent<HTMLInputElement, MouseEvent>) => {
        dispatch(toggle());
    }

    return (
        <div className="toggle-switch">
            <span className="person-label">Person</span>
            <input type="checkbox" id="switch" onClick={handleToggle}/><label htmlFor="switch">Toggle</label>
            <span className="pet-label">Pet</span>
        </div>
    )
}

export default ToggleSwitch