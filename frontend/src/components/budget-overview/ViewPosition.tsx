import {Position} from "../../model/Position";
import {netToGross} from "../../service/utils/taxHelpers";
import "./styles/ViewPosition.css"
import {toast} from "react-toastify";
import {formatMoney} from "../../service/utils/beatifyHelpers";

type ViewPositionProps = {
    position: Position
    deletePosition: (id: string) => void
    toggleEnableEdit: () => void
}

export default function ViewPosition({position, deletePosition, toggleEnableEdit}:ViewPositionProps){

    const onDelete = () =>{
        deletePosition(position.id)
        toast.success(`${position.name} has been deleted.`)
    }

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
                <button className={"edit-position-button"} onClick={toggleEnableEdit}>edit</button>
                <button className={"delete-position-button"} onClick={onDelete}>delete</button>
            </div>
        </div>
    )
}