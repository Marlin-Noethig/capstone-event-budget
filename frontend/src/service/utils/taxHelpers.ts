export const netToGross: (price: number, tax: number) => number = (price, tax) =>{
    return price *((tax / 100 ) + 1)
}
export const grossToNet: (price: number, tax: number) => number = (price, tax) =>{
    return price / ((tax / 100 ) + 1)
}
