package com.dsag3.serveye.Controllers.SubControllers;

import com.dsag3.serveye.Controllers.dbController;
import com.dsag3.serveye.Controllers.rpController;
import com.dsag3.serveye.Controllers.sgController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TitlebarSubController {
    private final Stage app;
    private final dbController dbCont;
    private final rpController rpCont;
    private final sgController sgCont;
    private final BorderPane titleBar;
    private final Button minimize;
    private final Button maximize;
    private final Button exit;
    public boolean isDraggable = true;
    private double mouseX, mouseY;
    public TitlebarSubController(Stage app,
                                 BorderPane titleBar,
                                 Button minimize,
                                 Button maximize,
                                 Button exit,
                                 dbController dbCont,
                                 rpController rpCont,
                                 sgController sgCont) {
        this.app = app;
        this.dbCont = dbCont;
        this.rpCont = rpCont;
        this.sgCont = sgCont;
        this.titleBar = titleBar;
        this.minimize = minimize;
        this.maximize = maximize;
        this.exit = exit;
    }

    public void init() {
        // Custom title bar actions
        minimize.setOnAction(event -> app.setIconified(true));
        maximize.setOnAction(event -> {
            isDraggable = dbCont.isDraggable;
            if (app.isMaximized()) {
                isDraggable = true;
                app.setMaximized(false);
            } else {
                isDraggable = false;
                app.setMaximized(true);
            }
            dbCont.isDraggable = isDraggable;
            rpCont.isDraggable = isDraggable;
            sgCont.isDraggable = isDraggable;
        });
        exit.setOnAction(event -> app.close());
        // Custom title bar drag
        titleBar.setOnMouseDragged(event -> {
            isDraggable = dbCont.isDraggable;
            if(isDraggable) {
                app.setX(event.getScreenX() - mouseX);
                app.setY(event.getScreenY() - mouseY);
            }
        });
        titleBar.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
    }
}
