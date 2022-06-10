import {User} from "../../model/User";
import "./styles/AdminUserView.css"

type AdminUserViewProps = {
    user: User
}

export default function AdminUserView({user}:AdminUserViewProps){
    return(
        <div className={"admin-list-item"}>
            <div className={"full-user-name"}>{user.firstName + " " + user.lastName}</div>
            <div className={"user-infos"}>
                <div className={"user-company"}>{user.company}</div>
                <div className={"user-role"}>current role: {user.role}</div>
            </div>
        </div>
    )
}