import {ChangeEvent, FormEvent, useState} from "react";
import {grossToNet, netToGross} from "../../service/utils/taxHelpers";
import {Position} from "../../model/Position";
import "./styles/WritePosition.css"


type WritePositionProps = {
    mode: string,
    position?: Position,
    addNewPosition?: (newPosition: Omit<Position, "id">) => void,
    toggleEnableAdd?: () => void,
    toggleEnableEdit?: () => void
    updatePosition?: (id: string, newPosition: Omit<Position, "id">) => void
}

export default function WritePosition({
                                          mode,
                                          position,
                                          addNewPosition,
                                          toggleEnableAdd,
                                          toggleEnableEdit,
                                          updatePosition
                                      }: WritePositionProps) {

    const [name, setName] = useState<string>(position ? position.name : "");
    const [description, setDescription] = useState<string>(position ? position.description : "");
    const [amount, setAmount] = useState<number>(position ? position.amount : 0);
    const [netPrice, setNetPrice] = useState<number>(position ? position.price : 0);
    const [grossPrice, setGrossPrice] = useState<number>(position ? netToGross(position.price, position.tax) : 0);
    const [tax, setTax] = useState<number>(position ? position.tax : 19);

    const onSubmitNewPosition = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if (!name) {
            alert("Name must be set")
            return
        }
        if (amount <= 0) {
            alert("amount must be more than 0")
            return
        }
        const positionValues = {
            name: name,
            description: description,
            amount: amount,
            price: netPrice,
            tax: tax
        }

        //Typescript made me encapsulate the function call into conditionals
        if (mode === "ADD") {
            if (addNewPosition) {
                addNewPosition(positionValues);
            }
            if (toggleEnableAdd) {
                toggleEnableAdd();
            }
        }
        if (mode === "EDIT") {
            if (position) {
                if (updatePosition) {
                    updatePosition(position.id, positionValues)
                }
            }
            if (toggleEnableEdit) {
                toggleEnableEdit();
            }
        }
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

    return (
        <form onSubmit={onSubmitNewPosition} className={"write-position-form"}>
            <label>name:</label>
            <input type={"text"} value={name} onChange={e => setName(e.target.value)}/><br/>

            <label>description:</label>
            <input type={"text"} value={description} onChange={e => setDescription(e.target.value)}/><br/>

            <label>amount:</label>
            <input type={"number"} value={amount} min={0} onChange={e => setAmount(Number(e.target.value))}/><br/>

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

            {mode === "ADD" ?
                <div className={"add-mode-buttons-wrapper"}>
                    <input type={"submit"} value={"add"} className={"add-button"}/>
                    <button onClick={toggleEnableAdd}>X</button>
                </div>
                :
                <input type={"submit"} value={"save"} className={"save-button"}/>
            }

        </form>
    )
}