import {Position} from "../../model/Position";
import {netToGross} from "../../service/utils/taxHelpers";
import "./styles/ViewPosition.css"
import {formatMoney} from "../../service/utils/formattingHelpers";
import DeletionDialogue from "../DeletionDialogue";
import {useState} from "react";
import PositionChangeLog from "./PositionChangeLog";

type ViewPositionProps = {
    position: Position
    deletePosition: (id: string, name: string) => void
    toggleEnableEdit: () => void
    subCategoryId: string
    idOfEvent: string
}

export default function ViewPosition({
                                         position,
                                         deletePosition,
                                         toggleEnableEdit,
                                         subCategoryId,
                                         idOfEvent
                                     }: ViewPositionProps) {

    const [showLog, setShowLog] = useState<boolean>(false);

    const toggleShowLog = () => {
        setShowLog(!showLog)
    }

    return (<div>

            <div className={"position-item-container"}>
                <ul className={"overview-grid-item"}>
                    <li>{position.name}</li>
                    <li>{position.description}</li>
                    <li>{position.amount}</li>
                    <li>{position.tax}%</li>
                    <li>{formatMoney(position.price)}€</li>
                    <li>{formatMoney(netToGross(position.price, position.tax))}€</li>
                    <li>{formatMoney((position.price * position.amount))}€</li>
                </ul>
                <div className={"position-view-buttons-container"}>
                    <button onClick={toggleShowLog}>log</button>
                    <button onClick={toggleEnableEdit}>edit</button>
                    <DeletionDialogue position={position} deleteFunction={deletePosition}/>
                </div>
            </div>
            {showLog && <PositionChangeLog positionId={position.id}
                                           subCategoryId={subCategoryId}
                                           idOfEvent={idOfEvent}/>}
        </div>

    )
}