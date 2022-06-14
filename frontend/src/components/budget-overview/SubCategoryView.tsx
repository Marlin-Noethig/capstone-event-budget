import {SubCategory} from "../../model/SubCategory";
import "./styles/SubCategoryView.css"
import PositionList from "./PositionList";
import {Position} from "../../model/Position";
import {useState} from "react";
import {getSubSum} from "../../service/utils/sumHelpers";
import {formatMoney} from "../../service/utils/formatingHelpers";

type SubCategoryViewProps = {
    subCategory: SubCategory,
    positions: Position[],
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string, name: string) => void,
    updatePosition: (id: string, newPosition: Omit<Position, "id">) => void
    idOfEvent: string
}

export default function SubCategoryView({
                                            subCategory,
                                            positions,
                                            addNewPosition,
                                            deletePosition,
                                            updatePosition,
                                            idOfEvent
                                        }: SubCategoryViewProps) {

    const filteredPositions = positions.filter(position => position.subCategoryId === subCategory.id)
    const [enableAdd, setEnableAdd] = useState<boolean>(false);
    const [collapsed, setCollapsed] = useState<boolean>(false);

    const toggleEnableAdd = () => {
        setEnableAdd(!enableAdd)
    }

    const toggleCollapsed = () => {
        setCollapsed(!collapsed)
    }

    return (
        <div>
            <div className={"category-view sub-category-view"}>
                <div className={"sub-category-start"}>
                    <button className={"collapse-category-button"}
                            onClick={toggleCollapsed}>{collapsed ? "˄" : "˅"}</button>
                    <span>{subCategory.name}</span>
                </div>
                <span>{formatMoney(getSubSum(filteredPositions))} €</span>
            </div>
            {collapsed && <PositionList positions={filteredPositions}
                                        addNewPosition={addNewPosition}
                                        deletePosition={deletePosition}
                                        updatePosition={updatePosition}
                                        subCategoryId={subCategory.id}
                                        enableAdd={enableAdd}
                                        toggleEnableAdd={toggleEnableAdd}
                                        idOfEvent={idOfEvent}

            />}
        </div>
    )
}