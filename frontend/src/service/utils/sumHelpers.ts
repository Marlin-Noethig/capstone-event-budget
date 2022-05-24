import {Position} from "../../model/Position";
import {SubCategory} from "../../model/SubCategory";

export const getSubSum: (positions: Position[]) => number = (positions) =>{
    const sumsOfPositions = positions.map(position => position.price * position.amount)
    return sumsOfPositions.reduce((a, b) => (a + b), 0);
}

export const getMainSum = (positions: Position[], subCategories: SubCategory[]) => {
    let positionsBySubCategories :Position[] = [];
    subCategories.forEach(subCategory =>{
        const positionsByCurrentCategory = positions.filter(position => position.subCategoryId === subCategory.id)
        positionsByCurrentCategory.forEach(position => positionsBySubCategories.push(position))
    })
    return getSubSum(positionsBySubCategories);
}
