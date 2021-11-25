type ViaCep = {
  bairro: string
  cidade: string
  complemento: string
  logradouro: string
  uf: string
}

type Categoria = {
  id: number
  nome: string
}

type Movel = {
  id: number
  categoriaId: number
  usuarioId: number
  descricao: string
  imagem: string
  nome: string
  valorMes: number
  disponivel: boolean
  altura: number
  largura: number
  espessura: number
}

type MovelPaginado = {
  usuarionome: string
  categoria: string
  nome: string
  descricao: string
  imagem: string
  valormes: number
  disponivel: true
  id: number
  cidade: string
  avaliacao: number
  avaliacoes: number
  seu: boolean
}
