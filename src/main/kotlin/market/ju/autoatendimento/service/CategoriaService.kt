package market.ju.autoatendimento.service

import market.ju.autoatendimento.dto.CategoriaDTO
import market.ju.autoatendimento.entity.Categoria
import market.ju.autoatendimento.exceptions.NaoEncontradoException
import market.ju.autoatendimento.repository.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val categoriaRepository: CategoriaRepository) {
    fun adicionarCategoria(categoriaDTO: CategoriaDTO): Categoria {
        val novaCategoria = Categoria(nome = categoriaDTO.nome)
        return categoriaRepository.save(novaCategoria)
    }

    fun listarCategorias(): List<Categoria> {
       return categoriaRepository.findAll()
    }
    fun buscarCategoriaPorId(id: Long): Categoria {
       return categoriaRepository.findById(id).orElseThrow{
           NaoEncontradoException("Categoria com ID $id não foi encontrada")
       }
    }
    fun editarCategoria(id: Long, categoria: Categoria): Categoria {
        val categoriaAtual = categoriaRepository.findById(id).orElseThrow {
            NaoEncontradoException("Categoria não encontrada")
        }
        categoriaAtual.nome = categoria.nome

        return categoriaRepository.save(categoriaAtual)
    }
    fun excluirCategoria(id: Long) {
        val categoriaCadastrada = categoriaRepository.findById(id).orElseThrow{
            NaoEncontradoException("Categoria não Encontrada")
        }
        categoriaRepository.deleteById(categoriaCadastrada.id!!)
    }
}