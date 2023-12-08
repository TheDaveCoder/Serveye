package com.dsag3.serveye.Controllers;

import com.dsag3.serveye.Controllers.SubControllers.ResizeSubController;
import com.dsag3.serveye.Controllers.SubControllers.SideMenuSubController;
import com.dsag3.serveye.Controllers.SubControllers.TitlebarSubController;
import com.dsag3.serveye.Models.GeneralInfo;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class sgController {
    // FXML Injections
    @FXML
    public BorderPane titleBar;
    public Button btn_db;
    public Button btn_rp;
    public Button btn_sg;
    @FXML
    public Region topResizeHandle;
    @FXML
    public Region topLeftResizeHandle;
    @FXML
    public Region topRightResizeHandle;
    @FXML
    public Region rightResizeHandle;
    @FXML
    public Region bottomResizeHandle;
    @FXML
    public Region bottomLeftResizeHandle;
    @FXML
    public Region bottomRightResizeHandle;
    @FXML
    public Region leftResizeHandle;
    @FXML
    public Button minimize;
    @FXML
    public Button maximize;
    @FXML
    public Button exit;
    @FXML
    public FlowPane flowPaneContainer;
    // Sub Controllers
    private TitlebarSubController titleBarSC;
    private SideMenuSubController sideMenuSC;
    private ResizeSubController resizeSC;
    // Local Fields
    private Stage app;
    private LinkedList<String> suggestionList;
    public boolean isDraggable = true;
    public void init(Stage app,
                     Scene dashboard,
                     Scene responses,
                     Scene suggestions,
                     dbController dbCont,
                     rpController rpCont,
                     ScheduledExecutorService scheduler,
                     LinkedList<String> suggestionList) {
        this.app = app;
        this.suggestionList = suggestionList;
        // Initialize Title Bar
        titleBarSC = new TitlebarSubController(this.app, titleBar, minimize, maximize, exit, dbCont, rpCont, this, scheduler);
        // Initialize Side Menu
        sideMenuSC = new SideMenuSubController(this.app, dashboard, responses, suggestions, btn_db, btn_rp, btn_sg);
        // Initialize Custom Resize
        resizeSC = new ResizeSubController(this.app,
                topResizeHandle,
                topLeftResizeHandle,
                topRightResizeHandle,
                rightResizeHandle,
                bottomResizeHandle,
                bottomLeftResizeHandle,
                bottomRightResizeHandle,
                leftResizeHandle);
    }

    public void handleControls() {
        // Sub Controller for Title Bar
        titleBarSC.init();
        // Sub Controller for Side Menu
        sideMenuSC.init();
        // Sub Controller for custom resize
        resizeSC.init();
        // Update UI
        updateUI(this.suggestionList);
    }

    public void updateUI(LinkedList<String> suggestionList) {
        flowPaneContainer.getChildren().clear();
        if(!suggestionList.isEmpty()) {
            for(int i = 0; i < suggestionList.size(); i++) {
                // Create Nodes
                BorderPane suggestionCard = new BorderPane();
                ScrollPane scrollableCont = new ScrollPane();
                AnchorPane suggestionCardTextContainer = new AnchorPane();
                Label suggestionCardLabel = new Label();
                Label suggestionCardText = new Label();
                // Apply stylesheets and layout
                suggestionCard.getStyleClass().add("suggestion-card");
                scrollableCont.getStyleClass().add("scrollable-cont");
                suggestionCardTextContainer.getStyleClass().add("suggestion-card-text-container");
                suggestionCardLabel.getStyleClass().add("suggestion-card-label");
                suggestionCardText.getStyleClass().add("suggestion-card-text");
                suggestionCard.setAlignment(suggestionCardLabel, Pos.TOP_LEFT);
                scrollableCont.setFitToWidth(true);
                scrollableCont.setFitToHeight(true);
                suggestionCardText.setWrapText(true);
                // Set Content
                switch(i) {
                    case 0 -> suggestionCardLabel.setText("Overall Experience");
                    case 1 -> suggestionCardLabel.setText("Ambiance Rating");
                    case 2 -> suggestionCardLabel.setText("Staff Interaction");
                    case 3 -> suggestionCardLabel.setText("Food Quality");
                    case 4 -> suggestionCardLabel.setText("Menu Variety");
                    case 5 -> suggestionCardLabel.setText("Waiting Time");
                    case 6 -> suggestionCardLabel.setText("Cleanliness Rating");
                    case 7 -> suggestionCardLabel.setText("Value For Money");
                    case 8 -> suggestionCardLabel.setText("Recommendation Likelihood");
                }
                suggestionCardText.setText(suggestionList.get(i));
                // Insert Nodes
                suggestionCard.setTop(suggestionCardLabel);
                suggestionCard.setCenter(scrollableCont);
                scrollableCont.setContent(suggestionCardTextContainer);
                suggestionCardTextContainer.getChildren().add(suggestionCardText);
                flowPaneContainer.getChildren().add(suggestionCard);
            }
        }
    }
}
