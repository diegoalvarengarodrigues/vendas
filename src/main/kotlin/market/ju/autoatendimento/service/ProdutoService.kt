package market.ju.autoatendimento.service


import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.exceptions.NaoEncontradoException
import market.ju.autoatendimento.repository.CategoriaRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class ProdutoService (
    private val produtoRepository: ProdutoRepository,
    private val categoriaRepository: CategoriaRepository
){
    /*
    fun criarProduto(produtoDTO: ProdutoDTO, categoriaId: Long): Produto {
        val categoria = categoriaRepository.findById(categoriaId).orElseThrow {
            throw NaoEncontradoException("Categoria não encontrada")
        }
        val produto = Produto(
            nome = produtoDTO.nome,
            unidadeDeMedida = produtoDTO.unidadeDeMedida,
            precoUnitario = produtoDTO.precoUnitario,
            categoria = produtoDTO.categoria
        )
        return produtoRepository.save(produto)

    }*/
    fun criarProduto(produto: Produto): Produto {
        val existe: Boolean = this.categoriaRepository.existsById(produto.categoria.id!!)
        if(existe) {
            return this.produtoRepository.save(produto)
        } else{
            throw NaoEncontradoException("Categoria não encontrada")
        }
    }
    fun listarProdutos(): List<Produto> {
        return produtoRepository.findAll()
    }

    fun buscarProdutoPorNome(nome: String): List<Produto> {
        return produtoRepository.findByNome(nome)
    }
    fun buscarProdutoPorId(id: Long): Produto {

        return produtoRepository.findById(id).orElseThrow {NaoEncontradoException("Produto com Id $id não encontrado")}

    }
    fun editarProduto(produto: Produto): String {
        val situacao: Int = this.produtoRepository.editarProduto(produto.id!!, produto.nome, produto.unidadeDeMedida, produto.precoUnitario, produto.categoria.id!!)
        if (situacao == 1) {
            return "O produto de ID: ${produto.id} foi alterado com sucesso!"
        } else {
            return "Produto não encontrado"
        }
    }
    fun excluirProduto(id: Long) {
       if (!produtoRepository.existsById(id)) {
           throw NaoEncontradoException("Produto não encotrado")
       }
        produtoRepository.deleteById(id)
    }
}