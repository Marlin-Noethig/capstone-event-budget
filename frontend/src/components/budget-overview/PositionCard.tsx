import ViewPosition from "./ViewPosition";
import {Position} from "../../model/Position";
import {useState} from "react";
import WritePosition from "./WritePosition";

type PositionCardProps = {
    position: Position
    deletePosition: (id: string, name:string) => void
    updatePosition: (id: string,newPosition: Omit<Position, "id">) => void
    subCategoryId: string
}


export default function PositionCard({position, deletePosition, updatePosition, subCategoryId}: PositionCardProps) {

    const [enableEdit, setEnableEdit] = useState<boolean>(false);

    const toggleEnableEdit = () => {
        setEnableEdit(!enableEdit)
    }

    const setEnableEditToTrue = () => {
        setEnableEdit(true)
    }

    return (
        <div className={"position-container"} onDoubleClick={setEnableEditToTrue}>
            {enableEdit ? <WritePosition position={position}
                                         toggleEnableEdit={toggleEnableEdit}
                                         updatePosition={updatePosition}
                                         subCategoryId={subCategoryId}
            /> : <ViewPosition position={position}
                               deletePosition={deletePosition}
                               toggleEnableEdit={toggleEnableEdit}
            />
            }
        </div>
    )
}