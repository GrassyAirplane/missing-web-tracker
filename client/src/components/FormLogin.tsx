import { useState, useEffect } from "react"
import ScrollReveal from "scrollreveal"
import Sweetalert2 from "sweetalert2"
import { useDispatch, useSelector } from "react-redux"
import {RootState} from "../redux/store"
import { login, logout } from "../redux/loginSlice"
import Home from '../pages/Home'
import "./FormLogin.css"

interface FormLoginProps {
    onCreateAccountClick: () => void;
}

const FormLogin = (props: FormLoginProps) => {
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const dispatch = useDispatch();
    const loggedIn = useSelector((state: RootState) => state.logger.loggedIn)

    console.log(loggedIn)

    useEffect(() => {
        const sr = ScrollReveal();
        sr.reveal('.login-form', {
          duration: 400,
          distance: '40px',
          easing: 'ease-out',
          origin: 'bottom',
          reset: true,
          viewFactor: 0.2,
          delay: 0,
        });
    
      }, []);

    const handleLogin = () => {
        dispatch(login());
    }

    const handleLogout = () => {
        dispatch(logout());
    }

    const handleLoginSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent the default form submission behavior
    
        const formData = new FormData(event.currentTarget);
    
        // Perform some validation on the email and password fields
        if (email.trim() === '' || password.trim() === '') {
          Sweetalert2.fire({
            icon: 'error',
            iconColor: 'teal',
            title: 'Oops...',
            text: 'Please enter email & password',
          })
          return;
        }
        
        // You can perform the login logic here, such as calling an API to authenticate the user
        const response = await fetch('http://localhost:8080/login', {
          method: 'POST',
          body: formData
        })
        console.log(response)
    
        if (response.ok) {
          // handle login logic
          localStorage.setItem("email", email)
          localStorage.setItem("password", password)
          console.log("User logged in")
          //window.location.href = '/'
          handleLogin()
          console.log(localStorage)
          console.log(loggedIn)
    
          Sweetalert2.fire({
            icon: 'success',
            iconColor: 'teal',
            title: 'Successful',
          })
          return;
        }
    
        else {
          Sweetalert2.fire({
            icon: 'error',
            iconColor: 'teal',
            title: 'Oops...',
            text: 'Invalid email or Password',
          })
          return;
        }
      };


    return (
        <>
            {!loggedIn ? 
                (
                    <div className="login-form container">
                        <section className="wrapper">
                            <div className="heading">
                                <h1 className="text text-large">Sign In</h1>
                                <p className="text text-normal">
                                New user?{" "}
                                <span>
                                    <a href="#" className="text text-links" onClick={props.onCreateAccountClick}>
                                    Create an account
                                    </a>
                                </span>
                                </p>
                            </div>
                            <form name="signin" className="form" onSubmit={handleLoginSubmit}>
                                <div className="input-control input-field">

                                <input
                                    type="email"
                                    name="email"
                                    id="email"
                                    className="input-field"
                                    placeholder="Email Address"
                                    value={email}
                                    onChange={(event) => setEmail(event.target.value)}
                                />
                                </div>
                                <div className="input-control input-field">

                                <input
                                    type="password"
                                    name="password"
                                    id="password"
                                    className="input-field"
                                    placeholder="Password"
                                    value={password}
                                    onChange={(event) => setPassword(event.target.value)}
                                />
                                </div>
                                <div className="input-control">
                                <input
                                    type="submit"
                                    name="submit"
                                    className="input-submit button-submit"
                                    value="Sign In"
                                />
                                </div>
                            </form>
                        </section>
                    </div>
                ) : (<Home />)
            }
        </>
    )
}

export default FormLogin