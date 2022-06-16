import usePositionChanges from "../../hooks/usePositionChanges";
import PositionChangeLogEntry from "./PositionChangeLogEntry";
import {PositionChange} from "../../model/PositionChange";

type PositionChangeLogProps = {
    positionId?: string
    subCategoryId: string
    idOfEvent: string
}

export default function PositionChangeLog({positionId, subCategoryId, idOfEvent}: PositionChangeLogProps){

    const {positionChanges} = usePositionChanges(subCategoryId);

    let filteredChanges: PositionChange[]

    if (positionId){
        filteredChanges = positionChanges.filter(change => change.positionId === positionId).filter(change => change.data.eventId === idOfEvent)
    } else {
        filteredChanges = positionChanges.filter(change => change.method === "DELETE").filter(change => change.data.eventId === idOfEvent)
    }

    if (filteredChanges.length < 1) {
        return (
            <div>No changes occurred yet.</div>
        )
    }

    return(
        <div className={"position-change-log"}>
            {filteredChanges.map(change => <PositionChangeLogEntry key={change.id} change={change}/>)}
        </div>
    )
}