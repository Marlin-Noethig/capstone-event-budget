import {User} from "../../model/User";
import AdminUserView from "./AdminUserView";

type AdminViewUsersListProps = {
    users: User[]
}

export default function AdminViewUsersList({users}: AdminViewUsersListProps) {

    const usersSortedByCompanyAndFirstName = [...users].sort((a, b) => {
        return a.company.localeCompare(b.company) || a.firstName.localeCompare(b.firstName)
    })

    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Users</div>
        {usersSortedByCompanyAndFirstName.map(user => <AdminUserView key={user.id} user={user}/>)}
    </div>)
}