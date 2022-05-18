export const netToGross: (price: number, tax: number) => number = (price, tax) =>{
    return ((tax / 100 ) + 1) * price
}