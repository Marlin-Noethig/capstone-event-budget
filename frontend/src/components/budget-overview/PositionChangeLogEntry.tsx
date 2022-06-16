import {PositionChange} from "../../model/PositionChange";
import {formatMoney} from "../../service/utils/formattingHelpers";

type PositionChangeLogEntryProps = {
    change: PositionChange
}

export default function PositionChangeLogEntry({change}:PositionChangeLogEntryProps){
    return <div className={"position-change-log-entry"}>
        <div className={"change-metadata"}>
            <div>Changed by: {change.userInfo}</div>
            <div>Changed at: {new Date(change.timestamp).toLocaleString()}</div>
            <div>Method: {change.method}</div>
        </div>
        <div className={"change-data"}>
            <div>Name: {change.data.name}</div>
            <div>Description: {change.data.description}</div>
            <div>Amount: {change.data.amount}</div>
            <div>Tax: {change.data.tax} %</div>
            <div>Net price: {formatMoney(change.data.price)} €</div>
        </div>
    </div>
}