import FormLogin from "../components/FormLogin"
import Login from "./Login"
import NavBar from "../components/NavBar"
import { useEffect, useState} from "react"
import { useSelector } from "react-redux"
import { RootState } from "../redux/store"
import Card from "../components/Card"
import "./Home.css"

const Home = () => {
    const loggedIn = useSelector((state: RootState) => state.logger.loggedIn);
    const url = "http://localhost:8080/reports"

    const [peopleCards, setPeopleCards] = useState([]);
    const [petCards, setPetCards] = useState([]);

    useEffect(() => {
      fetch("http://localhost:9999/reports?reportType=PERSON")
        .then(response => response.json())
        .then(data => {
          console.log("reach")
          const newPeople= data.map((person, index) => (
            <Card person={person} />
          ));
          setPeopleCards(newPeople);
        })
        .catch(error => console.error(error));
    }, []);

    useEffect(() => {
        fetch("http://localhost:9999/reports?reportType=PET")
          .then(response => response.json())
          .then(data => {
            console.log("reach")
            const newPet= data.map((pet, index) => (
              <Card person={pet} />
            ));
            setPetCards(newPet);
          })
          .catch(error => console.error(error));
      }, []);

    useEffect(() => {
        if (!loggedIn) {
            
        }
    }, [])

    return (
        <div className="card-section">
            { peopleCards }
            { petCards }
        </div>
    )
}

export default Home