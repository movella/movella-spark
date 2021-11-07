package com.movella.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
  int id;
  String celular;
  String email;
  String foto;
  String senha;
  String nome;
  String acesso;
  String cep;
  String logradouro;
  String complemento;
  String bairro;
  String cidade;
  String uf;

  public Usuario( //
      int id, //
      String celular, //
      String email, //
      String foto, //
      String senha, //
      String nome, //
      String acesso, //
      String cep, //
      String logradouro, //
      String complemento, //
      String bairro, //
      String cidade, //
      String uf //
  ) {
    setid(id);
    setcelular(celular);
    setemail(email);
    setfoto(foto);
    setsenha(senha);
    setnome(nome);
    setacesso(acesso);
    setcep(cep);
    setlogradouro(logradouro);
    setcomplemento(complemento);
    setbairro(bairro);
    setcidade(cidade);
    setuf(uf);
  }

  public int getid() {
    return this.id;
  };

  public String getcelular() {
    return this.celular;
  };

  public String getemail() {
    return this.email;
  };

  public String getfoto() {
    return this.foto;
  };

  public String getsenha() {
    return this.senha;
  };

  public String getnome() {
    return this.nome;
  };

  public String getacesso() {
    return this.acesso;
  };

  public String getcep() {
    return this.cep;
  };

  public String getlogradouro() {
    return this.logradouro;
  };

  public String getcomplemento() {
    return this.complemento;
  };

  public String getbairro() {
    return this.bairro;
  };

  public String getcidade() {
    return this.cidade;
  };

  public String getuf() {
    return this.uf;
  };

  public void setid(int id) {
    this.id = id;
  }

  public void setcelular(String celular) {
    this.celular = celular;
  }

  public void setemail(String email) {
    this.email = email;
  }

  public void setfoto(String foto) {
    this.foto = foto;
  }

  public void setsenha(String senha) {
    this.senha = senha;
  }

  public void setnome(String nome) {
    this.nome = nome;
  }

  public void setacesso(String acesso) {
    this.acesso = acesso;
  }

  public void setcep(String cep) {
    this.cep = cep;
  }

  public void setlogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public void setcomplemento(String complemento) {
    this.complemento = complemento;
  }

  public void setbairro(String bairro) {
    this.bairro = bairro;
  }

  public void setcidade(String cidade) {
    this.cidade = cidade;
  }

  public void setuf(String uf) {
    this.uf = uf;
  }

  public static Usuario fromResultSet(ResultSet rs) throws SQLException {
    return new Usuario( //
        rs.getInt("id"), //
        rs.getString("celular"), //
        rs.getString("email"), //
        rs.getString("foto"), //
        rs.getString("senha"), //
        rs.getString("nome"), //
        rs.getString("acesso"), //
        rs.getString("cep"), //
        rs.getString("logradouro"), //
        rs.getString("complemento"), //
        rs.getString("bairro"), //
        rs.getString("cidade"), //
        rs.getString("uf") //
    );
  }
}

// `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
// `celular` VARCHAR(11) NULL DEFAULT NULL,
// `email` VARCHAR(255) NOT NULL,
// `foto` VARCHAR(255) NOT NULL DEFAULT 'default.png',
// `senha` TEXT NOT NULL,
// `nome` VARCHAR(20) NOT NULL,
// `acesso` ENUM('normal', 'verificado', 'admin') NOT NULL DEFAULT 'normal',
// `cep` CHAR(8) NULL DEFAULT NULL,
// `logradouro` VARCHAR(255) NULL DEFAULT NULL,
// `complemento` VARCHAR(255) NULL DEFAULT NULL,
// `bairro` VARCHAR(255) NULL DEFAULT NULL,
// `cidade` VARCHAR(255) NULL DEFAULT NULL,
// `uf` CHAR(2) NULL DEFAULT NULL,
