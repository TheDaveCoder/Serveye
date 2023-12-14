package com.dsag3.serveye.Utility;

import com.dsag3.serveye.Models.GeneralInfo;
import com.dsag3.serveye.Models.ResponseModel;
import com.dsag3.serveye.Serveye;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.Properties;

public class FetchSuggestions {
    private static final String url = "https://api.openai.com/v1/chat/completions";
    private static final String APIKey = getAPIKey();
    private static final String model = "gpt-3.5-turbo";
    public static LinkedList<String> getSuggestions(GeneralInfo generalInformation) {
        LinkedList<String> suggestionList = new LinkedList<>();
        if(!generalInformation.responseList.isEmpty()) {
            for (int i = 0; i < 9; i++) {
                String message = buildMessage(generalInformation, i);
                try {
                    // Open the connection
                    URL obj = new URL(url);
                    HttpsURLConnection endPointConnection = (HttpsURLConnection) obj.openConnection();
                    endPointConnection.setRequestMethod("POST");
                    endPointConnection.setRequestProperty("Authorization", "Bearer " + APIKey);
                    endPointConnection.setRequestProperty("Content-Type", "application/json");

                    // Create the body of the request
                    String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
                    endPointConnection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(endPointConnection.getOutputStream());
                    writer.write(body);
                    writer.flush();
                    writer.close();

                    // Submit the request
                    BufferedReader in = new BufferedReader(new InputStreamReader(endPointConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    // append to list
                    suggestionList.add(extractContentFromResponse(response.toString()));

                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return suggestionList;
    }
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

    public static String buildMessage(GeneralInfo generalInformation, int currIndex) {
        String message = "Create a short but detailed suggestion (50 word maximum) for me (not the customer), based on the category and score of my restaurant (MAKE SURE to mention the rating in your suggestion, and MAKE SURE to NOT USE DOUBLE QUOTATION MARKS in your suggestion).";
        String category = "Category: ";
        String rating = "Rating: ";
        category = switch (currIndex) {
            case 0 -> category + "Overall Experience, ";
            case 1 -> category + "Ambiance Rating, ";
            case 2 -> category + "Staff Interaction, ";
            case 3 -> category + "Food Quality, ";
            case 4 -> category + "Menu Variety, ";
            case 5 -> category + "Waiting Time, ";
            case 6 -> category + "Cleanliness Rating, ";
            case 7 -> category + "Value for Money, ";
            case 8 -> category + "How Likely they are to recommend the restaurant, ";
            default -> category;
        };
        rating = rating + generalInformation.categoryAverage.get(currIndex);
        message = message + category + rating;
        return message;
    }

    public static String getAPIKey() {
        Properties properties = new Properties();
        try (InputStream input = FetchSuggestions.class.getClassLoader().getResourceAsStream("cfg.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("apiKey");
    }
}