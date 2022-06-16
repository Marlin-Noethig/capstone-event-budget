import {PositionChange} from "../../model/PositionChange";
import {formatMoney} from "../../service/utils/formattingHelpers";
import "./styles/PositionChangeLogEntry.css"
import {Position} from "../../model/Position";

type PositionChangeLogEntryProps = {
    change: PositionChange
    toggleShowLog: () => void
    updatePosition?: (id: string, newPosition: Omit<Position, "id">) => void
    addNewPosition?: (newPosition: Omit<Position, "id">) => void
}

export default function PositionChangeLogEntry({change, toggleShowLog, updatePosition, addNewPosition}:PositionChangeLogEntryProps){

    const onRestore = () => {
        const positionToRestore = {
            name: change.data.name,
            description: change.data.description,
            amount: change.data.amount,
            price: change.data.price,
            tax: change.data.tax,
            subCategoryId: change.subCategoryId,
            eventId: change.data.eventId
        }

        if (updatePosition){
            updatePosition(change.data.id, positionToRestore);
        }
        if (addNewPosition){
            addNewPosition(positionToRestore)
        }
        toggleShowLog();
    }


    return <div className={"position-change-log-entry"}>
        <div className={"position-change-log-entry-wrapper"}>
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
        <div className={"restore-position"} onClick={onRestore}>RESTORE</div>
    </div>
}