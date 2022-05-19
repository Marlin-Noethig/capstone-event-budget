import "./styles/EventDetailView.css"


export default function EventDetailView(){

    //these are dummy-values for future data of specific events which will be fetched from the api
    const eventName: string = ":/localfestival:3000/";
    const maximumCapacity: number = 2000;
    const fromDate: string = "21.04.2023"
    const untilDate: string = "23.04.30"

    return(
        <div className={"event-detail-view-container"}>
            <p className={"event-detail-name"}>Event: {eventName}</p>
            <p>Max. Capacity: {maximumCapacity}</p>
            <p>From: {fromDate}</p>
            <p>Until: {untilDate}</p>
        </div>
    )
}
