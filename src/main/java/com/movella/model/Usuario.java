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
    setId(0);
    setCelular(null);
    setEmail(null);
    setFoto(null);
    setSenha(null);
    setNome(null);
    setAcesso(null);
    setCep(null);
    setCpf(null);
    setLogradouro(null);
    setComplemento(null);
    setBairro(null);
    setCidade(null);
    setUf(null);
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
    setId(id);
    setCelular(celular);
    setEmail(email);
    setFoto(foto);
    setSenha(senha);
    setNome(nome);
    setAcesso(acesso);
    setCep(cep);
    setCpf(cpf);
    setLogradouro(logradouro);
    setComplemento(complemento);
    setBairro(bairro);
    setCidade(cidade);
    setUf(uf);
  }

  public int getId() {
    return this.id;
  };

  public String getCelular() {
    return this.celular;
  };

  public String getEmail() {
    return this.email;
  };

  public String getFoto() {
    return this.foto;
  };

  public String getSenha() {
    return this.senha;
  };

  public String getNome() {
    return this.nome;
  };

  public String getAcesso() {
    return this.acesso;
  };

  public String getCep() {
    return this.cep;
  };

  public String getCpf() {
    return this.cpf;
  };

  public String getLogradouro() {
    return this.logradouro;
  };

  public String getComplemento() {
    return this.complemento;
  };

  public String getBairro() {
    return this.bairro;
  };

  public String getCidade() {
    return this.cidade;
  };

  public String getUf() {
    return this.uf;
  };

  public void setId(int id) {
    this.id = id;
  }

  public void setCelular(String celular) {
    this.celular = celular;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setAcesso(String acesso) {
    this.acesso = acesso;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public void setUf(String uf) {
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
        usuario.setId(js.get("id").getAsInt());
        break;
      case "celular":
        usuario.setCelular(js.get("celular").getAsString());
        break;
      case "email":
        usuario.setEmail(js.get("email").getAsString());
        break;
      case "foto":
        usuario.setFoto(js.get("foto").getAsString());
        break;
      case "senha":
        usuario.setSenha(js.get("senha").getAsString());
        break;
      case "nome":
        usuario.setNome(js.get("nome").getAsString());
        break;
      case "acesso":
        usuario.setAcesso(js.get("acesso").getAsString());
        break;
      case "cep":
        usuario.setCep(js.get("cep").getAsString());
        break;
      case "cpf":
        usuario.setCpf(js.get("cpf").getAsString());
        break;
      case "logradouro":
        usuario.setLogradouro(js.get("logradouro").getAsString());
        break;
      case "complemento":
        usuario.setComplemento(js.get("complemento").getAsString());
        break;
      case "bairro":
        usuario.setBairro(js.get("bairro").getAsString());
        break;
      case "cidade":
        usuario.setCidade(js.get("cidade").getAsString());
        break;
      case "uf":
        usuario.setUf(js.get("uf").getAsString());
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
