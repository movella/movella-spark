/// <reference path="../jquery-3.6.0.js" />
/// <reference path="../sweetalert2.d.ts" />
/// <reference path="index.d.ts" />
/// <reference path="common.js" />

try {
  const { default: Swal } = require('sweetalert2')
} catch (error) {}

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

    $('#form')
      .serializeArray()
      .forEach((v) => {
        body[v.name] = v.value
      })

    // Swal.showLoading()

    fetch('/api/movel/create', {
      method: 'post',
      body: JSON.stringify(body),
      headers: { 'content-type': 'application/json' },
    }).then(async (v) => {
      console.log(v)

      // Swal.close()

      console.log(v.status)

      if (v.status === 200) {
        const data = await v.json()

        console.log(data)

        await upload()

        console.log(432432)

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

const upload = async () => {
  /**
   * @type {HTMLInputElement}
   */
  const input = document.getElementById('foto')

  const files = input.files

  if (files.length === 0) return

  const str = await getBase64(input.files[0])

  await fetch('/api/movel/upload/0', {
    body: str,
    method: 'post',
  })
    .then(async (v) => {
      console.log(v)

      if (v.status === 200) {
        const data = await v.json()

        console.log(data)
      } else if (v.status == 400) {
        const data = await v.json()

        console.log(data)
        Swal.fire({ title: 'Atenção', icon: 'error', text: data['message'] })
      } else {
        Swal.fire({
          title: 'Atenção',
          icon: 'error',
          text: 'Houve um erro inesperado',
        })
      }
    })
    .catch(console.log)
}

/**
 * @param {File} file
 * @returns {Promise<string>}
 */
const getBase64 = (file) => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    try {
      reader.readAsDataURL(file)
    } catch (error) {}

    reader.onload = () => {
      let encoded = reader.result.toString().replace(/^data:(.*,)?/, '')

      if (encoded.length % 4 > 0)
        encoded += '='.repeat(4 - (encoded.length % 4))

      resolve(encoded)
    }

    reader.onerror = (error) => reject(error)
  })
}
