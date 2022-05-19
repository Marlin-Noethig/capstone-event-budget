import {ChangeEvent, FormEvent, useState} from "react";
import {grossToNet, netToGross} from "../../service/utils/taxHelpers";

export default function WritePosition() {
    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [amount, setAmount] = useState<number>(0);
    const [netPrice, setNetPrice] = useState<number>(0);
    const [grossPrice, setGrossPrice] = useState<number>(0);
    const [tax, setTax] = useState<number>(19);

    const onSubmitNewPosition = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
    }

    const onChangeNetPrice = (e: ChangeEvent<HTMLInputElement>) => {
        const value = Number(e.target.value)
        setNetPrice(value)
        setGrossPrice(netToGross(value, tax))
    }

    const onChangeGrossPrice = (e: ChangeEvent<HTMLInputElement>) => {
        const value = Number(e.target.value)
        setGrossPrice(value)
        setNetPrice(grossToNet(value, tax))
    }

    return (
        <form onSubmit={onSubmitNewPosition}>
            <label>name:</label>
            <input type={"text"} value={name} onChange={e => setName(e.target.value)}/><br/>

            <label>description:</label>
            <input type={"text"} value={description} onChange={e => setDescription(e.target.value)}/><br/>

            <label>amount:</label>
            <input type={"number"} value={amount} onChange={e => setAmount(Number(e.target.value))}/><br/>

            <label>net price:</label>
            <input type={"number"} value={netPrice} onChange={onChangeNetPrice}/><br/>

            <label>gross price:</label>
            <input type={"number"} value={grossPrice} onChange={onChangeGrossPrice}/><br/>

            <label>tax:</label>
            <input type={"number"} value={tax} onChange={e => setTax(Number(e.target.value))}/><br/>

            <input type={"submit"} value={"add"}/>
        </form>
    )
}