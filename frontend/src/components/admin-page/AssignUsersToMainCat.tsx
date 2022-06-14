import {User} from "../../model/User";
import {ChangeEvent, FormEvent, useState} from "react";

type AssignUsersToMainCatProps = {
    users: User[]
    assignedUsers: string[]
    toggleEnableAssignUsers: () => void
}
export default function AssignUsersToMainCat({
                                                 toggleEnableAssignUsers,
                                                 assignedUsers,
                                                 users
                                             }: AssignUsersToMainCatProps) {

    const [userIds, setUserIds] = useState<string[]>(assignedUsers)

    const handleUserIdsChange = (e: ChangeEvent<HTMLSelectElement>) => {
        let value = Array.from(e.target.selectedOptions, option => option.value)
        setUserIds(value)
    }

    const onSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log(userIds);
        toggleEnableAssignUsers();
    }

    return (<div>
        <form onSubmit={onSubmit}>
            <select multiple={true} value={userIds} onChange={handleUserIdsChange}>
                {users.map(user => <option key={user.id} value={user.id}
                                           defaultChecked={userIds.includes(user.id)}>{`${user.firstName} ${user.lastName} (${user.company})`}</option>)}
            </select>
            <input type="submit" value={"save"}/>
        </form>
    </div>)
}