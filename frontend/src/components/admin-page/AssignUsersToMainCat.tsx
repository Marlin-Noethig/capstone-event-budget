import {User} from "../../model/User";
import {ChangeEvent, FormEvent, useState} from "react";
import "./styles/AssignUsersToMainCat.css"

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
        let currentIds = userIds
        if (!userIds.includes(e.target.value)){
            currentIds.push(e.target.value)
            setUserIds(currentIds)
        } else  {
            setUserIds(currentIds.filter(id => id !== e.target.value))
        }
    }

    const onSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        updateMainCategory(idOfMainCategory, userIds)
        toggleEnableAssignUsers();
    }

    return (<div>
        <form className={"assign-to-main-form"} onSubmit={onSubmit}>
            <select multiple={true} value={userIds} onChange={handleUserIdsChange}>
                {users.map(user => <option key={user.id} value={user.id}
                                           defaultChecked={userIds.includes(user.id)}>{`${user.firstName} ${user.lastName} (${user.company})`}</option>)}
            </select>
            <div className={"remove-all-users"} onClick={() => setUserIds([])}>remove all users</div>
            <input className={"submit-button"} type="submit" value={"save"}/>
        </form>
    </div>)
}