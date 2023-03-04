import FormLogin from "../components/FormLogin"
import Login from "./Login"
import NavBar from "../components/NavBar"
import { useEffect } from "react"
import { useSelector } from "react-redux"
import { RootState } from "../redux/store"

const Home = () => {
    const loggedIn = useSelector((state: RootState) => state.logger.loggedIn);
    const url = "http://localhost:8080/reports"

    useEffect(() => {
        if (!loggedIn) {
            
        }
    }, [])

    return (
        <div>
            {
                
            }
        </div>
    )
}

export default Home