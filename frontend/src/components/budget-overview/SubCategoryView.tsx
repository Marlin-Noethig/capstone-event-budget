import {SubCategory} from "../../model/SubCategory";
import "./styles/SubCategoryView.css"
import PositionList from "./PositionList";
import {Position} from "../../model/Position";
import {useEffect, useState} from "react";
import {getSubSum} from "../../service/utils/sumHelpers";

type SubCategoryViewProps = {
    subCategory: SubCategory,
    positions: Position[],
    addNewPosition: (newPosition: Omit<Position, "id">) => void,
    deletePosition: (id: string) => void,
    updatePosition: (id: string, newPosition: Omit<Position, "id">) => void
}

export default function SubCategoryView({
                                            subCategory,
                                            positions,
                                            addNewPosition,
                                            deletePosition,
                                            updatePosition
                                        }: SubCategoryViewProps) {

    const filteredPositions = positions.filter(position => position.subCategoryId === subCategory.id)
    const [enableAdd, setEnableAdd] = useState<boolean>(false);
    const [subSum, setSubSum] = useState<number>(0)
    const [collapsed, setCollapsed] = useState<boolean>(true);

    const toggleEnableAdd = () => {
        setEnableAdd(!enableAdd)
    }

    useEffect(() => {
            setSubSum(getSubSum(filteredPositions))
        }, [filteredPositions])

    const  toggleCollapsed = () => {
        setCollapsed(!collapsed)
    }

    return (
        <div>
            <div className={"category-view sub-category-view"}>
                <div className={"sub-category-start"}>
                    <span>{subCategory.name}</span>
                    <button onClick={toggleEnableAdd}>+</button>
                </div>
                <div className={"sub-category-end"}>
                    <span>{subSum.toFixed(2)} €</span>
                    <button className={"collapse-category-button"} onClick={toggleCollapsed}>{collapsed ? "˄" : "˅"}</button>
                </div>
            </div>
            {collapsed && <PositionList positions={filteredPositions}
                          addNewPosition={addNewPosition}
                          deletePosition={deletePosition}
                          updatePosition={updatePosition}
                          subCategoryId={subCategory.id}
                          enableAdd={enableAdd}
                          toggleEnableAdd={toggleEnableAdd}
            />}
        </div>
    )
}