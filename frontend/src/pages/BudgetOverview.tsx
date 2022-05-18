import usePositions from "../hooks/usePositions";
import PositionList from "../components/budget-overview/PositionList";

export default function BudgetOverview(){

    const positions = usePositions();

    console.log(positions);


    return(
        <PositionList positions={positions}/>
    )
}