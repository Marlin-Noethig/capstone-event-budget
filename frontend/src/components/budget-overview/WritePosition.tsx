import {ChangeEvent, FormEvent, useState} from "react";
import {grossToNet, netToGross} from "../../service/utils/taxHelpers";
import {Position} from "../../model/Position";
import "./styles/WritePosition.css"
import {toast} from "react-toastify";
import {formatMoney} from "../../service/utils/beatifyHelpers";


type WritePositionProps = {
    position?: Position,
    addNewPosition?: (newPosition: Omit<Position, "id">) => void,
    toggleEnableAdd?: () => void,
    toggleEnableEdit?: () => void,
    updatePosition?: (id: string, newPosition: Omit<Position, "id">) => void,
    subCategoryId: string
}

export default function WritePosition({
                                          position,
                                          addNewPosition,
                                          toggleEnableAdd,
                                          toggleEnableEdit,
                                          updatePosition,
                                          subCategoryId
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
            toast.warn("Name must be set")
            return
        }
        if (amount <= 0) {
            toast.warn("Amount must be more than 0")
            return
        }
        const positionValues = {
            name: name,
            description: description,
            amount: amount,
            price: netPrice,
            tax: tax,
            subCategoryId: subCategoryId
        }

        if (addNewPosition && toggleEnableAdd) {
            addNewPosition(positionValues);
            toggleEnableAdd();
        }
        if (position && updatePosition && toggleEnableEdit) {
            updatePosition(position.id, positionValues);
            toggleEnableEdit();
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
            <div className={"write-position-form-inputs overview-grid-item"}>
                <input type={"text"} value={name} onChange={e => setName(e.target.value)}/>
                <input type={"text"} value={description} onChange={e => setDescription(e.target.value)}/>
                <input type={"number"} value={amount} min={0} step={"any"}
                       onChange={e => setAmount(Number(e.target.value))}/>
                <select value={tax} onChange={onChangeTax}>
                    <option value="19">19 %</option>
                    <option value="7">7 %</option>
                    <option value="0">0 %</option>
                </select>
                <input type={"number"} value={netPrice} step={"any"} onChange={onChangeNetPrice}/>
                <input type={"number"} value={grossPrice} step={"any"} onChange={onChangeGrossPrice}/>
                <span className={"current-sum"}>{formatMoney((netPrice * amount))} €</span>
            </div>

            <div className={"write-position-form-buttons"}>
                {position ?
                    <input type={"submit"} value={"save"} className={"save-button"}/>
                    :
                    <input type={"submit"} value={"add"} className={"add-button"}/>
                }
                <button onClick={toggleEnableAdd ? toggleEnableAdd : toggleEnableEdit}>X</button>
            </div>
        </form>
    )
}