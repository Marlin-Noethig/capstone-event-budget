import {EventData} from "../../model/EventData";
import "./styles/AdminEventView.css";
import {useNavigate} from "react-router-dom";

type AdminEventViewProps = {
   event: EventData
}

export default function AdminEventView({event}: AdminEventViewProps) {
    const navigate = useNavigate();


    return(
        <div className={"admin-list-item"}>
            <div className={"admin-event-view-title"} onClick={() => navigate(`/events/${event.id}`)}>{event.name}</div>
            <div className={"event-dates"}>{`${new Date(event.startDate).toLocaleDateString()} to ${new Date(event.endDate).toLocaleDateString()}`}</div>
        </div>
    )
}