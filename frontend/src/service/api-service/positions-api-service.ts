import axios from "axios";
import {Position} from "../../model/Position";

export const getPositions: () => Promise<Position[]> = () => {
    return axios.get("/api/positions/")
        .then(response => response.data);
}