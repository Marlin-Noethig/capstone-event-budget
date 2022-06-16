import {Position} from "../../model/Position";
import {netToGross} from "../../service/utils/taxHelpers";
import "./styles/ViewPosition.css"
import {formatMoney} from "../../service/utils/formattingHelpers";
import DeletionDialogue from "../DeletionDialogue";

type ViewPositionProps = {
    position: Position
    deletePosition: (id: string, name: string) => void
    toggleEnableEdit: () => void
}

export default function ViewPosition({position, deletePosition, toggleEnableEdit}:ViewPositionProps){

    return(
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
                <button onClick={toggleEnableEdit}>edit</button>
                <DeletionDialogue position={position} deleteFunction={deletePosition}/>
            </div>
        </div>
    )
}