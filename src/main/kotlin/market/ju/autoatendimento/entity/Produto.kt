package market.ju.autoatendimento.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank


@Entity
data class Produto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @NotBlank(message = "O nome do produto é obrigatório")
    var nome: String,
    var unidadeDeMedida: String,
    var precoUnitario: Double,
    @ManyToOne
    val categoria: Categoria
)
