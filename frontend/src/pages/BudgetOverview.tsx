import PositionList from "../components/budget-overview/PositionList";
import EventDetailView from "../components/budget-overview/EventDetailView";

export default function BudgetOverview(){

    return(
        <div className={"budget-overview-container"}>
            <EventDetailView/>
            <PositionList/>
        </div>
    )
}