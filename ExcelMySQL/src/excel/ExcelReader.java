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
package excel;

import data.DBConnection;
import data.MySQLDBConnection;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Daniel Ricardo Sequeira Campos <https://rdani2005.works>
 */
public class ExcelReader {

    private String excelPath;
    private DBConnection dbConnection;

    public ExcelReader(String excelPath) {
        this.excelPath = excelPath;
        this.dbConnection = new MySQLDBConnection();
        System.out.println("--> Here we are going to read the file: " + excelPath);
    }

    public ExcelReader(String excelPath, DBConnection dbConnection) {
        this.excelPath = excelPath;
        this.dbConnection = dbConnection;
    }

    public void insertData() {
       
        try {
            File file = new File(excelPath);
            Connection conn = dbConnection.getConnection(); 
            InputStream inputStream = new FileInputStream(file);  
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                
                
            Sheet sheet = workbook.getSheetAt(0);
            String insertQuery = "INSERT INTO data (Id, Name, LastName, Address, Email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            sheet.shiftRows(1, sheet.getLastRowNum(), -1);
            for (Row row : sheet) {
                Cell Id = row.getCell(0);
                Cell Name = row.getCell(1);
                Cell LastName = row.getCell(2);
                Cell Address = row.getCell(3);
                Cell Email = row.getCell(4);
                
                preparedStatement.setDouble(1, Id.getNumericCellValue());
                preparedStatement.setString(2, Name.getStringCellValue());
                preparedStatement.setString(3, LastName.getStringCellValue());
                preparedStatement.setString(4, Address.getStringCellValue());
                preparedStatement.setString(5, Email.getStringCellValue());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            System.out.println("Data Added successfully!");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
    }
}
