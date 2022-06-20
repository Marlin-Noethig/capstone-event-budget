import {User} from "../../model/User";
import {EventData} from "../../model/EventData";
import {ChangeEvent, FormEvent, useState} from "react";
import {formatDateToIsoString} from "../../service/utils/formattingHelpers";
import {toast} from "react-toastify";
import "./styles/WriteEvent.css";

type WriteEventProps = {
    users: User[]
    event: EventData | undefined
    addEvent: (newEvent: Omit<EventData, "id">) => void,
    updateEvent: (id: string, eventToUpdate: Omit<EventData, "id">) => void,
    toggleEnableEdit: () => void
}

export default function WriteEvent({users, event, addEvent, updateEvent, toggleEnableEdit}: WriteEventProps) {

    const [name, setName] = useState<string>(event ? event.name : "")
    const [startDate, setStartDate] = useState<string>(event ? formatDateToIsoString(event.startDate) : "")
    const [endDate, setEndDate] = useState<string>(event ? formatDateToIsoString(event.endDate) : "")
    const [guests, setGuests] = useState<number>(event ? event.guests : 0)
    const [userIds, setUserIds] = useState<string[]>(event ? event.userIds : [])

    const handleUserIdsChange = (e: ChangeEvent<HTMLSelectElement>) => {
        let currentIds = userIds
        if (!userIds.includes(e.target.value)) {
            currentIds.push(e.target.value)
            setUserIds(currentIds)
        } else {
            setUserIds(currentIds.filter(id => id !== e.target.value))
        }
    }

    const onSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!name) {
            toast.warn("Name must be set")
            return
        }
        if (!startDate) {
            toast.warn("Starting date must be set")
            return
        }
        if (!endDate) {
            toast.warn("Ending date must be set")
            return
        }
        if (endDate < startDate){
            toast.warn("End date must be after start date.")
            return
        }
        if (guests <= 0 ){
            toast.warn("Guests must be more than 0.")
            return
        }

        const eventValues = {
            name: name,
            startDate: new Date(startDate),
            endDate: new Date(endDate),
            guests: guests,
            userIds: userIds
        }

        if (event) {
            updateEvent(event.id, eventValues)
            toggleEnableEdit()
        } else {
            addEvent(eventValues)
        }

    }

    return (
        <div className={"write-event-form-container"}>
            <form className={"write-event-form"} onSubmit={onSubmit}>
                <div className={"write-event-form-fields"}>
                    <label>Name: </label>
                    <input type={"text"} placeholder={"Name of new event"} value={name}
                           onChange={e => setName(e.target.value)}/>

                    <label>Start Date: </label>
                    <input type={"date"} value={startDate} onChange={e => setStartDate(e.target.value)}/>

                    <label>End Date: </label>
                    <input type={"date"} value={endDate} onChange={e => setEndDate(e.target.value)}/>

                    <label>Maximum Guests: </label>
                    <input type={"number"} value={guests} min={0} step={1}
                           onChange={e => setGuests(Number(e.target.value))}/>

                    <label>Assigned Collaborators: </label>
                    <select multiple={true} value={userIds} onChange={handleUserIdsChange}>
                        {users.map(user => <option key={user.id} value={user.id}
                                                   defaultChecked={userIds.includes(user.id)}>{`${user.firstName} ${user.lastName} (${user.company})`}</option>)}
                    </select>
                    <div className={"remove-all-users"} onClick={() => setUserIds([])}>REMOVE ALL USERS</div>
                </div>
                {event ?
                    <input className={"submit-button"} type="submit" value={"save"}/>
                    :
                    <input className={"submit-button"} type="submit" value={"add"}/>
                }
            </form>
        </div>
    )
}