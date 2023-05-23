/*
 * The MIT License
 *
 * Copyright 2023 Daniel Ricardo Sequeira Campos <https://rdani2005.works>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Daniel Ricardo Sequeira Campos <https://rdani2005.works>
 */
public class MySQLDBConnection implements DBConnection {

    private final String DB_URL;
    private final String USERNAME;
    private final String PASSWORD;
    private Connection conn;
    public MySQLDBConnection() {
        this.DB_URL = "jdbc:mysql://localhost:3306/excel";
        this.USERNAME = "root";
        this.PASSWORD = "Seque1505!";
    }

    public MySQLDBConnection(String DB_URL, String USERNAME, String PASSWORD) {
        this.DB_URL = DB_URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    @Override
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("BD Connection Done.");
        } catch (SQLException e) {
            System.out.println("Error on connecting to DB. " + e);
        }
        return conn;
    }

    @Override
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection to DB Closed.");
            } catch (SQLException e) {
                System.out.println("Error while closing DB connection: "
                        + e.getMessage()
                );
            }
        }
    }
}
