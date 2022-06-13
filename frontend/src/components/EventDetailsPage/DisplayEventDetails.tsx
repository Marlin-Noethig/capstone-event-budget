import {EventData} from "../../model/EventData";
import {User} from "../../model/User";
import "./styles/DisplayEventDetails.css"

type DisplayEventDetailsProps = {
    event: EventData
    users: User[]
}

export default function DisplayEventDetails({event, users}:DisplayEventDetailsProps) {

    const assignedUsers = users.filter(user => user.role === "USER").filter(user => event.userIds.includes(user.id))

    return (<div className={"display-event-container"}>
        <div className={"event-infos-container"}>
            <div className={"event-infos-heading"}>{event.name}</div>
            <div>From: {new Date(event.startDate).toLocaleDateString()}</div>
            <div>Until: {new Date(event.endDate).toLocaleDateString()}</div>
            <div>Maximum capacity: {event.guests}</div>
        </div>
        <div className={"collaborators-container"}>
            <div className={"event-infos-heading"}>Assigned collaborators</div>
            {assignedUsers.map(user => <div>{`${user.firstName} ${user.lastName} (${user.company})`}</div>)}
        </div>
    </div>)
}