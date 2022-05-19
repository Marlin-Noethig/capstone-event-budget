import usePositions from "../hooks/usePositions";
import PositionList from "../components/budget-overview/PositionList";
import EventDetailView from "../components/budget-overview/EventDetailView";
import WritePosition from "../components/budget-overview/WritePosition";

export default function BudgetOverview(){

    const positions = usePositions();



    return(
        <div className={"budget-overview-container"}>
            <EventDetailView/>
            <PositionList positions={positions}/>
            <WritePosition/>
        </div>

    )
}