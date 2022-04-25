package com.example.firm.jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    public static Connection getNewConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\accounting_firm_automation\\db_firm");
        if (connection.isValid(1)) {
            System.out.println("Connection successful");
        }
        return connection;
    }
}
