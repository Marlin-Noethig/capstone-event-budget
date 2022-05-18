import {Position} from "../../model/Position";
import PositionCard from "./PositionCard";

type PositionListProps = {
    positions: Position[]
}

export default function PositionList({positions}: PositionListProps) {

    return (
        <div className={"position-list-container"}>
            {positions.map(position => <PositionCard
                key={position.id}
                position={position}/>)}
        </div>
    )
}