package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.CategoriaDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.repository.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val categoriaRepository: CategoriaRepository) {

    fun adicionarCategoria(categoriaDTO: CategoriaDTO): CategoriaDTO {
        val categoria = Categoria(nome = categoriaDTO.nome)
        val novaCategoria = categoriaRepository.save(categoria)
        return CategoriaDTO(novaCategoria.id, novaCategoria.nome)
    }
    fun listarCategorias(): List<CategoriaDTO> {
        val categorias = categoriaRepository.findAll()
        return categorias.map { CategoriaDTO(it.id, it.nome) }
    }
    fun buscarCategoriaPorId(id: Long): CategoriaDTO {
        val categoria = categoriaRepository.findById(id).orElseThrow()
        return CategoriaDTO(categoria.id, categoria.nome)
    }
    fun atualizarCategoria(id: Long, categoriaDTO: CategoriaDTO): Categoria {
        val categoriaAtual = categoriaRepository.findById(id).orElseThrow()
        categoriaAtual.nome = categoriaDTO.nome
        val categoriaAtualizada = categoriaRepository.save(categoriaAtual)
        return Categoria(categoriaAtualizada.id, categoriaAtualizada.nome)
    }
    fun excluirCategoria(id: Long) {
        val categoriaCadastrada = categoriaRepository.findById(id).orElseThrow()
        categoriaRepository.deleteById(categoriaCadastrada.id!!)
    }
}