package com.dsag3.serveye.Controllers;

import com.dsag3.serveye.Controllers.SubControllers.ResizeSubController;
import com.dsag3.serveye.Controllers.SubControllers.SideMenuSubController;
import com.dsag3.serveye.Controllers.SubControllers.TitlebarSubController;
import com.dsag3.serveye.Models.ResponseModel;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class rpController {
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
    public VBox feedbackCardContainer;
    @FXML
    public HBox dateAndTextContainer;
    @FXML
    public Label feedbackWindowTitle;
    @FXML
    public VBox dateContainer;
    @FXML
    public Label dateContainerData;
    @FXML
    public Label dateContainerLabel;
    @FXML
    public FlowPane responseInfoContainer;
    public Button helpButton;
    // Sub Controllers
    private TitlebarSubController titleBarSC;
    private SideMenuSubController sideMenuSC;
    private ResizeSubController resizeSC;
    // Local Fields
    private Stage app;
    private LinkedList<ResponseModel> responsesList;
    public boolean isDraggable = true;
    public void init(Stage app,
                     Scene dashboard,
                     Scene responses,
                     Scene suggestions,
                     dbController dbCont,
                     sgController sgCont,
                     ScheduledExecutorService scheduler,
                     LinkedList<ResponseModel> responsesList) {
        this.app = app;
        this.responsesList = responsesList;
        // Initialize Title Bar
        titleBarSC = new TitlebarSubController(this.app, titleBar, minimize, maximize, exit, dbCont, this, sgCont, scheduler);
        // Initialize Side Menu
        sideMenuSC = new SideMenuSubController(this.app, dashboard, responses, suggestions, btn_db, btn_rp, btn_sg, helpButton);
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
        // Main Controller
        // Update the UI
        updateUI(this.responsesList);
    }

    public void updateUI(LinkedList<ResponseModel> responseDataList) {
        System.out.println("updating!");
        // Reset nodes before proceeding
        // Reset Feedback Cards
        feedbackCardContainer.getChildren().clear();
        // Rest Date Card
        feedbackWindowTitle.setText("Feedback #");
        dateContainer.setStyle("-fx-background-color: none;"); // set bg color
        dateContainerLabel.setText(""); // set label
        dateContainerData.setText(""); // set date and time
        // Reset Additional Text Card if it exists
        if(dateAndTextContainer.getChildren().size() > 1) {
            dateAndTextContainer.getChildren().remove(1);
        }
        // Reset info container
        responseInfoContainer.getChildren().clear();
        if(!responseDataList.isEmpty()) {
            // Create the appropriate amount of feedback cards
            LinkedList<BorderPane> feedbackCardList = new LinkedList<>();
            LinkedList<Label> feedbackCardLabelList = new LinkedList<>();
            LinkedList<Label> feedbackCardSublabelList = new LinkedList<>();
            for(int i = 0; i < responseDataList.size(); i++) {
                // Create the border pane instance
                feedbackCardList.add(new BorderPane());
                // Add stylesheet class
                feedbackCardList.get(i).getStyleClass().add("feedback-card");
                // Create Labels
                feedbackCardLabelList.add(new Label()); // Response ID
                feedbackCardLabelList.get(i).setText(String.valueOf(responseDataList.get(i).responseID));
                feedbackCardSublabelList.add(new Label()); // Time Stamp
                feedbackCardSublabelList.get(i).setText(String.valueOf(responseDataList.get(i).timeStamp));
                // Add styling to labels
                feedbackCardLabelList.get(i).getStyleClass().add("feedback-card-label");
                feedbackCardSublabelList.get(i).getStyleClass().add("feedback-card-sublabel");
                // Insert Labels into border pane instance
                feedbackCardList.get(i).setLeft(feedbackCardLabelList.get(i)); // Label
                feedbackCardList.get(i).setCenter(feedbackCardSublabelList.get(i)); // Sub Label
                // Insert border pane instance to feedback card container
                feedbackCardContainer.getChildren().add(feedbackCardList.get(i));
            }
            // Add Event Handlers for each feedback card
            for(int i = 0; i < responseDataList.size(); i++) {
                final int index = i;
                feedbackCardList.get(index).setOnMouseClicked(mouseEvent -> {
                    if(dateAndTextContainer.getChildren().size() > 1) {
                        dateAndTextContainer.getChildren().remove(1);
                    }
                    responseInfoContainer.getChildren().clear();
                    String[] responseInfoCardNames = {"Overall\nExperience",
                            "Ambiance\nRating",
                            "Staff\nInteraction",
                            "Food\nQuality",
                            "Menu\nVariety",
                            "Waiting\nTime",
                            "Cleanliness\nRating",
                            "Value\nFor Money",
                            "Recommendation\nLikelihood"

                    };
                    // Update feedback window title
                    feedbackWindowTitle.setText("Feedback #" + responseDataList.get(index).responseID);
                    // Update Response Window


                    // Date Card
                    dateContainer.setStyle("-fx-background-color: #4b4b4b;"); // set bg color
                    dateContainerLabel.setText("Date and Time"); // set label
                    dateContainerData.setText(responseDataList.get(index).timeStamp.substring(4, 24)); // set date and time

                    // Special Case
                    if(!responseDataList.get(index).additionalComments.isEmpty()) {
                        // Create Nodes
                        BorderPane currAddCommentContainer = new BorderPane();
                        ScrollPane currScrollPane = new ScrollPane();
                        FlowPane currAddCommentTextContainer = new FlowPane();
                        Label currAddCommentLabel = new Label();
                        Label currAddCommentText = new Label();
                        // Set Labels
                        currAddCommentLabel.setText("Additional Comments");
                        currAddCommentText.setText(responseDataList.get(index).additionalComments);
                        // Set styling and layout
                        currAddCommentContainer.getStyleClass().add("addCommentContainer");
                        currAddCommentLabel.getStyleClass().add("addCommentLabel");
                        currScrollPane.getStyleClass().add("scrollableCont");
                        currScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        currScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                        currAddCommentTextContainer.getStyleClass().add("addCommentTextContainer");
                        currAddCommentText.getStyleClass().add("addCommentText");
                        currAddCommentContainer.setStyle("-fx-background-color: #4b4b4b;");
                        dateAndTextContainer.setHgrow(currAddCommentContainer, javafx.scene.layout.Priority.ALWAYS);
                        currAddCommentContainer.setAlignment(currAddCommentLabel, Pos.TOP_LEFT);
                        currAddCommentContainer.setMargin(currScrollPane, new javafx.geometry.Insets(15, 15, 15, 15));
                        currScrollPane.setFitToHeight(true);
                        currScrollPane.setFitToWidth(true);
                        currAddCommentText.setWrapText(true);
                        // Insert Nodes
                        dateAndTextContainer.getChildren().add(currAddCommentContainer);
                        currAddCommentContainer.setTop(currAddCommentLabel);
                        currAddCommentContainer.setCenter(currScrollPane);
                        currScrollPane.setContent(currAddCommentTextContainer);
                        currAddCommentTextContainer.getChildren().add(currAddCommentText);
                    }

                    // Additional Comments Card
                    // Response Info Cards
                    for(int j = 0; j < 9; j++) {
                        final int subIndex = j;
                        // Create necessary nodes
                        VBox currCard = new VBox();
                        Label currCardLabel = new Label();
                        Label currCardData = new Label();
                        // Add Styling
                        currCard.getStyleClass().add("responseInfoCard"); // card
                        currCardData.getStyleClass().add("responseInfoCardData"); // data
                        currCardLabel.getStyleClass().add("responseInfoCardLabel"); // label
                        // Set appropriate text for labels
                        currCardLabel.setText(responseInfoCardNames[subIndex]); // for the category name
                        switch(subIndex) { // for the data
                            case 0:
                                currCardData.setText(String.valueOf(responseDataList.get(index).overallExperience));
                                break;
                            case 1:
                                currCardData.setText(String.valueOf(responseDataList.get(index).ambianceRating));
                                break;
                            case 2:
                                currCardData.setText(String.valueOf(responseDataList.get(index).staffInteraction));
                                break;
                            case 3:
                                currCardData.setText(String.valueOf(responseDataList.get(index).foodQuality));
                                break;
                            case 4:
                                currCardData.setText(String.valueOf(responseDataList.get(index).menuVariety));
                                break;
                            case 5:
                                currCardData.setText(String.valueOf(responseDataList.get(index).waitingTime));
                                break;
                            case 6:
                                currCardData.setText(String.valueOf(responseDataList.get(index).cleanlinessRating));
                                break;
                            case 7:
                                currCardData.setText(String.valueOf(responseDataList.get(index).valueForMoney));
                                break;
                            case 8:
                                currCardData.setText(String.valueOf(responseDataList.get(index).recommendationLikelihood));
                                break;
                        }
                        // Add nodes
                        currCard.getChildren().addAll(currCardData, currCardLabel); // add labels
                        responseInfoContainer.getChildren().add(currCard); // add curr card to info container
                    }
                });
            }
        }
    }
}
