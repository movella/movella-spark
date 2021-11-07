package com.movella.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

public class DBConnection {
  static final String server = System.getenv("PGSQL_SERVER");
  static final String defaultDatabase = System.getenv("PGSQL_DATABASE");
  static final String username = System.getenv("PGSQL_USER");
  static final String password = System.getenv("PGSQL_PASSWORD");
  static final int port = Integer.parseInt(System.getenv("PGSQL_PORT"));

  static final String driverName = "org.postgresql.Driver";
  static final String url = String.format("jdbc:postgresql://%s:%d/%s", server, port, defaultDatabase);

  static Connection connect() throws Exception {
    try {
      Class.forName(driverName);
      Connection connection = DriverManager.getConnection(url, username, password);

      System.out.println("Connected");

      return connection;
    } catch (ClassNotFoundException e) {
      System.err.println(e);

      throw e;
    } catch (SQLException e) {
      System.err.println(e);

      throw e;
    }
  }

  public static void execute(String sql, String[] params) throws Exception {
    final Connection connection = connect();

    try {
      final PreparedStatement st = connection.prepareStatement(sql);

      int i = 0;
      for (String p : params)
        st.setString(++i, p);

      st.execute();
      st.close();
      connection.close();
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public static JsonObject queryOne(String sql, String[] params) throws Exception {
    JsonObject out = null;
    final Connection connection = connect();

    try {
      final PreparedStatement st = connection.prepareStatement(sql);

      int i = 0;
      for (String p : params)
        st.setString(++i, p);

      final ResultSet rs = st.executeQuery();

      final ResultSetMetaData rsmd = rs.getMetaData();
      final int columnCount = rsmd.getColumnCount();

      if (rs.next()) {
        out = new JsonObject();
        for (int j = 1; j <= columnCount; j++) {
          final String columnName = rsmd.getColumnName(j);
          final Object prop = rs.getObject(columnName);
          if (prop != null)
            out.addProperty(columnName, prop.toString());
        }
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return out;
  }

  public static List<JsonObject> query(String sql, String[] params) throws Exception {
    final List<JsonObject> out = new ArrayList<JsonObject>();
    final Connection connection = connect();

    try {
      final PreparedStatement st = connection.prepareStatement(sql);

      int i = 0;
      for (String p : params)
        st.setString(++i, p);

      final ResultSet rs = st.executeQuery();

      final ResultSetMetaData rsmd = rs.getMetaData();
      final int columnCount = rsmd.getColumnCount();

      if (rs.next()) {
        final JsonObject jsonObject = new JsonObject();

        for (int j = 1; j <= columnCount; j++) {
          final String columnName = rsmd.getColumnName(j);
          final Object prop = rs.getObject(columnName);
          if (prop != null)
            jsonObject.addProperty(columnName, prop.toString());
        }

        out.add(jsonObject);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return out;
  }
}
