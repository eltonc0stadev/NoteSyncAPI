CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    id_estudante VARCHAR(255) NOT NULL UNIQUE,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ativo TINYINT(1)
);

CREATE TABLE nota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    conteudo TEXT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    arquivada TINYINT(1),
    lixeira TINYINT(1),
    usuario_dono_id BIGINT NOT NULL,
    CONSTRAINT fk_nota_usuario_dono FOREIGN KEY (usuario_dono_id) REFERENCES usuario(id)
);

CREATE TABLE nota_usuarios_compartilhados (
    nota_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (nota_id, usuario_id),
    CONSTRAINT fk_compartilhado_nota FOREIGN KEY (nota_id) REFERENCES nota(id) ON DELETE CASCADE,
    CONSTRAINT fk_compartilhado_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);