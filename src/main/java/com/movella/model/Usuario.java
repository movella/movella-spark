package com.movella.model;

import java.sql.SQLException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Usuario {
  int id;
  String celular;
  String email;
  String foto;
  String senha;
  String nome;
  String acesso;
  String cep;
  String cpf;
  String logradouro;
  String complemento;
  String bairro;
  String cidade;
  String uf;

  public Usuario() {
    setid(0);
    setcelular(null);
    setemail(null);
    setfoto(null);
    setsenha(null);
    setnome(null);
    setacesso(null);
    setcep(null);
    setcpf(null);
    setlogradouro(null);
    setcomplemento(null);
    setbairro(null);
    setcidade(null);
    setuf(null);
  }

  public Usuario( //
      int id, //
      String celular, //
      String email, //
      String foto, //
      String senha, //
      String nome, //
      String acesso, //
      String cep, //
      String cpf, //
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
    setcpf(cpf);
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

  public String getcpf() {
    return this.cpf;
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

  public void setcpf(String cpf) {
    this.cpf = cpf;
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

  public static Usuario fromJson(JsonObject js) throws SQLException {
    final Usuario usuario = new Usuario();

    for (String key : js.keySet()) {
      final String uKey = key.toLowerCase();
      final JsonElement val = js.get(uKey.toLowerCase());

      if (val == null)
        continue;

      switch (uKey) {
      case "id":
        usuario.setid(js.get("id").getAsInt());
        break;
      case "celular":
        usuario.setcelular(js.get("celular").getAsString());
        break;
      case "email":
        usuario.setemail(js.get("email").getAsString());
        break;
      case "foto":
        usuario.setfoto(js.get("foto").getAsString());
        break;
      case "senha":
        usuario.setsenha(js.get("senha").getAsString());
        break;
      case "nome":
        usuario.setnome(js.get("nome").getAsString());
        break;
      case "acesso":
        usuario.setacesso(js.get("acesso").getAsString());
        break;
      case "cep":
        usuario.setcep(js.get("cep").getAsString());
        break;
      case "cpf":
        usuario.setcpf(js.get("cpf").getAsString());
        break;
      case "logradouro":
        usuario.setlogradouro(js.get("logradouro").getAsString());
        break;
      case "complemento":
        usuario.setcomplemento(js.get("complemento").getAsString());
        break;
      case "bairro":
        usuario.setbairro(js.get("bairro").getAsString());
        break;
      case "cidade":
        usuario.setcidade(js.get("cidade").getAsString());
        break;
      case "uf":
        usuario.setuf(js.get("uf").getAsString());
        break;
      }
    }

    return usuario;
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
