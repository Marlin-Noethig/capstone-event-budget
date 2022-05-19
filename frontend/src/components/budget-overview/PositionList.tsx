import {Position} from "../../model/Position";
import PositionCard from "./PositionCard";

type PositionListProps = {
    positions: Position[],
    deletePosition: (id: string) => void
}

export default function PositionList({positions, deletePosition}: PositionListProps){

    return(
        <div className={"position-list-container"}>
            {positions.map(position => <PositionCard
                key={position.id}
                position={position}
                deletePosition={deletePosition}/>)}
        </div>
    )
}