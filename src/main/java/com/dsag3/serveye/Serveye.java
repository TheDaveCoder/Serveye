package com.dsag3.serveye;

import com.dsag3.serveye.Controllers.dbController;
import com.dsag3.serveye.Controllers.rpController;
import com.dsag3.serveye.Controllers.sgController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

        // Initialize controllers
        dbCont.init(app, dashboard, responses, suggestions, rpCont, sgCont);
        rpCont.init(app, dashboard, responses, suggestions, dbCont, sgCont);
        sgCont.init(app, dashboard, responses, suggestions, dbCont, rpCont);

        // invoke controllers
        dbCont.handleControls();
        rpCont.handleControls();
        sgCont.handleControls();

        // Set Stage Properties
        app.initStyle(StageStyle.TRANSPARENT);
        app.setMinWidth(700);
        app.setMinHeight(510);
        app.setTitle("Serveye");
        app.setScene(dashboard); // sets the dashboard as the primary scene
        app.show();
    }
}