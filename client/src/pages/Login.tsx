import FormLogin from "../components/FormLogin";
import FormRegister from "../components/FormRegister";
import { useState } from "react";

const Login = () => {
    const [showRegistrationForm, setShowRegistrationForm] = useState(false);

    const handleCreateAccountClick = () => {
        setShowRegistrationForm(true);
    }

    const handleAlreadyHaveAccountClick = () => {
        setShowRegistrationForm(false);
    }

    return (
        <div>
            {showRegistrationForm ?
                <FormRegister onAlreadyHaveAccountClick={handleAlreadyHaveAccountClick}/>
                :
                <FormLogin onCreateAccountClick={handleCreateAccountClick}/>
            }
        </div>
    )
}

export default Login