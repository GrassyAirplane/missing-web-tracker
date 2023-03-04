import { useState, useEffect } from "react"
import { useSelector } from "react-redux"
import Sweetalert2 from "sweetalert2"
import ScrollReveal from "scrollreveal"
import {RootState} from "../redux/store"
import Card from "../components/Card"
import userLogo from "../assets/user-svgrepo-com.svg"

const ex_info = {
  name: "John Doe",
  lastKnownLocation: "Melbourne CBD",
  appearanceDescription: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus nec sapien tortor. Pellentesque cursus fringilla mattis. Praesent cursus turpis enim, ac lacinia augue sodales sit amet. Donec tempus vehicula turpis, eu varius turpis feugiat et. Maecenas vel molestie velit. Aenean mattis, orci at condimentum ornare, eros diam luctus nisl, nec ultrices elit metus et turpis. Aenean varius enim ut nunc volutpat, sit amet pulvinar dui dapibus. Mauris fermentum tempus lectus, in lobortis turpis ultrices ut.",
  sourceOfReport: "Official",
  uuid: "88abdd139e",
  age: 19,
  gender: "Male",
  imgSrc: "https://source.unsplash.com/WLUHO9A_xik/900x900"
}

const Account = () => {
    const loggedIn = useSelector((state: RootState) => state.logger.loggedIn);

    // if (!loggedIn) {
    //     Sweetalert2.fire({
    //       icon: "error",
    //       iconColor: "teal",
    //       title: "To login...",
    //       text: "You are not logged in!",
    //     }).then((result) => {
    //       if (result.isConfirmed) {
    //         window.location.href = "/login"; // replace '/login' with the actual URL of your login page
    //       }
    //     });
    // }

    useEffect(() => {
        const sr = ScrollReveal();

        sr.reveal(".profile-info", {
            duration: 500,
            distance: "40px",
            easing: "ease-out",
            origin: "left",
            reset: true,
            viewFactor: 0.5,
            delay: 0,
        });
        
        sr.reveal(".missing-info", {
            duration: 500,
            distance: "40px",
            easing: "ease-out",
            origin: "left",
            reset: true,
            viewFactor: 0.5,
            delay: 0,
        });

    }, [])


    return (
        <div className="grid md:grid-cols-3 md:grid-rows-1 gap-6 m-6 container mx-auto mt-20">
            <div className="grid-item row-span-1 profile-info shadow rounded-3xl p-6">
                <img src={userLogo} alt="User pic" className="mx-auto w-28 h-28"/>
                <h1 className="text-5xl text-center">{"John Doe"}</h1>

                <h1 className="text-3xl mt-3">Contact</h1>
                <table className="w-full">
                    <tbody>
                        <tr>
                            <td>Email</td>
                            <td className="text-right">{"example@gmail.com"}</td>
                        </tr>
                        <tr>
                            <td>Phone number</td>
                            <td className="text-right">{"+61 234 567"}</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div className="grid-item col-span-2 missing p-6 shadow rounded-3xl missing-info">
                <h1 className="text-3xl">I am looking for...</h1>
                <div className="overflow-x-auto flex flex-row gap-4 mt-3">
                    <Card name={ex_info.name} 
                        lastKnownLocation={ex_info.lastKnownLocation} 
                        appearanceDescription={ex_info.appearanceDescription}
                        sourceOfReport={ex_info.sourceOfReport}
                        uuid={ex_info.uuid}
                        age={ex_info.age}
                        gender={ex_info.gender}
                        imgSrc={ex_info.imgSrc}
                    />
                    <Card name={ex_info.name} 
                        lastKnownLocation={ex_info.lastKnownLocation} 
                        appearanceDescription={ex_info.appearanceDescription}
                        sourceOfReport={ex_info.sourceOfReport}
                        uuid={ex_info.uuid}
                        age={ex_info.age}
                        gender={ex_info.gender}
                        imgSrc={ex_info.imgSrc}
                    />
                    <Card name={ex_info.name} 
                        lastKnownLocation={ex_info.lastKnownLocation} 
                        appearanceDescription={ex_info.appearanceDescription}
                        sourceOfReport={ex_info.sourceOfReport}
                        uuid={ex_info.uuid}
                        age={ex_info.age}
                        gender={ex_info.gender}
                        imgSrc={ex_info.imgSrc}
                    />
                    <Card name={ex_info.name} 
                        lastKnownLocation={ex_info.lastKnownLocation} 
                        appearanceDescription={ex_info.appearanceDescription}
                        sourceOfReport={ex_info.sourceOfReport}
                        uuid={ex_info.uuid}
                        age={ex_info.age}
                        gender={ex_info.gender}
                        imgSrc={ex_info.imgSrc}
                    />
                </div>
                
            </div>
        </div>
    )


}

export default Account