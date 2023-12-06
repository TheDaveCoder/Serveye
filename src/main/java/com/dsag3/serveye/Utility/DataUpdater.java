package com.dsag3.serveye.Utility;

import com.dsag3.serveye.Controllers.dbController;
import com.dsag3.serveye.Controllers.rpController;
import com.dsag3.serveye.Controllers.sgController;
import com.dsag3.serveye.Models.GeneralInfo;
import com.dsag3.serveye.Models.ResponseModel;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

public class DataUpdater {
    private GeneralInfo genInf;
    private dbController dbCont;
    private rpController rpCont;
    private sgController sgCont;
    private static final int UPDATE_INTERVAL_SECONDS = 10;
    boolean newUpdate;

    private ScheduledExecutorService scheduler;

    public DataUpdater(GeneralInfo genInf, dbController dbCont, rpController rpCont, sgController sgCont, ScheduledExecutorService scheduler) {
        this.genInf = genInf;
        this.dbCont = dbCont;
        this.rpCont = rpCont;
        this.sgCont = sgCont;
        this.scheduler = scheduler;
    }

    public void startUpdating() {
        try {
            scheduler.scheduleAtFixedRate(this::updateData, 0, UPDATE_INTERVAL_SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData() {
        try {
            newUpdate = false;
            // Check the database and update the LinkedList
            LinkedList<ResponseModel> newDataList = DataHandler.fetchDataFromDatabase();

            // Check for new entries based on the primary key
            for (ResponseModel newEntry : newDataList) {
                if (genInf.responseList.stream().noneMatch(existingEntry -> existingEntry.responseID == newEntry.responseID)) {
                    // Flip Update Tracker
                    newUpdate = true;
                }
            }
            // Case 2: Database becomes empty
            if (newDataList.isEmpty() && !genInf.responseList.isEmpty()) {
                // Database is empty, clear the LinkedList
                genInf.responseList.clear();
                // Flip Update Tracker
                newUpdate = true;
            }
            // Check if an update occurred
            if(newUpdate) {
                // If an update occured and its not just the deletion of entries
                if(!newDataList.isEmpty()) {
                    genInf = new GeneralInfo(newDataList);
                }
                Platform.runLater(() -> {
                    // Call the updateUI method on your controller with the updated dataList
                    dbCont.updateUI(genInf);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
