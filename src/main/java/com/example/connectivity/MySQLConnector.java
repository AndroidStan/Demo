package com.example.connectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class MySQLConnector {
    Connection databaseConnection;

    public void connect(String url, String username, String password) {
        try {
            this.databaseConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            throw new RuntimeException(e);
        }
    }

    public void disconnect()  {
        if(this.databaseConnection != null) {
            try {
                this.databaseConnection.close();
            } catch (SQLException e) {
                System.out.println("Database close connection failed!");
                throw new RuntimeException(e);
            }
        }
    }

    public void insertDatabaseRecord(String tableName,  Map<String, String> dbRecordData ) {
        //dbRecordData contains keys - column names and values corresponding to the value of each column
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");

        int columnsNumber = 0;
        for (Map.Entry<String, String> entry : dbRecordData.entrySet()) {
            String key = entry.getKey();
            sql.append(key).append(", ");
            columnsNumber ++;
        }

        // Remove the last ", " after the last column name
        sql.setLength(Math.max(sql.length() - 2, 0));

        sql.append(") VALUES (");
        sql.append("?, ".repeat(Math.max(0, columnsNumber)));

        // Remove the last ", " after the ?
        sql.setLength(Math.max(sql.length() - 2, 0));

        sql.append(")");

        PreparedStatement statement = null;
        try {
            statement = this.databaseConnection.prepareStatement(sql.toString());
        } catch (SQLException e) {
            System.out.println("Prepare Statement failed!");
            throw new RuntimeException(e);
        }

        columnsNumber = 1;
        for (Map.Entry<String, String> entry : dbRecordData.entrySet()) {
            String value = entry.getValue();
            try {
                statement.setString(columnsNumber, value);
            } catch (SQLException e) {
                System.out.println("PreparedStatement setString() failed!");
                throw new RuntimeException(e);
            }
            columnsNumber ++;
        }

        int rows;
        try {
            rows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(rows > 0) {
            System.out.println("A database table row data has been inserted!");
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Statement close connection failed!");
            throw new RuntimeException(e);
        }
    }

}
