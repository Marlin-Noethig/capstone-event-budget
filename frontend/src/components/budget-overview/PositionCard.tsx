import {Position} from "../../model/Position";
import {netToGross} from "../../service/utils/taxHelpers";
import "./styles/PositionCard.css"

type PositionCardProps = {
    position: Position
    deletePosition: (id: string) => void
}

export default function PositionCard({position, deletePosition}:PositionCardProps){

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
            <button className={"delete-position-button"} onClick={onDelete}>delete</button>
        </div>
    )
}