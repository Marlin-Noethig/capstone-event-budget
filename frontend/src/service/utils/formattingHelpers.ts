export const formatMoney = (num: number) => {
    let num_parts = num.toFixed(2).toString().split(".");
    num_parts[0] = num_parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    return num_parts.join(",")
}

export const formatDateToIsoString: (dateToFormat: Date) => string = (dateToFormat) => {
    return new Date(dateToFormat).toISOString().split("T")[0];
}