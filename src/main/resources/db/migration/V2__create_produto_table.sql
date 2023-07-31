CREATE TABLE produto (
  id BIGINT AUTO_INCREMENT NOT NULL,
   nome VARCHAR(255) NOT NULL,
   unidade_de_medida VARCHAR(255) NOT NULL,
   preco_unitario DOUBLE NOT NULL,
   categoria_id BIGINT NULL,
   CONSTRAINT pk_produto PRIMARY KEY (id)
);

ALTER TABLE produto ADD CONSTRAINT FK_PRODUTO_ON_CATEGORIA FOREIGN KEY (categoria_id) REFERENCES categoria (id);