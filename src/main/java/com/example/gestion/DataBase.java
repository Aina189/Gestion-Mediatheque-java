package com.example.gestion;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DataBase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB_MEDIATHEQUE = "jdbc:mysql://localhost:3306/mediatheque";


    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_MEDIATHEQUE, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null; // Gérer l'erreur de connexion
        }
    }
    public static Connection connectDBTest() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de donnee...");
            return null; // Gérer l'erreur de connexion
        }

    }
    public void creatDataBase() throws SQLException {
        Connection connection = connectDBTest();
        try (Statement statement = connection.createStatement()) {
            // Exécutez ici vos requêtes de création de tables ou d'autres opérations
            // Exemple :
            String createDataBAseQuery = "CREATE DATABASE mediatheque";
            statement.executeUpdate(createDataBAseQuery);

            // Ajoutez d'autres requêtes selon vos besoins...
            System.out.println("Base de données créée avec succès !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();

    }

    public void createTable() throws SQLException {
        Connection connection1 = connectDB();
        try (Statement statement = connection1.createStatement()){
            String createTableMembreQuery = "CREATE TABLE membres (\n" +
                    "    Id_membre INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    nom_membre VARCHAR(255),\n" +
                    "    prenom_membre VARCHAR(255),\n" +
                    "    sex_membre ENUM('feminin', 'masculin'),\n" +
                    "    birth_membre DATE,\n" +
                    "    adress_membre VARCHAR(255),\n" +
                    "    occupation_membre ENUM('Etudiant(e)', 'Salarier'),\n" +
                    "    cin_membre VARCHAR(20),\n" +
                    "    mail_membre VARCHAR(255)\n" +
                    ");";
            String createTableAbnQuery = "CREATE TABLE abonnement (\n" +
                    "    Id_membre INT,\n" +
                    "    date_debut_abn DATE,\n" +
                    "    date_fin_abn DATE,\n" +
                    "    type_abn ENUM('Individuel', 'Groupe'),\n" +
                    "    FOREIGN KEY (Id_membre) REFERENCES membres(Id_membre)\n" +
                    ");";
            String makeUnique = "ALTER TABLE `abonnement` ADD UNIQUE(`Id_membre`);";
            statement.executeUpdate(createTableMembreQuery);
            statement.executeUpdate(createTableAbnQuery);
            statement.executeUpdate(makeUnique);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection1.close();
        System.out.println("Tables créée avec succès !");
    }

    public static boolean isMediathequeDatabaseExists() {
        try (Connection connection = connectDBTest()) {
            if (connection != null) {
                return databaseExists(connection, "mediatheque");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean databaseExists(Connection connection, String dbName) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
