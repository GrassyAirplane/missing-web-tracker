import './NavBar.css'
import SearchBar from './SearchBar'
import logo from '../../public/cabbage.svg'
import profileicon from '../../public/profile-circle-svgrepo-com.svg'
import ToggleSwitch from './ToggleSwitch'
import { NavLink } from "react-router-dom"
import { useSelector } from 'react-redux'
import { RootState } from '../redux/store'
import Swal from 'sweetalert2'


const NavBar = () => {
    const loggedIn = useSelector((state: RootState) => state.logger.loggedIn);

    console.log("LOGG")
    console.log(loggedIn)

    const handleProfileClick = () => {
        if (!loggedIn) {
            Swal.fire({
                title: 'Not Logged In',
                text: 'You need to be logged in to view your profile.',
                icon: 'warning',
                confirmButtonText: 'OK'
            });
        }
    }
    
    return (
        <header className="p-2 flex items-center nav">
            <NavLink to="/">
                <div className="main-header" style={{marginLeft: "2rem"}}>
                    <img className="logo" src={logo}/>
                    <h1>Missing</h1>
                </div>
            </NavLink>
            
            <SearchBar/>


            <div className="nav-right flex items-center">
                <ToggleSwitch/>

                <NavLink className = "login-btn" to="/login">Log In</NavLink>

                {!loggedIn && <img src={profileicon} onClick={handleProfileClick} style={{ width: '3rem', height: '3rem', marginLeft: "2rem", marginRight: "1rem" }}/>
        
                }

                {loggedIn && <NavLink to="/account">
                    <button className="profile">
                        <img src={profileicon} style={{ width: '24px', height: '24px' }}/>
                    </button>
                </NavLink>
                }
            </div>
        </header>
    )
}




export default NavBar