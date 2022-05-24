import {Position} from "../../model/Position";

export const getSumFromPositions = (positions: Position[]) =>{
    const sumsOfPositions = positions.map(position => position.price * position.amount)
    return sumsOfPositions.reduce((a, b) => (a + b), 0);
}