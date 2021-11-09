package com.movella.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

public class DBConnection {

  static final String driverName = "org.postgresql.Driver";

  static Connection connect() throws Exception {
    final ConnectionCreds connectionCreds = ConnectionCreds.get();

    final String url = String.format("jdbc:postgresql://%s:%d/%s", connectionCreds.server, connectionCreds.port,
        connectionCreds.defaultDatabase);

    try {
      Class.forName(driverName);
      Connection connection = DriverManager.getConnection(url, connectionCreds.username, connectionCreds.password);

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

      while (rs.next()) {
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

class ConnectionCreds {
  final String username;
  final String password;
  final String server;
  final int port;
  final String defaultDatabase;

  static ConnectionCreds connectionCreds = null;

  public static ConnectionCreds get() {
    if (connectionCreds == null)
      connectionCreds = new ConnectionCreds();

    return connectionCreds;
  }

  ConnectionCreds() {
    final String regex = "^(\\w+):\\/\\/(\\w+):(\\w+)@(.+):(\\d+)\\/(\\w+)$";
    final String string = System.getenv("DATABASE_URL");

    final Matcher matcher = Pattern.compile(regex).matcher(string);

    matcher.find();

    this.username = matcher.group(2);
    this.password = matcher.group(3);
    this.server = matcher.group(4);
    this.port = Integer.parseInt(matcher.group(5));
    this.defaultDatabase = matcher.group(6);
  }
}