import "./styles/BalanceView.css"

type BalanceViewProps = {
    sum: number
}

export default function BalanceView({sum}: BalanceViewProps) {

    const negativeClassName = (sum < 0) && "negative-sum";

    return (
        <div className={"balance-view-container"}>
            <span>BALANCE</span>
            <span className={"balance-sum " + negativeClassName}>{sum.toFixed(2)} €</span>
        </div>
    )
}