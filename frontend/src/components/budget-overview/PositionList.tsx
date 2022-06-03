import WritePosition from "./WritePosition";
import PositionCard from "./PositionCard";
import PositionListHead from "./PositionListHead";
import {Position} from "../../model/Position";
import "./styles/PositionList.css"

type PositionListProps = {
    positions: Position[],
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string, name: string) => void,
    updatePosition: (id: string, newPosition: Omit<Position, "id">) => void,
    subCategoryId: string,
    enableAdd: boolean
    toggleEnableAdd: () => void
    idOfEvent: string
}

export default function PositionList({
                                         positions,
                                         addNewPosition,
                                         deletePosition,
                                         updatePosition,
                                         subCategoryId,
                                         enableAdd,
                                         toggleEnableAdd,
                                         idOfEvent
                                     }: PositionListProps) {


    return (
        <div className={"position-list-container"}>
            <PositionListHead toggleEnableAdd={toggleEnableAdd}/>
            {positions.map(position => <PositionCard
                key={position.id}
                position={position}
                deletePosition={deletePosition}
                updatePosition={updatePosition}
                subCategoryId={subCategoryId}
                idOfEvent={idOfEvent}/>)}
            {enableAdd && <WritePosition addNewPosition={addNewPosition}
                                         toggleEnableAdd={toggleEnableAdd}
                                         subCategoryId={subCategoryId}
                                         idOfEvent={idOfEvent}
            />}
        </div>
    )
}