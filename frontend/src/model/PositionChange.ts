import {Position} from "./Position";

export type PositionChange = {
    id: string,
    timestamp: Date,
    method: string,
    data: Position,
    userInfo: string,
    positionId: string,
    subCategoryId: string
}