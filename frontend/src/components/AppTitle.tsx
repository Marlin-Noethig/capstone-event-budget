import "./styles/AppTitle.css"
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";


export default function AppTitle() {

    const {logout, token} = useContext(AuthContext);

    return (
        <div className={"app-title-container"}>
            <div> </div>
            <div className={"app-title"}>EventBudget</div>
            <div className={"logout-button-wrapper"}>
                {token && <button className={"logout-button"} onClick={logout}>Logout</button>}
            </div>
        </div>

    )
}