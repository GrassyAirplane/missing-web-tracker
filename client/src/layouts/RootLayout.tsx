import { Outlet, NavLink } from "react-router-dom";
import { RootState } from "../redux/store";
import { useSelector, useDispatch } from "react-redux";
import NavBar from "../components/NavBar";
import { useState } from "react";

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
            <header>
                <NavBar />
            </header>
            <Outlet/>
        </div>
    );
}

export default RootLayout