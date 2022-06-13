import {User} from "../../model/User";
import {EventData} from "../../model/EventData";
import {ChangeEvent, FormEvent, useState} from "react";
import {formatDateToIsoString} from "../../service/utils/formatingHelpers";
import {toast} from "react-toastify";

type WriteEventProps = {
    users: User[]
    event: EventData | undefined
    addEvent: (newEvent: Omit<EventData, "id">) => void,
    updateEvent: (id: string, eventToUpdate: Omit<EventData, "id">) => void,
    toggleEnableEdit: () => void
}

export default function WriteEvent({users, event, addEvent, updateEvent, toggleEnableEdit}: WriteEventProps) {

    const [name, setName] = useState<string>( event ? event.name : "")
    const [startDate, setStartDate] = useState<string>( event ? formatDateToIsoString(event.startDate) : "")
    const [endDate, setEndDate] = useState<string>( event ? formatDateToIsoString(event.endDate) : "")
    const [guests, setGuests] = useState<number>(event ? event.guests : 0)
    const [userIds, setUserIds] = useState<string[]>(event ? event.userIds : [])

    const handleUserIdsChange = (e: ChangeEvent<HTMLSelectElement>) => {
        let value = Array.from(e.target.selectedOptions, option => option.value)
        setUserIds(value)
    }

    const onSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!name) {
            toast.warn("Name must be set")
            return
        }
        if (!startDate){
            toast.warn("Starting date must be set")
            return
        }
        if (!endDate){
            toast.warn("Ending date must be set")
            return
        }

        const eventValues = {
            name: name,
            startDate: new Date(startDate),
            endDate: new Date(endDate),
            guests: guests,
            userIds: userIds
        }

        if(event) {
            updateEvent(event.id, eventValues)
            toggleEnableEdit()
        } else {
            addEvent(eventValues)
        }

    }

    return (
        <div>
            <form onSubmit={onSubmit}>
                <input type={"text"} value={name} onChange={e => setName(e.target.value)}/>
                <input type={"date"} value={startDate} onChange={e => setStartDate(e.target.value)}/>
                <input type={"date"} value={endDate} onChange={e => setEndDate(e.target.value)}/>
                <input type={"number"} value={guests} min={0} step={1}
                       onChange={e => setGuests(Number(e.target.value))}/>
                <select multiple={true} value={userIds} onChange={handleUserIdsChange}>
                    {users.map(user => <option key={user.id} value={user.id} defaultChecked={userIds.includes(user.id)}>{`${user.firstName} ${user.lastName} (${user.company})`}</option>)}
                </select>
                {event ?
                    <input type="submit" value={"save"}/>
                    :
                    <input type="submit" value={"add"}/>
                }

            </form>
        </div>
    )
}