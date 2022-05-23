import "./styles/PositionListHead.css"

export default function PositionListHead(){
    return(
        <div className={"position-list-head"}>
            <ul className={"overview-grid-item"}>
                <li>Name</li>
                <li>Description</li>
                <li>Amount</li>
                <li>Tax</li>
                <li>Net Price</li>
                <li>Gross Price</li>
                <li>SUM</li>
            </ul>
        </div>
    )
}