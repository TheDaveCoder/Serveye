package com.dsag3.serveye.Utility;

import com.dsag3.serveye.Models.ResponseModel;

import java.sql.*;
import java.util.LinkedList;

public class DataHandler {
    private static final String DB_URL = "jdbc:mysql://database-1.cl85pye4up69.ap-southeast-1.rds.amazonaws.com:3306/alpha_test";
    private static final String USER = "sf_app_admin";
    private static final String PASSWORD = "Drakathportal1atrds";
    public static LinkedList<ResponseModel> fetchDataFromDatabase() {
        LinkedList<ResponseModel> data = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT * FROM alpha_table";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                    // Create ResponseModel objects and add them to the LinkedList
                    ResponseModel model = new ResponseModel(
                            resultSet.getInt("responseID"),
                            resultSet.getString("timeStamp"),
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
}