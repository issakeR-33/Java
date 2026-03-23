package org.example;
import java.sql.ResultSetMetaData;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final Properties properties = new Properties();
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть логін: ");
        String login = scanner.nextLine();

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        try (FileInputStream in = new FileInputStream("D://Java_labs//lab5//lab5//src//main//resources//config.properties")) {
            properties.load(in);
            URL = properties.getProperty("db.URL");
            USERNAME = properties.getProperty("db.USERNAME");
            PASSWORD = properties.getProperty("db.PASSWORD");
        } catch (IOException e){
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT * FROM `Users` WHERE users_login = ? AND users_password = SHA2(?, 256)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, login);
                statement.setString(2, password);
                ResultSet userResult = statement.executeQuery();

                if (userResult.next()) {
                    System.out.println("Авторизація успішна! Вітаємо, " + login);

                    // DEPARTAMENTS
                    System.out.println("\n- DEPARTAMENTS -");

                    // Insert
                    String sqlInsertDep = "INSERT IGNORE INTO `Departaments`(`departament_id`, `departament_name`, `departament_type`, `hazard_bonus`) VALUES (?,?,?,?)";
                    PreparedStatement insertDep = connection.prepareStatement(sqlInsertDep);
                    insertDep.setInt(1, 6);
                    insertDep.setString(2, "Name");
                    insertDep.setString(3, "type");
                    insertDep.setInt(4, 0);
                    System.out.println("Insert Departaments: " + insertDep.executeUpdate());

                    // Update
                    String sqlUpdateDep = "UPDATE `Departaments` SET `departament_name` = 'Дирекція', `hazard_bonus` = 5 WHERE `departament_id` = 1";
                    Statement updateDep = connection.createStatement();
                    System.out.println("Update Departaments: " + updateDep.executeUpdate(sqlUpdateDep));

                    // Delete
                    String sqlDeleteDep = "DELETE FROM `Departaments` WHERE `departament_id` = 6";
                    Statement deleteDep = connection.createStatement();
                    System.out.println("Delete Departaments: " + deleteDep.executeUpdate(sqlDeleteDep));

                    //Read
                    Statement readDep = connection.createStatement();
                    ResultSet readDepRes = readDep.executeQuery("SELECT * FROM `Departaments`");
                    while (readDepRes.next()){
                      System.out.println(
                              readDepRes.getInt("departament_id") +
                                      " " + readDepRes.getString("departament_name") +
                                      " " + readDepRes.getString("departament_type") +
                                      " " + readDepRes.getInt("hazard_bonus")

                      );
                    };
                    // ResultMetaDate
                    ResultSetMetaData metaData = readDepRes.getMetaData();
                    System.out.println("MetaDate");
                    for (int i = 1; i <= metaData.getColumnCount(); i++){
                        System.out.println(
                                "Column " + i
                                        + " Name " + metaData.getColumnName(i)
                                        + " Type " + metaData.getColumnTypeName(i));
                    }

                    readDep.close();
                    readDepRes.close();

                    //POSITION
                    System.out.println("\n- POSITION -");

                    // Insert
                    String sqlInsertPos = "INSERT IGNORE INTO `Position`(`position_id`, `position_title`, `base_salary`, `schedule_bonus`) VALUES (?,?,?,?)";
                    PreparedStatement insertPos = connection.prepareStatement(sqlInsertPos);
                    insertPos.setInt(1, 6);
                    insertPos.setString(2, "Тестова посада");
                    insertPos.setInt(3, 10000);
                    insertPos.setInt(4, 5);
                    System.out.println("Insert Position: " + insertPos.executeUpdate());

                    // Update
                    String sqlUpdatePos = "UPDATE `Position` SET `base_salary` = 55000 WHERE `position_id` = 1";
                    Statement updatePos = connection.createStatement();
                    System.out.println("Update Position: " + updatePos.executeUpdate(sqlUpdatePos));

                    // Delete
                    String sqlDeletePos = "DELETE FROM `Position` WHERE `position_id` = 6";
                    Statement deletePos = connection.createStatement();
                    System.out.println("Delete Position: " + deletePos.executeUpdate(sqlDeletePos));

                    // Read
                    Statement readPos = connection.createStatement();
                    ResultSet readPosRes = readPos.executeQuery("SELECT * FROM `Position`");
                    while (readPosRes.next()) {
                        System.out.println(readPosRes.getInt("position_id") + " " +
                                readPosRes.getString("position_title") + " " +
                                readPosRes.getInt("base_salary") + " " +
                                readPosRes.getInt("schedule_bonus"));
                    }
                    readPos.close();
                    readPosRes.close();

                    //STAFFING_TABLE
                    System.out.println("\n- STAFFING_TABLE -");

                    // Insert
                    String sqlInsertSt = "INSERT IGNORE INTO `Staffing_table`(`staffing_id`, `total_slots`, `vacant_slots`, `departament_id`, `position_id`) VALUES (?,?,?,?,?)";
                    PreparedStatement insertSt = connection.prepareStatement(sqlInsertSt);
                    insertSt.setInt(1, 6);
                    insertSt.setInt(2, 3);
                    insertSt.setInt(3, 1);
                    insertSt.setInt(4, 1);
                    insertSt.setInt(5, 1);
                    System.out.println("Insert Staffing_table: " + insertSt.executeUpdate());

                    // Update
                    String sqlUpdateSt = "UPDATE `Staffing_table` SET `vacant_slots` = 0 WHERE `staffing_id` = 1";
                    Statement updateSt = connection.createStatement();
                    System.out.println("Update Staffing_table: " + updateSt.executeUpdate(sqlUpdateSt));

                    // Delete
                    String sqlDeleteSt = "DELETE FROM `Staffing_table` WHERE `staffing_id` = 6";
                    Statement deleteSt = connection.createStatement();
                    System.out.println("Delete Staffing_table: " + deleteSt.executeUpdate(sqlDeleteSt));

                    // Read
                    Statement readSt = connection.createStatement();
                    ResultSet readStRes = readSt.executeQuery("SELECT * FROM `Staffing_table`");
                    while (readStRes.next()) {
                        System.out.println(readStRes.getInt("staffing_id") + " " +
                                readStRes.getInt("total_slots") + " " +
                                readStRes.getInt("vacant_slots") + " " +
                                readStRes.getInt("departament_id") + " " +
                                readStRes.getInt("position_id"));
                    }
                    readSt.close();
                    readStRes.close();

                } else {
                    System.out.println("Невірний логін або пароль!");
                }

                userResult.close();
                statement.close();

                //Search function
                Main.Search(35000);


            }

        } catch (SQLException e) {
            System.out.println("Щось пішло не так. Помилка: " + e.getMessage());
        }
    }
    public static void Search(int salary) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sqlSearch = "SELECT * FROM `Position` WHERE `base_salary` > ?";
            PreparedStatement statement = connection.prepareStatement(sqlSearch);
            statement.setInt(1,salary);
            ResultSet result = statement.executeQuery();
            System.out.println("-- SEARCH --");
            while (result.next()){
                System.out.println(result.getInt("position_id") + " " +
                        result.getString("position_title") + " " +
                        result.getInt("base_salary") + " " +
                        result.getInt("schedule_bonus"));
            };
            statement.close();
            result.close();
        } catch (SQLException e) {
            System.out.println("Щось пішло не так. Помилка: " + e.getMessage());
        }
    }
}