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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DataUpdater {
    private GeneralInfo genInf;
    private LinkedList<String> suggestionList;
    private dbController dbCont;
    private rpController rpCont;
    private sgController sgCont;
    private static final int UPDATE_INTERVAL_SECONDS = 10;
    boolean newUpdate;

    private ScheduledExecutorService scheduler;

    public DataUpdater(GeneralInfo genInf, LinkedList<String> suggestionList, dbController dbCont, rpController rpCont, sgController sgCont, ScheduledExecutorService scheduler) {
        this.genInf = genInf;
        this.suggestionList = suggestionList;
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
            if (!(genInf.responseList.isEmpty()) && newDataList.isEmpty()) {
                // Database is empty, clear the LinkedLists
                genInf.responseList.clear();
                suggestionList.clear();
                // Flip Update Tracker
                newUpdate = true;
            }
            // Check if an update occurred
            if(newUpdate) {
                CompletableFuture<LinkedList<String>> resultFuture = asyncRequest(genInf);
                // If an update occured and it's not just the deletion of entries
                if(!newDataList.isEmpty()) {
                    genInf = new GeneralInfo(newDataList);
                    resultFuture.thenAccept(result -> {
                        Platform.runLater(() -> {
                            sgCont.updateUI(result);
                        });
                    });
                } else {
                    Platform.runLater(() -> {
                        sgCont.updateUI(suggestionList);
                    });
                }
                Platform.runLater(() -> {
                    // Call the updateUI method on your controller with the updated dataList
                    dbCont.updateUI(genInf); // Dashboard
                    rpCont.updateUI(genInf.responseList); // Response List
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CompletableFuture<LinkedList<String>> asyncRequest(GeneralInfo genInf) {
        return CompletableFuture.supplyAsync(() -> {
            return FetchSuggestions.getSuggestions(genInf);
        });
    }
}
