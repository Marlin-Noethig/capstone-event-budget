import {EventData} from "../../model/EventData";

type AdminViewEventListProps = {
    events: EventData[]
}

export default function AdminViewEventsList({events}:AdminViewEventListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Events</div>
        <ul>
            {events.map(event => <li>{event.name}</li>)}
        </ul>
    </div>)
}