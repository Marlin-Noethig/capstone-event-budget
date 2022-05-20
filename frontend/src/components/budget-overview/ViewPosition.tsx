import {Position} from "../../model/Position";
import {netToGross} from "../../service/utils/taxHelpers";
import "./styles/ViewPosition.css"

type ViewPositionProps = {
    position: Position
    deletePosition: (id: string) => void
    toggleEnableEdit: () => void
}

export default function ViewPosition({position, deletePosition, toggleEnableEdit}:ViewPositionProps){

    const onDelete = () =>{
        deletePosition(position.id)
    }

    return(
        <div className={"position-item-container"}>
            <ul>
                <li>name: {position.name}</li>
                <li>description: {position.description}</li>
                <li>amount: {position.amount}</li>
                <li>net price: {position.price}€</li>
                <li>gross price: {netToGross(position.price, position.tax)}€</li>
                <li>tax: {position.tax}%</li>
                <li>sum: {position.price * position.amount}€</li>
            </ul>
            <div className={"position-view-buttons-container"}>
                <button className={"edit-position-button"} onClick={toggleEnableEdit}>edit</button>
                <button className={"delete-position-button"} onClick={onDelete}>delete</button>
            </div>
        </div>
    )
}