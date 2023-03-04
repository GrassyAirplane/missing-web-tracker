import './NavBar.css'
import SearchBar from './SearchBar'
import logo from '../../public/cabbage.svg'
import profileicon from '../../public/profile-circle-svgrepo-com.svg'
import ToggleSwitch from './ToggleSwitch'
import { NavLink } from "react-router-dom"


const NavBar = () => {
    return (
        <header className="p-2 flex items-center">
            <NavLink to="/">
                <div className="main-header">
                    <img className="logo" src={logo}/>
                    <h1>Missing</h1>
                </div>
            </NavLink>
            
            <SearchBar/>

            

            <div className="nav-right flex items-center">
                <ToggleSwitch/>

                <NavLink className = "login-btn" to="/login">Log In</NavLink>

                <NavLink to="/account">
                    <button className="profile">
                        <img src={profileicon}/>
                    </button>
                </NavLink>
            </div>
        </header>
    )
}

export default NavBar