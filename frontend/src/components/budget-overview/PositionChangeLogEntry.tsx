import {PositionChange} from "../../model/PositionChange";
import {formatMoney} from "../../service/utils/formattingHelpers";
import "./styles/PositionChangeLogEntry.css"

type PositionChangeLogEntryProps = {
    change: PositionChange
}

export default function PositionChangeLogEntry({change}:PositionChangeLogEntryProps){
    return <div className={"position-change-log-entry"}>
        <div className={"change-metadata"}>
            <div>Changed by: {change.userInfo}</div>
            <div>Changed at: {new Date(change.timestamp).toLocaleString()}</div>
            <div>Method: {change.method.toLowerCase()}</div>
        </div>
        <div className={"change-data"}>
            <div className={"change-data-text"}>
                <div className={"change-data-name"}>{change.data.name}</div>
                <div>Description: {change.data.description}</div>
            </div>
            <div className={"change-data-numbers"}>
                <div>Amount: {change.data.amount}</div>
                <div>Tax: {change.data.tax} %</div>
                <div>Net price: {formatMoney(change.data.price)} €</div>
            </div>
        </div>
    </div>
}