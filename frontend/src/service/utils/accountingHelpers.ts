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

export const getBreakEven: (positions: Position[], subCategories: SubCategory[], mainCategories: MainCategory[], guests: number) => number = (positions, subCategories, mainCategories, guests) => {

    if (positions.length < 1){
        return 0
    }

    const mainsFilteredForExpenses = mainCategories.filter(mainCategory => !mainCategory.income)
    const mainsFilteredForIncome = mainCategories.filter(mainCategory => mainCategory.income)

    let totalExpenses = getBalance(positions, subCategories, mainsFilteredForExpenses);
    //to reverse the operation from above for recycling purposes
    totalExpenses *= -1;

    const totalIncomes = getBalance(positions, subCategories, mainsFilteredForIncome)

    //round up because there are no decimal guests
    return Math.ceil((totalExpenses / (totalIncomes/guests)))
}

export const getMargin = (positions: Position[], subCategories: SubCategory[], mainCategories: MainCategory[]) => {
    const currentBalance = getBalance(positions, subCategories, mainCategories)

    const mainsFilteredForExpenses = mainCategories.filter(mainCategory => !mainCategory.income)

    let totalExpenses = getBalance(positions, subCategories, mainsFilteredForExpenses);
    //to reverse the operation from above for recycling purposes
    totalExpenses *= -1;

    return (currentBalance / totalExpenses) * 100
}