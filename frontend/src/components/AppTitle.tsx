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

    return (
        <div className={"app-title-container"}>
                {currentUser ?
                    <div className={"display-current-user"}>{`${currentUser.firstName.toUpperCase()} ${currentUser.lastName.toUpperCase()}`} </div>
                    :
                    <div> </div>
                }
            {!token && <div className={"app-title"}>EventBudget</div>}
            {token ? <NavigationBar isAdmin={initiateIsAdmin()} logout={logout}/> : <div> </div>}
        </div>
    )
}