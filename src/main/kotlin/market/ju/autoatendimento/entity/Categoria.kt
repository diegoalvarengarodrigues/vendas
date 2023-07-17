package market.ju.autoatendimento.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Categoria(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    @NotBlank(message = "O nome da categoria é obrigatório")
    var nome: String

)
