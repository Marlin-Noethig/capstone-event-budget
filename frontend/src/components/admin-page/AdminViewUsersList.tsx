import {User} from "../../model/User";

type AAdminViewUsersListProps = {
    users: User[]
}

export default function AdminViewUsersList({users}: AAdminViewUsersListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Users</div>
        <ul>
            {users.sort((a, b) => a.company.localeCompare(b.company))
                .sort((a, b) => a.firstName.localeCompare(b.firstName))
                .map(user =>
                    <li>{`${user.firstName} ${user.lastName} (${user.company}), Role: ${user.role}`}</li>)}
        </ul>
    </div>)
}