package market.ju.autoatendimento.repository

import market.ju.autoatendimento.entity.Categoria
import org.springframework.data.jpa.repository.JpaRepository

interface CategoriaRepository : JpaRepository<Categoria, Long> {
}