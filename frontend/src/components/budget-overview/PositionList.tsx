import WritePosition from "./WritePosition";
import {useState} from "react";
import PositionCard from "./PositionCard";
import PositionListHead from "./PositionListHead";
import {Position} from "../../model/Position";

type PositionListProps = {
    positions: Position[]
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string) => void
    updatePosition: (id: string,newPosition: Omit<Position, "id">) => void
}

export default function PositionList({positions, addNewPosition, deletePosition, updatePosition}:PositionListProps) {

    const [enableAdd, setEnableAdd] = useState<boolean>(false);

    const toggleEnableAdd = () => {
        setEnableAdd(!enableAdd)
    }

    return (
        <div className={"position-list-container"}>
            <PositionListHead/>
            {positions.map(position => <PositionCard
                key={position.id}
                position={position}
                deletePosition={deletePosition}
                updatePosition={updatePosition}/>)}
            {enableAdd ? <WritePosition addNewPosition={addNewPosition}
                                        toggleEnableAdd={toggleEnableAdd}
            /> : <button onClick={toggleEnableAdd}>add new</button>}
        </div>
    )
}