package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.ProdutoDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.entity.Produto
import market.ju.autoatendimento.repository.CategoriaRepository
import market.ju.autoatendimento.repository.ProdutoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ProdutoService (
    private val produtoRepository: ProdutoRepository,
    private val categoriaRepository: CategoriaRepository
){
    fun adicionarProduto(produtoDTO: ProdutoDTO, categoriaId: Long): ProdutoDTO {
        val categoria = categoriaRepository.findById(categoriaId).orElseThrow {
            throw IllegalArgumentException("Categoria não encontrada")
        }
        val produto = Produto(
            id = produtoDTO.id,
            nome = produtoDTO.nome,
            unidadeDeMedida = produtoDTO.unidadeDeMedida,
            precoUnitario = produtoDTO.precoUnitario,
            categoria = categoria
        )
        val produtoSalvo = produtoRepository.save(produto)
        return ProdutoDTO(produtoSalvo.id, produtoSalvo.nome, produtoSalvo.precoUnitario, produtoSalvo.unidadeDeMedida, categoriaId)
    }
    fun listarProdutos(): List<ProdutoDTO> {
        val produtos = produtoRepository.findAll()
        return produtos.map { produto ->  ProdutoDTO(
            id = produto.id,
            nome = produto.nome,
            precoUnitario = produto.precoUnitario,
            unidadeDeMedida = produto.unidadeDeMedida,
            categoriaId = produto.categoria?.id) }
    }
    fun buscarProdutoPorId(id: Long): ProdutoDTO? {
        val produto = produtoRepository.findById(id).orElse(null)
        return produto?.let {
            ProdutoDTO(
                id = it.id,
                nome = it.nome,
                precoUnitario = it.precoUnitario,
                unidadeDeMedida = it.unidadeDeMedida,
                categoriaId = it.categoria?.id
            )
        }
    }
    fun atualizarProduto(produtoDTO: ProdutoDTO, categoriaId: Long) {
        val categoria = categoriaRepository.findById(categoriaId).orElseThrow {
            throw IllegalArgumentException("Categoria não encontrada")
        }
        val produto = Produto(
            id = produtoDTO.id,
            nome = produtoDTO.nome,
            unidadeDeMedida = produtoDTO.unidadeDeMedida,
            precoUnitario = produtoDTO.precoUnitario,
            categoria = categoria
        )
        produtoRepository.save(produto)
    }
    fun excluirProduto(id: Long) {
        val produtoCadastrado = produtoRepository.findById(id).orElseThrow()
        produtoRepository.deleteById(produtoCadastrado.id!!)
    }
}