import { Outlet, NavLink } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";
import NavBar from "../components/NavBar";
import { useState } from "react";
import Footer from '../components/Footer';
import './RootLayout.css'

const RootLayout = () => {
    const email = localStorage.getItem("email") || "";

    if (email !== undefined && email !== "") {
        fetch('http://localhost:8080/reports', {
            method: "GET",
        })
        .then((response) => {
            return response.json()
        })
        .then((json) => {

        })
    }

    return (
        <div>
            <header className="main-header-nav">
                <NavBar />
            </header>
            <Outlet/>
            <footer>
                <Footer/>
            </footer>
        </div>
    );
}

export default RootLayout