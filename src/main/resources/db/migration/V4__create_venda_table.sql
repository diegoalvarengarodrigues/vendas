CREATE TABLE venda (
  id BIGINT AUTO_INCREMENT NOT NULL,
   valor_total DOUBLE NOT NULL,
   forma_de_pagamento INT NOT NULL,
   CONSTRAINT pk_venda PRIMARY KEY (id)
);