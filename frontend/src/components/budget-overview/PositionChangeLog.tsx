import usePositionChanges from "../../hooks/usePositionChanges";
import PositionChangeLogEntry from "./PositionChangeLogEntry";

type PositionChangeLogProps = {
    positionId: string
    subCategoryId: string
    idOfEvent: string
}

export default function PositionChangeLog({positionId, subCategoryId, idOfEvent}: PositionChangeLogProps){

    const {positionChanges} = usePositionChanges(subCategoryId);

    const filteredChanges = positionChanges.filter(change => change.positionId === positionId).filter(change => change.data.eventId === idOfEvent)

    if (filteredChanges.length < 1) {
        return (
            <div>No changes occurred yet.</div>
        )
    }

    return(
        <div className={"position-change-log"}>
            {filteredChanges.map(change => <PositionChangeLogEntry change={change}/>)}
        </div>
    )
}