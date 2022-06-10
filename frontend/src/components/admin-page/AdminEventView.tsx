import {EventData} from "../../model/EventData";
import "./styles/AdminEventView.css";

type AdminEventViewProps = {
   event: EventData
}

export default function AdminEventView({event}: AdminEventViewProps) {
    return(
        <div className={"admin-list-item"}>
            <div>{event.name}</div>
            <div className={"event-dates"}>{`${new Date(event.startDate).toLocaleDateString()} to ${new Date(event.endDate).toLocaleDateString()}`}</div>
        </div>
    )
}