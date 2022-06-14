import {SubCategory} from "../../model/SubCategory";
import {Position} from "../../model/Position";
import {formatMoney} from "../../service/utils/formatingHelpers";
import {getSubSum} from "../../service/utils/sumHelpers";

type SubCategoryOverviewProps = {
    subCategory: SubCategory,
    positions: Position[],
}

export default function SubCategoryOverview({subCategory, positions}: SubCategoryOverviewProps) {

    const filteredPositions = positions.filter(position => position.subCategoryId === subCategory.id)
    return (
        <div>
            <div className={"category-view sub-category-view"}>
                <span>{subCategory.name}</span>
                <span>{formatMoney(getSubSum(filteredPositions))} €</span>
            </div>
        </div>
    )

}