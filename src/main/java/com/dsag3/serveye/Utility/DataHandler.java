package com.dsag3.serveye.Utility;

import com.dsag3.serveye.Models.ResponseModel;
import javafx.scene.chart.XYChart;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class DataHandler {
    private static final String DB_URL = getSecretValue("DBUrl");
    private static final String USER = getSecretValue("DBUsername");
    private static final String PASSWORD = getSecretValue("DBPass");
    public static LinkedList<ResponseModel> fetchDataFromDatabase() {
        LinkedList<ResponseModel> data = new LinkedList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String sql = "SELECT * FROM alpha_table";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                    // Create ResponseModel objects and add them to the LinkedList
                    ResponseModel model = new ResponseModel(
                            resultSet.getInt("responseID"),
                            resultSet.getString("currTimeStamp"),
                            resultSet.getInt("overallExperience"),
                            resultSet.getInt("ambianceRating"),
                            resultSet.getInt("staffInteraction"),
                            resultSet.getInt("foodQuality"),
                            resultSet.getInt("menuVariety"),
                            resultSet.getInt("waitingTime"),
                            resultSet.getInt("cleanlinessRating"),
                            resultSet.getInt("valueForMoney"),
                            resultSet.getInt("recommendationLikelihood"),
                            resultSet.getString("additionalComments")
                    );
                    data.add(model);
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        return data;
    }

    public static String getSecretValue(String field) {
        Properties properties = new Properties();
        try (InputStream input = DataHandler.class.getClassLoader().getResourceAsStream("cfg.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(field);
    }
}