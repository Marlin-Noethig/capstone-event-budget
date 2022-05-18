import {Position} from "../../model/Position";

type PositionCardProps = {
    position: Position
}

export default function PositionCard({position}:PositionCardProps){
    return(
        <div className={"position-item-container"}>
            <ul>
                <li>name: {position.name}</li>
                <li>description: {position.description}</li>
                <li>amount: {position.amount}</li>
                <li>net price: {position.price}€</li>
                <li>tax: {position.tax}%</li>
                <li>sum: {position.price * position.amount}€</li>
            </ul>
        </div>
    )
}