import {Position} from "../../model/Position";
import WritePosition from "./WritePosition";
import {useState} from "react";
import PositionCard from "./PositionCard";
import PositionListHead from "./PositionListHead";

type PositionListProps = {
    positions: Position[],
    deletePosition: (id: string) => void,
    addNewPosition: (newPosition: Omit<Position, "id">) => void
    updatePosition: (id: string,newPosition: Omit<Position, "id">) => void
}

export default function PositionList({positions, deletePosition, addNewPosition, updatePosition}: PositionListProps) {

    const [enableAdd, setEnableAdd] = useState<boolean>(false)

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
            {enableAdd ? <WritePosition mode={"ADD"}
                                        addNewPosition={addNewPosition}
                                        toggleEnableAdd={toggleEnableAdd}
            /> : <button onClick={toggleEnableAdd}>add new</button>}
        </div>
    )
}