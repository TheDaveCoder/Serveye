package com.dsag3.serveye.Models;

public class ResponseModel {
    public int responseID,
            overallExperience,
            ambianceRating,
            staffInteraction,
            foodQuality,
            menuVariety, waitingTime,
            cleanlinessRating,
            valueForMoney,
            recommendationLikelihood;
    public String timeStamp, additionalComments;
    public ResponseModel(int responseID,
                         String timeStamp,
                         int overallRating,
                         int ambianceRating,
                         int staffInteraction,
                         int foodQuality,
                         int menuVariety,
                         int waitingTime,
                         int cleanlinessRating,
                         int valueForMoney,
                         int recommendationLikelihood,
                         String additionalComments) {
        this.responseID = responseID;
        this.timeStamp = timeStamp;
        this.overallExperience = overallRating;
        this.ambianceRating = ambianceRating;
        this.staffInteraction = staffInteraction;
        this.foodQuality = foodQuality;
        this.menuVariety = menuVariety;
        this.waitingTime = waitingTime;
        this.cleanlinessRating = cleanlinessRating;
        this.valueForMoney = valueForMoney;
        this.recommendationLikelihood = recommendationLikelihood;
        this.additionalComments = additionalComments;
    }
}
