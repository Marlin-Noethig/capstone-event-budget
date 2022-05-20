import {ChangeEvent, FormEvent, useState} from "react";
import {grossToNet, netToGross} from "../../service/utils/taxHelpers";
import {Position} from "../../model/Position";
import "./styles/WritePosition.css"


type WritePositionProps = {
    addNewPosition: (newPosition: Omit<Position, "id">) => void
}

export default function WritePosition({addNewPosition}:WritePositionProps) {
    const [name, setName] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [amount, setAmount] = useState<number>(0);
    const [netPrice, setNetPrice] = useState<number>(0);
    const [grossPrice, setGrossPrice] = useState<number>(0);
    const [tax, setTax] = useState<number>(19);

    const onSubmitNewPosition = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!name){
            alert("Name must be set")
            return
        }
        if(amount <= 0){
            alert("amount must be more than 0")
            return
        }
        const positionToAdd = {
            name: name,
            description: description,
            amount: amount,
            price: netPrice,
            tax: tax
        }
        addNewPosition(positionToAdd)
        clearForm()
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

    const onChangeTax = (e: ChangeEvent<HTMLSelectElement>) => {
        const newTax = Number(e.target.value)
        setTax(newTax)
        setGrossPrice(netToGross(netPrice, newTax))
    }

    const clearForm = () => {
        setName("")
        setDescription("")
        setAmount(0)
        setNetPrice(0)
        setGrossPrice(0)
        setTax(19)
    }

    return (
        <form onSubmit={onSubmitNewPosition} className={"write-position-form"}>
            <label>name:</label>
            <input type={"text"} value={name} onChange={e => setName(e.target.value)}/><br/>

            <label>description:</label>
            <input type={"text"} value={description} onChange={e => setDescription(e.target.value)}/><br/>

            <label>amount:</label>
            <input type={"number"} value={amount} onChange={e => setAmount(Number(e.target.value))}/><br/>

            <label>tax:</label>
            <select value={tax} onChange={onChangeTax}>
                <option value="19">19 %</option>
                <option value="7">7 %</option>
                <option value="0">0 %</option>
            </select><br/>


            <label>net price:</label>
            <input type={"number"} value={netPrice} onChange={onChangeNetPrice}/><br/>

            <label>gross price:</label>
            <input type={"number"} value={grossPrice} onChange={onChangeGrossPrice}/><br/>

            <input type={"submit"} value={"add"} className={"add-button"}/>
        </form>
    )
}