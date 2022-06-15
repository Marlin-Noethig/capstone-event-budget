import "./styles/AppTitle.css"
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";
import {Link} from "react-router-dom";


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
            <div>
                {currentUser ?
                    <div className={"display-current-user"}>{`Hey, ${currentUser.firstName} ${currentUser.lastName}!`} </div>
                    :
                    <></>
                }
            </div>
            <div className={"app-title"}>EventBudget</div>
            <div className={"logout-button-wrapper"}>
                {token && isAdmin && <Link to={"/admin"}>Admin</Link>}
                {token && <button className={"logout-button"} onClick={logout}>Logout</button>}
            </div>
        </div>
    )
}