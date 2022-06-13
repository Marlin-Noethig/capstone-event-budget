import "./styles/BalanceView.css"
import {formatMoney} from "../../service/utils/formatingHelpers";

type BalanceViewProps = {
    sum: number
    onBudgedList: boolean
}

export default function BalanceView({sum, onBudgedList}: BalanceViewProps) {

    const negativeClassName = (sum < 0) && "negative-sum";
    const budgetListStyleAdd = onBudgedList && "on-budget-list";

    return (
        <div className={"balance-view-container " + budgetListStyleAdd}>
            <span>BALANCE</span>
            <span className={"balance-sum " + negativeClassName}>{formatMoney(sum)} €</span>
        </div>
    )
}