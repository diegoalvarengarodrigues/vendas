package market.ju.autoatendimento.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
data class Categoria(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false) @NotNull(message = "O nome da Categoria é obrigatório") var nome: String,
)
