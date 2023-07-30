package market.ju.autoatendimento.repository

import jakarta.transaction.Transactional
import market.ju.autoatendimento.entity.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long> {

    //Busca de Produto por nome
    @Query("select p from Produto p where p.nome = :nome")
    fun findByNome(nome: String): List<Produto>

    //Edita um produto
    @Transactional
    @Modifying
    @Query(value = "UPDATE produto SET nome = :nome, unidade_de_medida = :unidadeDeMedida, " +
            "preco_unitario = precoUnitario, categoria_id = :categoria  WHERE id= :id", nativeQuery = true)
    fun editarProduto(@Param("id") id: Long,
                      @Param("nome") nome: String,
                      @Param("unidadeDeMedida") unidadeDeMedida: String,
                      @Param("precoUnitario") precoUnitario: Double,
                      @Param("categoria") categoria: Long): Int
}
