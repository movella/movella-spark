/// <reference path="../jquery-3.6.0.js" />
/// <reference path="../sweetalert2.d.ts" />
/// <reference path="index.d.ts" />
/// <reference path="common.js" />

// const { default: Swal } = require('sweetalert2')

$(() => {
  $('#valorMes').on('change', function () {
    let val = $(this).val()
    val = numeral(val).format('$0.00').replace('$', '').replace('.', ',')
    $(this).val(val)
  })

  $('#foto').on('change', function () {
    if (this.files && this.files[0]) {
      const reader = new FileReader()
      reader.onload = (e) => $('#imagem').attr('src', e.target.result)
      reader.readAsDataURL(this.files[0])
    }
  })

  $('#form').on('submit', (e) => {
    let body = {}

    console.log(4234)

    $('#form')
      .serializeArray()
      .forEach((v) => {
        body[v.name] = v.value
      })

    fetch('/api/movel/create', {
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

  fetch('/api/categorias').then(async (v) => {
    console.log(v)

    if (v.status === 200) {
      /**
       * @type {Categoria[]}
       */
      const data = await v.json()

      $('#categoria').append(
        data.map((v) => {
          return /* html */ `<option value="${v.id}">${v.nome}</option>`
        })
      )
    } else if (v.status == 400) {
      const data = await v.json()

      Swal.fire({ title: 'Atenção', icon: 'error', text: data['message'] })
    } else {
      Swal.fire({
        title: 'Atenção',
        icon: 'error',
        text: 'Houve um erro inesperado',
      })
    }
  })
})
