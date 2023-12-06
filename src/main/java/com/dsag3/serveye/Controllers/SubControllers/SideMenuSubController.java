package com.dsag3.serveye.Controllers.SubControllers;

import com.dsag3.serveye.Controllers.dbController;
import com.dsag3.serveye.Controllers.rpController;
import com.dsag3.serveye.Controllers.sgController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.LinkedList;

public class SideMenuSubController {
    private final Stage app;
    private boolean isDraggable;
    private final Scene dashboard;
    private final Scene responses;
    private final Scene suggestions;
    private final LinkedList<Button> buttons = new LinkedList<>();
    private double newHeight, newWidth, newX, newY;
    public SideMenuSubController(Stage app,
                                 Scene dashboard,
                                 Scene responses,
                                 Scene suggestions,
                                 Button btn_db,
                                 Button btn_rp,
                                 Button btn_sg) {
        this.app = app;
        this.dashboard = dashboard;
        this.responses = responses;
        this.suggestions = suggestions;
        buttons.add(btn_db);
        buttons.add(btn_rp);
        buttons.add(btn_sg);
    }

    public void init() {
        for (Button currButton : buttons) {
            currButton.setOnMouseClicked(event -> {
                // Identify the element clicked
                Button sourceButton = (Button) event.getSource();
                // Get new window dimensions for first switch
                newHeight = app.getHeight();
                newWidth = app.getWidth();
                newX = app.getX();
                newY = app.getY();
                // Switch scenes and set isDraggable
                switch (sourceButton.getText()) {
                    case "Dashboard":
                        app.setScene(dashboard);
                        break;
                    case "Responses":
                        app.setScene(responses);
                        break;
                    case "Suggestions":
                        app.setScene(suggestions);
                        break;
                }
                app.setWidth(newWidth);
                app.setHeight(newHeight);
                app.setX(newX);
                app.setY(newY);
            });
        }
    }
}
