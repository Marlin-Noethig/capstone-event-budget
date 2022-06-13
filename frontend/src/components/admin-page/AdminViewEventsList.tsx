import {EventData} from "../../model/EventData";
import AdminEventView from "./AdminEventView";

type AdminViewEventListProps = {
    events: EventData[]
}

export default function AdminViewEventsList({events}:AdminViewEventListProps) {
    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Events</div>
        {events.map(event => <AdminEventView key={event.id} event={event}/>)}
    </div>)
}