package com.movella.dao;

import com.google.gson.JsonObject;
import com.movella.app.DBConnection;
import com.movella.exceptions.InvalidDataException;
import com.movella.model.Usuario;
import com.movella.utils.Localization;

public class UsuarioDAO {
  public static Usuario login(String email, String senha) throws Exception {
    final JsonObject res = DBConnection.queryOne("select * from tbl_usuario where email = ? and senha = ?",
        new String[] { email, senha });

    if (res == null)
      throw new InvalidDataException(Localization.userNotFound);

    final Usuario usuario = Usuario.fromJson(res);

    return usuario;
  }

  public static Usuario register(String nome, String email, String senha) throws Exception {
    DBConnection.execute("insert into tbl_usuario (nome, email, senha) values (?, ?, ?)",
        new String[] { nome, email, senha });

    final Usuario usuario = login(email, senha);

    return usuario;
  }

  public static Usuario update(String email, String acesso, String bairro, String celular, String cep, String cidade,
      String cpf, String complemento, String logradouro, String uf, String nome, String senha, int id)
      throws Exception {
    DBConnection.execute(
        "update tbl_usuario set acesso = cast(? as acesso), bairro = ?, celular = ?, cep = ?, cidade = ?, cpf = ?, complemento = ?, logradouro = ?, uf = ?, nome = ?, senha = ? where id = cast(? as integer)",
        new String[] { acesso, bairro, celular, cep, cidade, cpf, complemento, logradouro, uf, nome, senha,
            String.valueOf(id) });

    final Usuario usuario = login(email, senha);

    return usuario;
  }
}
