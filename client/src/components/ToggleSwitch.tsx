import './ToggleSwitch.css'

const ToggleSwitch = () => {
    return (
        <div className="toggle-switch">
            <span className="person-label">Person</span>
            <input type="checkbox" id="switch" /><label htmlFor="switch">Toggle</label>
            <span className="pet-label">Pet</span>
        </div>
    )
}

export default ToggleSwitch