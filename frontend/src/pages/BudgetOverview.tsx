import usePositions from "../hooks/usePositions";
import PositionList from "../components/budget-overview/PositionList";

export default function BudgetOverview(){

    const positions = usePositions();



    return(
        <PositionList positions={positions}/>
    )
}