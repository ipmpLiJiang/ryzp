const custom = {
  makeCode (o, l) {
    let identifyCode = ''
    let max = o.length - 1
    let n = 0
    for (let i = 0; i < l; i++) {
      n = Math.round(Math.random() * max)
      if (n < 0) {
        n = 0
      }
      identifyCode += o[n]
    }
    return identifyCode
  }
}
const data = {
}
export { custom, data }
