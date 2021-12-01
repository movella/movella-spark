/// <reference path="../jquery-3.6.0.js" />
/// <reference path="../sweetalert2.d.ts" />
/// <reference path="index.d.ts" />
/// <reference path="common.js" />

try {
  const { default: Swal } = require('sweetalert2')
} catch (error) {}

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

  $('#v-pills-pagamento-tab').on('click', () => {
    $('.pagamentos').html(/* html */ `
      <small class="text-center p-3">Carregando pagamentos</small>
    `)

    fetch('/api/pagamentos').then(async (v) => {
      console.log(v)

      if (v.status === 200) {
        /**
         * @type {Pagamento[]}
         */
        const data = await v.json()

        if (data.length === 0)
          $('.pagamentos').html(/* html */ `
            <small class="text-center p-3">Nenhum pagamento encontrado</small>
          `)
        else {
          $('.pagamentos').html('')

          $('.pagamentos').append(
            data.map((v) => {
              return /* html */ `
              <div class="card p-0 mb-2">
                <div class="card-body d-flex flex-row">
                  <div class="d-flex flex-column flex-grow-1">
                    <h5>${v.chave.replace(
                      /(\d{3})(\d{3})(\d{3})(\d{2})/,
                      '$1.$2.$3-$4'
                    )}</h5>
                    <small>${v.tipo === 'cpf' ? 'Chave de CPF' : ''}</small>
                  </div>
                  <button
                    data-id="${v.id}"
                    class="btn btn-primary bg-primary btn-delete-pagamento"
                  >
                    <i class="mdi mdi-delete text-white"></i>
                  </button>
                </div>
              </div>
              `
            })
          )
        }

        $('.btn-delete-pagamento').on('click', async function () {
          const button = $(this)

          const id = button.data('id')

          const res = await Swal.fire({
            title: 'Aviso',
            showCancelButton: true,
            cancelButtonText: 'Não',
            confirmButtonText: 'Sim',
            text: 'Deseja mesmo remover este meio de pagamento?',
            footer: /* html */ `<small>Esta ação nã pode ser desfeita!</small>`,
          })

          if (res.isConfirmed) {
            fetch('/api/pagamento/delete', {
              method: 'post',
              body: JSON.stringify({ id: id }),
              headers: { 'content-type': 'application/json' },
            }).then(async (v) => {
              if (v.status === 200) {
                /**
                 * @type {BaseResponse}
                 */
                const data = await v.json()

                await Swal.fire({
                  icon: 'success',
                  title: data.message,
                }).then(() => {
                  location = '/perfil'
                })
              } else if (v.status == 400) {
                /**
                 * @type {BaseResponse}
                 */
                const data = await v.json()

                Swal.fire({
                  title: 'Atenção',
                  icon: 'error',
                  text: data.message,
                })
              } else {
                Swal.fire({
                  title: 'Atenção',
                  icon: 'error',
                  text: 'Houve um erro inesperado',
                })
              }
            })
          }
        })
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

  $('#btn-add-pagamento').on('click', async () => {
    const res = await Swal.fire({
      input: 'text',
      showCancelButton: true,
      cancelButtonText: 'Voltar',
      title: 'Cadastrar pagamento',
      confirmButtonText: 'Cadastrar',
      footer: /* html */ `<small>Chave PIX de CPF</small>`,
      inputLabel: 'Insira a chave que deseja utilizar.',
      inputValidator: (result) => {
        if (
          result.match(/\d{3}\.\d{3}\.\d{3}\-\d{2}/) === null ||
          !validarCPF(result)
        )
          return 'Chave inválida'
      },
      preConfirm: (inputValue) => {
        return inputValue
      },
      inputAttributes: {
        maxlength: 14,
      },
      didOpen: (popup) => {
        const el = $(popup)

        el.find('#swal2-input').mask('000.000.000-00')
      },
    })

    if (res.isConfirmed) {
      fetch('/api/pagamento/create', {
        method: 'post',
        body: JSON.stringify({ chave: res.value }),
        headers: { 'content-type': 'application/json' },
      }).then(async (v) => {
        if (v.status === 200) {
          /**
           * @type {BaseResponse}
           */
          const data = await v.json()

          await Swal.fire({
            icon: 'success',
            title: data.message,
          }).then(() => {
            location = '/perfil'
          })
        } else if (v.status == 400) {
          /**
           * @type {BaseResponse}
           */
          const data = await v.json()

          Swal.fire({ title: 'Atenção', icon: 'error', text: data.message })
        } else {
          Swal.fire({
            title: 'Atenção',
            icon: 'error',
            text: 'Houve um erro inesperado',
          })
        }
      })
    }
  })
})
