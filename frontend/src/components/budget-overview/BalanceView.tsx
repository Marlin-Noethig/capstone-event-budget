import "./styles/BalanceView.css"
import {formatMoney} from "../../service/utils/formattingHelpers";

type BalanceViewProps = {
    sum: number
    breakEven?: number
    margin?: number
    onBudgedList: boolean
}

export default function BalanceView({sum, breakEven, margin, onBudgedList}: BalanceViewProps) {

    const negativeBalanceClassName = (sum < 0) && "negative";
    const negativeMarginClassName = (margin && margin < 0) && "negative";

    const budgetListStyleAdd = onBudgedList && "on-budget-list";

    return (
        <div className={"balance-view-container " + budgetListStyleAdd}>
            <span>BALANCE</span>
            {onBudgedList && <span className={"accounting-item"}>{`Break Even: ${breakEven} guests`}</span>}
            {onBudgedList && <span className={"accounting-item " + negativeMarginClassName}>{`Margin: ${margin?.toFixed(2)} %`}</span>}
            <span className={"balance-sum " + negativeBalanceClassName}>{formatMoney(sum)} €</span>
        </div>
    )
}