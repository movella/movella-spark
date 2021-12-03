/// <reference path="../jquery-3.6.0.js" />
/// <reference path="../sweetalert2.d.ts" />
/// <reference path="index.d.ts" />
/// <reference path="common.js" />

try {
  const { default: Swal } = require('sweetalert2')
} catch (error) {}

let offset = 0

$(() => {
  $('#quantidade, #disponiveis').on('change', () => {
    offset = 0

    refresh()
  })

  $('#filtro').on('keyup', () => {
    offset = 0

    refresh()
  })

  $('.ordem').on('change', () => {
    offset = 0

    refresh()
  })

  fetch('/api/categorias').then(async (v) => {
    console.log(v)

    if (v.status === 200) {
      /**
       * @type {Categoria[]}
       */
      const data = await v.json()

      $('#v-pills-tab').append(
        data.map((v, k) => {
          return /* html */ `
            <a
              role="tab"
              class="nav-link"
              data-toggle="pill"
              aria-selected="true"
              data-bs-toggle="pill"
              href="#v-pills-${k + 1}"
              aria-controls="v-pills-${k + 1}"
            >
              ${v.nome}
            </a>
          `
        })
      )

      $('#v-pills-tab > a').on('click', () => {
        offset = 0

        refresh()
      })
    } else if (v.status === 400) {
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

  refresh()
})

const refresh = () => {
  fetch('/api/moveis', {
    method: 'post',
    body: JSON.stringify({
      limit: parseInt($('#quantidade').val()),
      offset: offset,
      categoria: $('#v-pills-tab > .active').text().replace(/\n/g, '').trim(),
      filtro: $('#filtro').val(),
      disponivel: $('#disponiveis').val() === 'on',
      order: $('.ordem:checked').val(),
    }),
    headers: { 'content-type': 'application/json' },
  }).then(async (v) => {
    console.log(v)

    if (v.status === 200) {
      /**
       * @type {{moveis: MovelPaginado[], qntPages: number}}
       */
      const data = await v.json()

      $('#moveis, #pagination').html('')

      if (data.moveis.length === 0)
        $('#moveis').append(/* html */ `
          <div class="p-4 text-center">
            <small>Nenhum móvel encontrado</small>
          </div>
        `)
      else {
        if (offset !== 0) {
          $('#pagination').append(/* html */ `
           <li class="page-item previous" data-role="previous">
             <a class="page-link" href="#">Anterior</a>
           </li>
          `)
        }

        $('#pagination').append(
          new Array(data.qntPages).fill(true).map((v, k) => {
            return /* html */ `
            <li
              class="page-item item"
              data-role="page"
              data-page="${k + 1}"
            >
              <a class="page-link" href="#">${k + 1}</a>
            </li>
            `
          })
        )

        if (
          (offset + parseInt($('#quantidade').val())) /
            parseInt($('#quantidade').val()) !==
          data.qntPages
        ) {
          $('#pagination').append(/* html */ `
           <li class="page-item next" data-role="next">
             <a class="page-link" href="#">Próximo</a>
           </li>
          `)
        }

        $('.next').on('click', function () {
          offset += parseInt($('#quantidade').val())
          refresh()
        })
        $('.previous').on('click', function () {
          offset -= parseInt($('#quantidade').val())
          refresh()
        })
        $('.item').on('click', function () {
          offset =
            (parseInt($(this).find('a').text()) - 1) *
            parseInt($('#quantidade').val())

          refresh()
        })

        $('#moveis').append(
          data.moveis.map((v) => {
            return /* html */ `
              <div class="col-6 col-md-4 p-1 movel"
              data-id="${v.id}"
              data-nome="${v.nome}"
              data-por="${v.usuarionome}"
              data-disponivel="${v.disponivel}"
              data-seu="${v.seu}"
              data-localidade="${v.cidade}"
              data-valor="${v.valormes}"
              data-imagem="${v.imagem}"
              >
                <div class="card h-100">
                  <img
                    class="card-img-top"
                    style="height: 150px; object-fit: cover"
                    src="/img/${v.imagem}"
                    alt="Móvel"
                  />
                  <div class="card-body">
                    ${v.nome}
                    <br />
                    <small>${v.cidade}</small>
                    <br />
                    <small>Por: ${v.usuarionome}</small>
                    <b>
                      <p class="text-${v.disponivel ? 'success' : 'danger'}">
                        ${v.disponivel ? 'Disponível' : 'Indisponível'}
                      </p>
                    </b>
                  </div>
                  <div class="card-footer">${formataValor(v.valormes)}/mês</div>
                </div>
              </div>
            `

            // <small>Avaliação: ${v.avaliacao}</small>
          })
        )

        $('.movel').on('click', async function () {
          const movel = $(this)

          const id = movel.data('id')
          const nome = movel.data('nome')
          const por = movel.data('por')
          const seu = Boolean(movel.data('seu'))
          const localidade = movel.data('localidade')
          const valor = parseInt(movel.data('valor'))
          const disponivel = Boolean(movel.data('disponivel') && !seu)
          const imagem = movel.data('imagem')

          const res = await Swal.fire({
            title: nome,
            showCancelButton: true,
            imageUrl: `/img/${imagem}`,
            cancelButtonText: 'Voltar',
            confirmButtonText: 'Alugar',
            showConfirmButton: disponivel,
            input: disponivel && 'number',
            footer: /* html */ `<small>Por: ${por}</small>`,
            inputLabel: disponivel && 'Por quantos dias deseja alugar?',
            inputValidator: (result) => {
              result = parseInt(result)
              if (result <= 0 || result > 90) return 'Quantidade inválida'
            },
            inputAttributes: {
              minlength: 1,
              minLength: 1,
            },
            inputOptions: {
              minlength: 1,
              minLength: 1,
            },
            preConfirm: (inputValue) => {
              inputValue ??= ''

              return inputValue.length === 0
                ? false
                : { dias: inputValue, pagamento: $('#meio-de-pagamento').val() }
            },
            didOpen: (popup) => {
              const el = $(popup)

              el.find('#swal2-input').mask('00')

              el.find('#swal2-input').on('keyup', function () {
                const input = $(this)
                const dias = parseInt(input.val())

                if (typeof dias === 'number' && !isNaN(dias))
                  $('#valor-total').text(formataValor(valor * (dias / 30)))
              })

              fetch('/api/pagamentos').then(async (d) => {
                console.log(d)

                if (v.status === 200) {
                  /**
                   * @type {Pagamento[]}
                   */
                  const data = await d.json()

                  $('#meio-de-pagamento').append(
                    data.map((v) => {
                      return /* html */ `<option value="${v.id}">
                        Chave PIX - ${v.chave.replace(
                          /(\d{3})(\d{3})(\d{3})(\d{2})/,
                          '$1.$2.$3-$4'
                        )}
                      </option>`
                    })
                  )
                }
              })
            },
            html: /* html */ `
            <b>
              ${
                seu
                  ? ''
                  : /* html */ `
                  <p class="text-${disponivel ? 'success' : 'danger'}">
                    ${disponivel ? 'Disponível' : 'Indisponível'}
                  </p>
                  `
              }
              <table class="w-100">
                <tr>
                  <td class="text-start"><small>Valor por mês</small></td>
                  <td class="text-end">
                    <small>${formataValor(valor)}</small>
                  </td>
                </tr>
                <tr>
                  <td class="text-start"><small>Localidade</small></td>
                  <td class="text-end"><small>${localidade}</small></td>
                </tr>
                <tr>
                  <td class="text-start"><small>Valor total</small></td>
                  <td class="text-end">
                    <small id="valor-total">${formataValor(0)}</small>
                  </td>
                </tr>
              </table>
              <hr />
              ${
                disponivel
                  ? /* html */ `
                  <div class="mb-3">
                    <label for="meio-de-pagamento">Meio de pagamento</label>
                    <select id="meio-de-pagamento" class="form-control"></select>
                  <div>
                  <small>Caso não tenha meios de pagamento, adicione um na página de "Minha conta".</small>
                  `
                  : ''
              }
            </b>
            `,
          })

          if (res.isConfirmed) {
            /**
             * @type {{ dias: string, pagamento: string}}
             */
            const result = res.value

            fetch('/api/alugar', {
              method: 'post',
              body: JSON.stringify({
                movel: id,
                dias: result.dias,
                pagamento: result.pagamento,
              }),
              headers: { 'content-type': 'application/json' },
            }).then(async (v) => {
              console.log(v)

              if (v.status === 200) {
                Swal.fire({
                  icon: 'success',
                  title: 'Aluguel realizado!',
                }).then(() => {
                  location = '/'
                })
              } else if (v.status === 400) {
                /**
                 * @type {BaseResponse}
                 */
                const data = await v.json()

                Swal.fire({
                  title: 'Atenção',
                  icon: 'error',
                  text: data.message,
                })
              } else if (v.status === 401) {
                /**
                 * @type {BaseResponse}
                 */
                const data = await v.json()

                Swal.fire({
                  title: 'Atenção',
                  icon: 'error',
                  text: `${data.message} Para alugar um móvel, faça login e ative sua conta na página "Minha conta".`,
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
      }
    } else if (v.status === 400) {
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
