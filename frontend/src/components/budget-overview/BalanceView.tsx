import "./styles/BalanceView.css"
import {formatMoney} from "../../service/utils/formattingHelpers";

type BalanceViewProps = {
    sum: number
    breakEven?: number
    onBudgedList: boolean
}

export default function BalanceView({sum, breakEven, onBudgedList}: BalanceViewProps) {

    const negativeClassName = (sum < 0) && "negative-sum";
    const budgetListStyleAdd = onBudgedList && "on-budget-list";

    return (
        <div className={"balance-view-container " + budgetListStyleAdd}>
            <span>BALANCE</span>
            {onBudgedList && <span className={"break-even-display"}>{`Break Even: ${breakEven} guests`}</span>}
            <span className={"balance-sum " + negativeClassName}>{formatMoney(sum)} €</span>
        </div>
    )
}