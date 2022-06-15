import {User} from "../../model/User";
import {ChangeEvent, FormEvent, useState} from "react";

type AssignUsersToMainCatProps = {
    idOfMainCategory: string
    users: User[],
    assignedUsers: string[],
    updateMainCategory: (id: string, updatedUserIds: string[]) => void,
    toggleEnableAssignUsers: () => void
}
export default function AssignUsersToMainCat({
                                                 idOfMainCategory,
                                                 toggleEnableAssignUsers,
                                                 assignedUsers,
                                                 updateMainCategory,
                                                 users
                                             }: AssignUsersToMainCatProps) {

    const [userIds, setUserIds] = useState<string[]>(assignedUsers)

    const handleUserIdsChange = (e: ChangeEvent<HTMLSelectElement>) => {
        let value = Array.from(e.target.selectedOptions, option => option.value)
        setUserIds(value)
    }

    const onSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        updateMainCategory(idOfMainCategory, userIds)
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