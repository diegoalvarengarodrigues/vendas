CREATE TABLE carrinho (
  id BIGINT AUTO_INCREMENT NOT NULL,
   produto_id BIGINT NULL,
   quantidade INT NOT NULL,
   preco_final DOUBLE NOT NULL,
   venda_id BIGINT NULL,
   CONSTRAINT pk_carrinho PRIMARY KEY (id)
);

ALTER TABLE carrinho ADD CONSTRAINT FK_CARRINHO_ON_PRODUTO FOREIGN KEY (produto_id) REFERENCES produto (id);
