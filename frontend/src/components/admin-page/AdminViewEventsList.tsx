import {EventData} from "../../model/EventData";
import AdminEventView from "./AdminEventView";
import {useNavigate} from "react-router-dom";

type AdminViewEventListProps = {
    events: EventData[]
}

export default function AdminViewEventsList({events}:AdminViewEventListProps) {
    const navigate = useNavigate();

    return (<div className={"admin-view-list"}>
        <div className={"admin-view-list-title"}>Events</div>
        {events.map(event => <AdminEventView key={event.id} event={event}/>)}
        <button onClick={() => navigate("/events/new")}>+</button>
    </div>)
}