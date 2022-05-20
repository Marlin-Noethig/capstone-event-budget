import ViewPosition from "./ViewPosition";
import {Position} from "../../model/Position";
import {useState} from "react";
import WritePosition from "./WritePosition";

type PositionCardProps = {
    position: Position
    deletePosition: (id: string) => void
    updatePosition: (id: string,newPosition: Omit<Position, "id">) => void
}


export default function PositionCard({position, deletePosition, updatePosition}: PositionCardProps) {

    const [enableEdit, setEnableEdit] = useState<boolean>(false);

    const toggleEnableEdit = () => {
        setEnableEdit(!enableEdit)
    }

    return (
        <div className={"position-container"}>
            {enableEdit ? <WritePosition mode={"EDIT"}
                                         position={position}
                                         toggleEnableEdit={toggleEnableEdit}
                                         updatePosition={updatePosition}
            /> : <ViewPosition position={position}
                               deletePosition={deletePosition}
                               toggleEnableEdit={toggleEnableEdit}
            />
            }

        </div>
    )
}