package com.dsag3.serveye;

import com.dsag3.serveye.Controllers.dbController;
import com.dsag3.serveye.Controllers.rpController;
import com.dsag3.serveye.Controllers.sgController;
import com.dsag3.serveye.Models.ResponseModel;
import com.dsag3.serveye.Utility.DataHandler;
import com.dsag3.serveye.Models.GeneralInfo;
import com.dsag3.serveye.Utility.DataUpdater;
import com.dsag3.serveye.Utility.FetchSuggestions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Serveye extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Create primary stage
        Stage app = new Stage();

        // Create the three root menu nodes
        FXMLLoader[] rootMenus = new FXMLLoader[3];

        // Set the values of the root menu nodes
        rootMenus[0] = new FXMLLoader(getClass().getResource("rootMenu-db.fxml"));
        rootMenus[1] = new FXMLLoader(getClass().getResource("rootMenu-rp.fxml"));
        rootMenus[2] = new FXMLLoader(getClass().getResource("rootMenu-sg.fxml"));

        // Load the FXML files
        Scene dashboard = new Scene(rootMenus[0].load());
        Scene responses = new Scene(rootMenus[1].load());
        Scene suggestions = new Scene(rootMenus[2].load());

        // Get controllers
        dbController dbCont = rootMenus[0].getController();
        rpController rpCont = rootMenus[1].getController();
        sgController sgCont = rootMenus[2].getController();

        // Get Initial Survey Responses
        LinkedList<ResponseModel> initialData = DataHandler.fetchDataFromDatabase();
        // Setup General Info Object
        GeneralInfo genInf = new GeneralInfo(initialData);
        // Setup Initial Suggestions
        LinkedList<String> suggestionList = FetchSuggestions.getSuggestions(genInf);
        // Setup Scheduler
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Easter Egg
        Stage popup = new Stage();
        FXMLLoader popupFXML = new FXMLLoader(getClass().getResource("easter-egg.fxml"));
        Scene primaryScene = new Scene(popupFXML.load());
        popup.setScene(primaryScene);
        popup.setTitle("Rawr");

        // Initialize controllers and initial data
        dbCont.init(app, dashboard, responses, suggestions, rpCont, sgCont, scheduler, genInf, popup);
        rpCont.init(app, dashboard, responses, suggestions, dbCont, sgCont, scheduler, initialData);
        sgCont.init(app, dashboard, responses, suggestions, dbCont, rpCont, scheduler, suggestionList);

        // invoke controllers
        dbCont.handleControls();
        rpCont.handleControls();
        sgCont.handleControls();

        // Set Stage Properties
        app.initStyle(StageStyle.TRANSPARENT);
        app.getIcons().add(new Image("file:/D:/Libraries/Programming/JavaApp/Serveye/src/main/resources/icons/serveye.png"));
        app.setMinWidth(700);
        app.setMinHeight(510);
        app.setTitle("Serveye");
        app.setScene(dashboard); // sets the dashboard as the primary scene
        app.show();

        // Start updater every X seconds
        DataUpdater dataUpdater = new DataUpdater(genInf, suggestionList, dbCont, rpCont, sgCont, scheduler);
        dataUpdater.startUpdating();
    }
}