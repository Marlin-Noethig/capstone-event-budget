import WritePosition from "./WritePosition";
import {useState} from "react";
import PositionCard from "./PositionCard";
import PositionListHead from "./PositionListHead";
import usePositions from "../../hooks/usePositions";

export default function PositionList() {

    const {positions, addNewPosition, updatePositionById, removePositionById} = usePositions();
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
                deletePosition={removePositionById}
                updatePosition={updatePositionById}/>)}
            {enableAdd ? <WritePosition mode={"ADD"}
                                        addNewPosition={addNewPosition}
                                        toggleEnableAdd={toggleEnableAdd}
            /> : <button onClick={toggleEnableAdd}>add new</button>}
        </div>
    )
}