package com.movella;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
  static String server = System.getenv("PGSQL_SERVER");
  static String defaultDatabase = System.getenv("PGSQL_DATABASE");
  static String username = System.getenv("PGSQL_USER");
  static String password = System.getenv("PGSQL_PASSWORD");
  static int port = Integer.parseInt(System.getenv("PGSQL_PORT"));

  static String driverName = "org.postgresql.Driver";
  static String url = String.format("jdbc:postgresql://%s:%d/%s", server, port, defaultDatabase);

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

  public static void execute(String sql) throws Exception {
    Connection connection = connect();

    try {
      Statement st = connection.createStatement();
      st.execute(sql);
      st.close();
      connection.close();
    } catch (SQLException u) {
      throw new RuntimeException(u);
    }
  }

  public static ResultSet queryOne(String sql) throws Exception {
    ResultSet out = null;
    Connection connection = connect();

    try {
      Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(sql);

      if (rs.first())
        out = rs;

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return out;
  }

  public static List<ResultSet> query(String sql) throws Exception {
    List<ResultSet> out = new ArrayList<ResultSet>();
    Connection connection = connect();

    try {
      Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(sql);

      if (rs.next()) {
        rs.beforeFirst();

        while (rs.next())
          out.add(rs);
      }

      st.close();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return out;
  }
}
