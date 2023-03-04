import './NavBar.css'
import SearchBar from './SearchBar'
import logo from '../../public/cabbage.svg'
import loginicon from '../../public/profile-circle-svgrepo-com.svg'
import ToggleSwitch from './ToggleSwitch'

const NavBar = () => {
    return (
        <header>
            <div className="main-header">
                <img className="logo" src={logo}/>
                <h1>Missing</h1>
            </div>
            <SearchBar/>
            <div className="nav-right">
                <ToggleSwitch/>
                <button className="login">
                    <img src={loginicon}/>
                </button>
            </div>
        </header>
    )
}

export default NavBar