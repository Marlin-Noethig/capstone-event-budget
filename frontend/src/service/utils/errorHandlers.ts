import {toast} from "react-toastify";

export const handleRequestError = (statusCode: number) => {

    if (statusCode === 403) {
        toast.error("Your session may be expired. Please try to log in again.")

    } else {
        toast.error("Connection failure. Please try later.")
    }
}