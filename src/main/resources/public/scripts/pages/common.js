/**
 *
 * @param {number} val
 * @returns {string}
 */
const formataValor = (val) => {
  return `R$ ${val.toFixed(2).replace(/\./, ',')}`
}

/**
 * @param {string} cep
 * @returns {ViaCep}
 */
const viaCep = async (cep) => {
  return await fetch(`https://viacep.com.br/ws/${cep}/json/`).then(
    async (res) => {
      const data = await res.json()

      return {
        bairro: data.bairro,
        cidade: data.localidade,
        complemento: data.complemento,
        logradouro: data.logradouro,
        uf: data.uf,
      }
    }
  )
}

/**
 * @param {string} val
 * @returns {string}
 */
const maskBehavior = (val) => {
  return val.replace(/\D/g, '').length === 11
    ? '(00) 00000-0000'
    : '(00) 0000-00000'
}

const options = {
  onKeyPress: function (val, e, field, options) {
    field.mask(maskBehavior.apply({}, arguments), options)
  },
}

/**
 * @param {string} cpf
 * @returns {boolean}
 */
const validarCPF = (cpf) => {
  cpf = cpf.replace(/[^\d]+/g, '')

  if (
    cpf.length !== 11 ||
    cpf === '00000000000' ||
    cpf === '11111111111' ||
    cpf === '22222222222' ||
    cpf === '33333333333' ||
    cpf === '44444444444' ||
    cpf === '55555555555' ||
    cpf === '66666666666' ||
    cpf === '77777777777' ||
    cpf === '88888888888' ||
    cpf === '99999999999'
  )
    return false

  let add = 0

  for (let i = 0; i < 9; i++) add += parseInt(cpf.charAt(i)) * (10 - i)

  let rev = 11 - (add % 11)

  if (rev === 10 || rev === 11) rev = 0

  if (rev !== parseInt(cpf.charAt(9))) return false

  add = 0

  for (let i = 0; i < 10; i++) add += parseInt(cpf.charAt(i)) * (11 - i)

  rev = 11 - (add % 11)

  if (rev === 10 || rev === 11) rev = 0

  if (rev !== parseInt(cpf.charAt(10))) return false

  return true
}
