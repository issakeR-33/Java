package org.example;

import java.sql.ResultSetMetaData;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    // Конфіг підключення зберігається окремо — не хардкодимо дані в коді
    private static final Properties config = new Properties();
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Логін: ");
        String login = scanner.nextLine();

        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        // Завантажуємо параметри підключення з config.properties
        try (FileInputStream in = new FileInputStream("src/main/resources/config.properties")) {
            config.load(in);
            dbUrl      = config.getProperty("db.URL");
            dbUser     = config.getProperty("db.USERNAME");
            dbPassword = config.getProperty("db.PASSWORD");
        } catch (IOException e) {
            System.out.println("Не вдалося завантажити конфіг: " + e.getMessage());
            return;
        }

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

            if (conn == null || conn.isClosed()) {
                System.out.println("З'єднання з базою не встановлено.");
                return;
            }

            // Авторизація — пароль зберігається як SHA-256 хеш, не у відкритому вигляді
            String authSql = "SELECT * FROM `Users` WHERE users_login = ? AND users_password = SHA2(?, 256)";
            PreparedStatement authStmt = conn.prepareStatement(authSql);
            authStmt.setString(1, login);
            authStmt.setString(2, password);
            ResultSet authResult = authStmt.executeQuery();

            if (!authResult.next()) {
                System.out.println("Невірний логін або пароль.");
                return;
            }

            System.out.println("Ласкаво просимо, " + login + "!\n");

            // ==================== COMPUTERS ====================
            System.out.println("=== COMPUTERS ===");

            // Insert — додаємо тестовий ПК
            String insertComputerSql = "INSERT IGNORE INTO `Computers`(`computer_id`, `computer_name`, `specs`, `hourly_rate`, `is_available`) VALUES (?,?,?,?,?)";
            PreparedStatement insertComputer = conn.prepareStatement(insertComputerSql);
            insertComputer.setInt(1, 10);
            insertComputer.setString(2, "PC-10");
            insertComputer.setString(3, "Тестовий ПК");
            insertComputer.setDouble(4, 25.00);
            insertComputer.setInt(5, 1);
            System.out.println("Insert Computers: " + insertComputer.executeUpdate());

            // Update — змінюємо ціну першого ПК
            String updateComputerSql = "UPDATE `Computers` SET `hourly_rate` = 65.00 WHERE `computer_id` = 1";
            Statement updateComputer = conn.createStatement();
            System.out.println("Update Computers: " + updateComputer.executeUpdate(updateComputerSql));

            // Delete — видаляємо тестовий ПК
            String deleteComputerSql = "DELETE FROM `Computers` WHERE `computer_id` = 10";
            Statement deleteComputer = conn.createStatement();
            System.out.println("Delete Computers: " + deleteComputer.executeUpdate(deleteComputerSql));

            // Read — виводимо всі комп'ютери
            Statement readComputers = conn.createStatement();
            ResultSet computerRows = readComputers.executeQuery("SELECT * FROM `Computers`");
            System.out.println("-- Список комп'ютерів --");
            while (computerRows.next()) {
                System.out.println(
                        computerRows.getInt("computer_id")      + " | " +
                                computerRows.getString("computer_name")  + " | " +
                                computerRows.getString("specs")          + " | " +
                                computerRows.getDouble("hourly_rate")    + " грн/год | " +
                                (computerRows.getInt("is_available") == 1 ? "вільний" : "зайнятий")
                );
            }

            // ResultSetMetaData — показуємо структуру таблиці
            ResultSetMetaData computerMeta = computerRows.getMetaData();
            System.out.println("-- MetaData Computers --");
            for (int i = 1; i <= computerMeta.getColumnCount(); i++) {
                System.out.println(
                        "Column " + i +
                                " | Name: " + computerMeta.getColumnName(i) +
                                " | Type: " + computerMeta.getColumnTypeName(i)
                );
            }

            readComputers.close();
            computerRows.close();

            // ==================== CLIENTS ====================
            System.out.println("\n=== CLIENTS ===");

            // Insert
            String insertClientSql = "INSERT IGNORE INTO `Clients`(`client_id`, `client_name`, `client_phone`, `balance`) VALUES (?,?,?,?)";
            PreparedStatement insertClient = conn.prepareStatement(insertClientSql);
            insertClient.setInt(1, 10);
            insertClient.setString(2, "Тест Тестович");
            insertClient.setString(3, "+380000000000");
            insertClient.setDouble(4, 0.00);
            System.out.println("Insert Clients: " + insertClient.executeUpdate());

            // Update — поповнюємо баланс клієнта
            String updateClientSql = "UPDATE `Clients` SET `balance` = 300.00 WHERE `client_id` = 1";
            Statement updateClient = conn.createStatement();
            System.out.println("Update Clients: " + updateClient.executeUpdate(updateClientSql));

            // Delete
            String deleteClientSql = "DELETE FROM `Clients` WHERE `client_id` = 10";
            Statement deleteClient = conn.createStatement();
            System.out.println("Delete Clients: " + deleteClient.executeUpdate(deleteClientSql));

            // Read
            Statement readClients = conn.createStatement();
            ResultSet clientRows = readClients.executeQuery("SELECT * FROM `Clients`");
            System.out.println("-- Список клієнтів --");
            while (clientRows.next()) {
                System.out.println(
                        clientRows.getInt("client_id")      + " | " +
                                clientRows.getString("client_name")  + " | " +
                                clientRows.getString("client_phone") + " | " +
                                "баланс: " + clientRows.getDouble("balance") + " грн"
                );
            }
            readClients.close();
            clientRows.close();

            // ==================== SESSIONS ====================
            System.out.println("\n=== SESSIONS ===");

            // Insert
            String insertSessionSql = "INSERT IGNORE INTO `Sessions`(`session_id`, `client_id`, `computer_id`, `start_time`, `duration_hrs`) VALUES (?,?,?,?,?)";
            PreparedStatement insertSession = conn.prepareStatement(insertSessionSql);
            insertSession.setInt(1, 10);
            insertSession.setInt(2, 1);
            insertSession.setInt(3, 1);
            insertSession.setString(4, "2025-03-10 12:00:00");
            insertSession.setDouble(5, 1.0);
            System.out.println("Insert Sessions: " + insertSession.executeUpdate());

            // Update — збільшуємо тривалість сесії
            String updateSessionSql = "UPDATE `Sessions` SET `duration_hrs` = 4.0 WHERE `session_id` = 1";
            Statement updateSession = conn.createStatement();
            System.out.println("Update Sessions: " + updateSession.executeUpdate(updateSessionSql));

            // Delete
            String deleteSessionSql = "DELETE FROM `Sessions` WHERE `session_id` = 10";
            Statement deleteSession = conn.createStatement();
            System.out.println("Delete Sessions: " + deleteSession.executeUpdate(deleteSessionSql));

            // Read
            Statement readSessions = conn.createStatement();
            ResultSet sessionRows = readSessions.executeQuery("SELECT * FROM `Sessions`");
            System.out.println("-- Список сесій --");
            while (sessionRows.next()) {
                System.out.println(
                        sessionRows.getInt("session_id")     + " | " +
                                "клієнт: "  + sessionRows.getInt("client_id")   + " | " +
                                "ПК: "      + sessionRows.getInt("computer_id") + " | " +
                                sessionRows.getString("start_time")              + " | " +
                                sessionRows.getDouble("duration_hrs") + " год"
                );
            }
            readSessions.close();
            sessionRows.close();

            authResult.close();
            authStmt.close();

            // Пошук вільних комп'ютерів за максимальною ціною за годину
            findAvailableByRate(50.00);

        } catch (SQLException e) {
            System.out.println("Помилка при роботі з базою: " + e.getMessage());
        }
    }

    // Шукаємо вільні ПК з ціною не більше вказаної
    public static void findAvailableByRate(double maxRate) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String searchSql = "SELECT * FROM `Computers` WHERE `is_available` = 1 AND `hourly_rate` <= ?";
            PreparedStatement searchStmt = conn.prepareStatement(searchSql);
            searchStmt.setDouble(1, maxRate);
            ResultSet found = searchStmt.executeQuery();

            System.out.println("\n=== ПОШУК: вільні ПК до " + maxRate + " грн/год ===");
            boolean hasResults = false;
            while (found.next()) {
                hasResults = true;
                System.out.println(
                        found.getInt("computer_id")      + " | " +
                                found.getString("computer_name")  + " | " +
                                found.getString("specs")          + " | " +
                                found.getDouble("hourly_rate")    + " грн/год"
                );
            }
            if (!hasResults) {
                System.out.println("Вільних ПК за такою ціною немає.");
            }

            searchStmt.close();
            found.close();
        } catch (SQLException e) {
            System.out.println("Помилка пошуку: " + e.getMessage());
        }
    }
}