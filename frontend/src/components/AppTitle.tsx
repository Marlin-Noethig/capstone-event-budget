import "./styles/AppTitle.css"
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import NavigationBar from "./NavigationBar";


export default function AppTitle() {

    const {logout, token, currentUser} = useContext(AuthContext);

    const initiateIsAdmin = () => {
        if (currentUser){
            return currentUser.role === "ADMIN"
        }
        return false;
    }

    const isAdmin: boolean = initiateIsAdmin();

    return (
        <div className={"app-title-container"}>
                {currentUser ?
                    <div className={"display-current-user"}>{`${currentUser.firstName.toUpperCase()} ${currentUser.lastName.toUpperCase()}`} </div>
                    :
                    <div> </div>
                }
            <div className={"app-title"}>EventBudget</div>
            {token ? <NavigationBar isAdmin={isAdmin} logout={logout}/> : <div> </div>}
        </div>
    )
}