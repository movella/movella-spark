/// <reference path="../jquery-3.6.0.js" />
/// <reference path="../sweetalert2.d.ts" />
/// <reference path="swal.d.ts" />

// const { default: Swal } = require('sweetalert2')

$(() => {
  $('#form').on('submit', (e) => {
    let body = {}

    $('#form')
      .serializeArray()
      .forEach((v) => {
        body[v.name] = v.value
      })

    if (body['senha'] === body['repetesenha'])
      fetch('/api/register', {
        method: 'post',
        body: JSON.stringify(body),
        headers: { 'content-type': 'application/json' },
      }).then(async (v) => {
        console.log(v)

        if (v.status === 200) {
          const data = await v.json()

          Swal.fire({ title: 'Olá', icon: 'success', text: data['message'] })
        } else if (v.status == 400) {
          const data = await v.json()

          Swal.fire({ title: 'Atenção', icon: 'error', text: data['message'] })
        } else {
          Swal.fire({
            title: 'Atenção',
            icon: 'error',
            text: 'Dados inválidos',
          })
        }
      })
    else
      Swal.fire({
        title: 'Atenção',
        icon: 'error',
        text: 'As senhas não são iguais',
      })

    return e.preventDefault() ?? false
  })
})
