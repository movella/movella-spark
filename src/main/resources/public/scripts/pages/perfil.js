/// <reference path="../jquery-3.6.0.js" />
/// <reference path="../sweetalert2.d.ts" />
/// <reference path="index.d.ts" />
/// <reference path="common.js" />

// const { default: Swal } = require('sweetalert2')

$(() => {
  $('#form').on('submit', (e) => {
    let body = {}

    if (!validarCPF($('#cpf').val()))
      return (
        Swal.fire({
          title: 'Atenção',
          icon: 'error',
          text: 'CPF inválido',
        }) && false
      )

    $('#form')
      .serializeArray()
      .forEach((v) => {
        body[v.name] = v.value
      })

    fetch('/api/usuario/update', {
      method: 'post',
      body: JSON.stringify(body),
      headers: { 'content-type': 'application/json' },
    }).then(async (v) => {
      console.log(v)

      if (v.status === 200) {
        const data = await v.json()

        Swal.fire({
          title: 'Sucesso',
          icon: 'success',
          text: data['message'],
        }).then((v) => {
          location = '/'
        })
      } else if (v.status == 400) {
        const data = await v.json()

        console.log(data)

        Swal.fire({ title: 'Atenção', icon: 'error', text: data['message'] })
      } else {
        Swal.fire({
          title: 'Atenção',
          icon: 'error',
          text: 'Dados inválidos',
        })
      }
    })

    return e.preventDefault() ?? false
  })

  $('#cep').on('change keyup', function () {
    /**
     * @type {string}
     */
    const cep = $(this).val()

    if (cep.length === 9)
      fetch(`https://viacep.com.br/ws/${cep}/json/`)
        .then(async (res) => {
          const data = await res.json()

          console.log('viacep', data)

          $('#bairro').val(data.bairro ?? '')
          $('#cidade').val(data.localidade ?? '')
          $('#complemento').val(data.complemento ?? '')
          $('#logradouro').val(data.logradouro ?? '')
          $('#uf').val(data.uf ?? '')
        })
        .catch(console.log)
  })

  $('#cep').mask('00000-000')
  $('#cpf').mask('000.000.000-00')
  $('#celular').mask(maskBehavior, options)
})
