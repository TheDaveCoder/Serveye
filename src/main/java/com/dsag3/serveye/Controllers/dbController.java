package com.dsag3.serveye.Controllers;

import com.dsag3.serveye.Controllers.SubControllers.ResizeSubController;
import com.dsag3.serveye.Controllers.SubControllers.SideMenuSubController;
import com.dsag3.serveye.Controllers.SubControllers.TitlebarSubController;
import com.dsag3.serveye.Models.GeneralInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledExecutorService;

public class dbController {
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
    public BarChart<String, Number> overallExperienceChart;
    @FXML
    public Label overallRatingLabel;
    @FXML
    public Label overallResponsesLabel;
    @FXML
    public AreaChart ambianceRatingChart;
    @FXML
    public BarChart staffInteractionChart;
    @FXML
    public BarChart foodQualityChart;
    @FXML
    public ScatterChart menuVarietyChart;
    @FXML
    public BarChart waitingTimeChart;
    @FXML
    public AreaChart cleanlinessRatingChart;
    @FXML
    public ScatterChart valueForMoneyChart;
    @FXML
    public AreaChart recommendationLikelihoodChart;
    public BorderPane button3;
    public BorderPane button2;
    public BorderPane button1;
    public BorderPane button4;
    public BorderPane button5;
    public BorderPane button6;
    public BorderPane button7;
    public BorderPane button8;
    public BorderPane button9;
    public StackPane stackPane;
    public Button helpButton;
    // Sub Controllers
    private TitlebarSubController titleBarSC;
    private SideMenuSubController sideMenuSC;
    private ResizeSubController resizeSC;
    // Local Fields
    private Stage popup;
    private Stage app;
    private boolean isElec = true;
    public boolean isDraggable = true;
    private GeneralInfo generalInformation;
    private final String passCode = "875";
    private String entry = "";

    public void init(Stage app,
                     Scene dashboard,
                     Scene responses,
                     Scene suggestions,
                     rpController rpCont,
                     sgController sgCont,
                     ScheduledExecutorService scheduler,
                     GeneralInfo generalInfo,
                     Stage popup) {
        this.app = app;
        this.popup = popup;
        this.generalInformation = generalInfo;
        // Initialize Title Bar
        titleBarSC = new TitlebarSubController(this.app, titleBar, minimize, maximize, exit, this, rpCont, sgCont, scheduler);
        // Initialize Side Menu
        sideMenuSC = new SideMenuSubController(this.app, dashboard, responses, suggestions, btn_db, btn_rp, btn_sg, helpButton, stackPane);
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
        updateUI(this.generalInformation);
        // Handle easter egg
        easterEgg();
    }

    public void updateUI(GeneralInfo genInf) {
        // Update Header Values
        if(!genInf.responseList.isEmpty()) {
            overallResponsesLabel.setText(String.valueOf(genInf.totalResponses));
            overallRatingLabel.setText(genInf.overallRatingString);
        } else {
            overallResponsesLabel.setText("0");
            overallRatingLabel.setText("0.0%");
        }
        // Update Overall Experience
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) overallExperienceChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) overallExperienceChart.getYAxis();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.overallExperienceCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.overallExperienceCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.overallExperienceCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.overallExperienceCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.overallExperienceCh.five));

            overallExperienceChart.getData().clear();
            overallExperienceChart.getData().add(series);
        } else {
            overallExperienceChart.getData().clear();
        }
        // Update Ambiance Rating
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) ambianceRatingChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) ambianceRatingChart.getYAxis();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.ambianceRatingCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.ambianceRatingCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.ambianceRatingCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.ambianceRatingCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.ambianceRatingCh.five));

            ambianceRatingChart.getData().clear();
            ambianceRatingChart.getData().add(series);
        } else {
            ambianceRatingChart.getData().clear();
        }
        // Update Staff Interaction
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) staffInteractionChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) staffInteractionChart.getYAxis();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.staffInteractionCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.staffInteractionCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.staffInteractionCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.staffInteractionCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.staffInteractionCh.five));

            staffInteractionChart.getData().clear();
            staffInteractionChart.getData().add(series);
        } else {
            staffInteractionChart.getData().clear();
        }
        // Update Food Quality
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) foodQualityChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) foodQualityChart.getYAxis();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.foodQualityCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.foodQualityCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.foodQualityCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.foodQualityCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.foodQualityCh.five));

            foodQualityChart.getData().clear();
            foodQualityChart.getData().add(series);
        } else {
            foodQualityChart.getData().clear();
        }
        // Menu Variety
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) menuVarietyChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) menuVarietyChart.getYAxis();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.menuVarietyCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.menuVarietyCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.menuVarietyCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.menuVarietyCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.menuVarietyCh.five));

            menuVarietyChart.getData().clear();
            menuVarietyChart.getData().add(series);
        } else {
            menuVarietyChart.getData().clear();
        }
        // Waiting Time
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) waitingTimeChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) waitingTimeChart.getYAxis();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.waitingTimeCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.waitingTimeCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.waitingTimeCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.waitingTimeCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.waitingTimeCh.five));

            waitingTimeChart.getData().clear();
            waitingTimeChart.getData().add(series);
        } else {
            waitingTimeChart.getData().clear();
        }

        // Cleanliness
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) cleanlinessRatingChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) cleanlinessRatingChart.getYAxis();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.cleanlinessRatingCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.cleanlinessRatingCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.cleanlinessRatingCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.cleanlinessRatingCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.cleanlinessRatingCh.five));

            cleanlinessRatingChart.getData().clear();
            cleanlinessRatingChart.getData().add(series);
        } else {
            cleanlinessRatingChart.getData().clear();
        }
        // Value For Money
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) valueForMoneyChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) valueForMoneyChart.getYAxis();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.valueForMoneyCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.valueForMoneyCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.valueForMoneyCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.valueForMoneyCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.valueForMoneyCh.five));

            valueForMoneyChart.getData().clear();
            valueForMoneyChart.getData().add(series);
        } else {
            valueForMoneyChart.getData().clear();
        }
        // Recommendation Likeliness
        if(!genInf.responseList.isEmpty()) {
            CategoryAxis xAxis = (CategoryAxis) recommendationLikelihoodChart.getXAxis();
            NumberAxis yAxis = (NumberAxis) recommendationLikelihoodChart.getYAxis();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("1", genInf.recommendationLikelihoodCh.one));
            series.getData().add(new XYChart.Data<>("2", genInf.recommendationLikelihoodCh.two));
            series.getData().add(new XYChart.Data<>("3", genInf.recommendationLikelihoodCh.three));
            series.getData().add(new XYChart.Data<>("4", genInf.recommendationLikelihoodCh.four));
            series.getData().add(new XYChart.Data<>("5", genInf.recommendationLikelihoodCh.five));

            recommendationLikelihoodChart.getData().clear();
            recommendationLikelihoodChart.getData().add(series);
        } else {
            recommendationLikelihoodChart.getData().clear();
        }
    }
    public void easterEgg() {
        button1.setOnMouseClicked(event -> {
            entry += "1";
            checkCode();
        });
        button2.setOnMouseClicked(event -> {
            entry += "2";
            checkCode();
        });
        button3.setOnMouseClicked(event -> {
            entry += "3";
            checkCode();
        });
        button4.setOnMouseClicked(event -> {
            entry += "4";
            checkCode();
        });
        button5.setOnMouseClicked(event -> {
            entry += "5";
            checkCode();
        });
        button6.setOnMouseClicked(event -> {
            entry += "6";
            checkCode();
        });
        button7.setOnMouseClicked(event -> {
            entry += "7";
            checkCode();
        });
        button8.setOnMouseClicked(event -> {
            entry += "8";
            checkCode();
        });
        button9.setOnMouseClicked(event -> {
            entry = "";
        });
    }
    
    public void checkCode() {
        if(passCode.equals(entry)) {
            // set popup
            popup.setFullScreen(true);
            popup.setFullScreenExitHint("");
            popup.show();
            // play wav sound
            String filePath = "easter.wav";
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                System.out.println("Play Audio");
                clip.start(); // play audio
                System.out.println("After Audio");
                while (!clip.isRunning()) {
                    Thread.sleep(10);
                }
                // Close the resources
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
            // reset entry code
            entry = "";
        }
    }
}
