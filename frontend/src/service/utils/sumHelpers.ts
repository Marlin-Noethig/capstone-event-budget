import {Position} from "../../model/Position";
import {SubCategory} from "../../model/SubCategory";
import {MainCategory} from "../../model/MainCategory";

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

export const getBalance = (positions: Position[], subCategories: SubCategory[], mainCategories: MainCategory[]) => {
    let mainSums: number[] =[];
    mainCategories.forEach(mainCategory => {
        const subsOfMain = subCategories.filter(subCategory => subCategory.mainCategoryId === mainCategory.id)
        let sumOfMain = getMainSum(positions, subsOfMain)
        if (!mainCategory.income){
            sumOfMain *= -1
        }
        mainSums.push(sumOfMain)
    })
    return mainSums.reduce((a, b) => (a + b), 0)
}
