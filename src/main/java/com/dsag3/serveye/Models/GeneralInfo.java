package com.dsag3.serveye.Models;

import com.dsag3.serveye.Models.ResponseModel;
import com.dsag3.serveye.Utility.RatingChart;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class GeneralInfo {
    public int totalResponses = 0;
    public double overallRating = 0;
    public String overallRatingString;
    public RatingChart overallExperienceCh = new RatingChart();
    public RatingChart ambianceRatingCh = new RatingChart();
    public RatingChart staffInteractionCh = new RatingChart();
    public RatingChart foodQualityCh = new RatingChart();
    public RatingChart menuVarietyCh = new RatingChart();
    public RatingChart waitingTimeCh = new RatingChart();
    public RatingChart cleanlinessRatingCh = new RatingChart();
    public RatingChart valueForMoneyCh = new RatingChart();
    public RatingChart recommendationLikelihoodCh = new RatingChart();
    public LinkedList<ResponseModel> responseList;
    public LinkedList<Double> categoryAverage = new LinkedList<Double>();

    public GeneralInfo(LinkedList<ResponseModel> responseList) {
        this.responseList = responseList;
        // update size
        totalResponses = this.responseList.size();
        // get overall rating
        double total = 0;
        for (ResponseModel currResponse : responseList) {
            int subTotal = 0;
            subTotal += currResponse.overallExperience;
            subTotal += currResponse.ambianceRating;
            subTotal += currResponse.staffInteraction;
            subTotal += currResponse.foodQuality;
            subTotal += currResponse.menuVariety;
            subTotal += currResponse.waitingTime;
            subTotal += currResponse.cleanlinessRating;
            subTotal += currResponse.valueForMoney;
            subTotal += currResponse.recommendationLikelihood;
            subTotal /= 9;
            total += subTotal;
        }
        if(!responseList.isEmpty()) {
            total /= responseList.size();
        }
        total = total/5*100;
        overallRating = Math.round(total*100.0)/100.0;
        overallRatingString = overallRating + "%";
        // get plot values
        for (ResponseModel currResponse : responseList) {
            // for overall experience
            switch(currResponse.overallExperience) {
                case 1:
                    overallExperienceCh.one++;
                    break;
                case 2:
                    overallExperienceCh.two++;
                    break;
                case 3:
                    overallExperienceCh.three++;
                    break;
                case 4:
                    overallExperienceCh.four++;
                    break;
                case 5:
                    overallExperienceCh.five++;
                    break;
            }
            // for ambiance rating experience
            switch(currResponse.ambianceRating) {
                case 1:
                    ambianceRatingCh.one++;
                    break;
                case 2:
                    ambianceRatingCh.two++;
                    break;
                case 3:
                    ambianceRatingCh.three++;
                    break;
                case 4:
                    ambianceRatingCh.four++;
                    break;
                case 5:
                    ambianceRatingCh.five++;
                    break;
            }
            // for staff interaction experience
            switch(currResponse.staffInteraction) {
                case 1:
                    staffInteractionCh.one++;
                    break;
                case 2:
                    staffInteractionCh.two++;
                    break;
                case 3:
                    staffInteractionCh.three++;
                    break;
                case 4:
                    staffInteractionCh.four++;
                    break;
                case 5:
                    staffInteractionCh.five++;
                    break;
            }
            // for food quality experience
            switch(currResponse.foodQuality) {
                case 1:
                    foodQualityCh.one++;
                    break;
                case 2:
                    foodQualityCh.two++;
                    break;
                case 3:
                    foodQualityCh.three++;
                    break;
                case 4:
                    foodQualityCh.four++;
                    break;
                case 5:
                    foodQualityCh.five++;
                    break;
            }
            // for menu variety experience
            switch(currResponse.menuVariety) {
                case 1:
                    menuVarietyCh.one++;
                    break;
                case 2:
                    menuVarietyCh.two++;
                    break;
                case 3:
                    menuVarietyCh.three++;
                    break;
                case 4:
                    menuVarietyCh.four++;
                    break;
                case 5:
                    menuVarietyCh.five++;
                    break;
            }
            // for waiting time experience
            switch(currResponse.waitingTime) {
                case 1:
                    waitingTimeCh.one++;
                    break;
                case 2:
                    waitingTimeCh.two++;
                    break;
                case 3:
                    waitingTimeCh.three++;
                    break;
                case 4:
                    waitingTimeCh.four++;
                    break;
                case 5:
                    waitingTimeCh.five++;
                    break;
            }
            // for cleanliness rating experience
            switch(currResponse.cleanlinessRating) {
                case 1:
                    cleanlinessRatingCh.one++;
                    break;
                case 2:
                    cleanlinessRatingCh.two++;
                    break;
                case 3:
                    cleanlinessRatingCh.three++;
                    break;
                case 4:
                    cleanlinessRatingCh.four++;
                    break;
                case 5:
                    cleanlinessRatingCh.five++;
                    break;
            }
            // for value for money experience
            switch(currResponse.valueForMoney) {
                case 1:
                    valueForMoneyCh.one++;
                    break;
                case 2:
                    valueForMoneyCh.two++;
                    break;
                case 3:
                    valueForMoneyCh.three++;
                    break;
                case 4:
                    valueForMoneyCh.four++;
                    break;
                case 5:
                    valueForMoneyCh.five++;
                    break;
            }
            // for recommendation likelihood experience
            switch(currResponse.recommendationLikelihood) {
                case 1:
                    recommendationLikelihoodCh.one++;
                    break;
                case 2:
                    recommendationLikelihoodCh.two++;
                    break;
                case 3:
                    recommendationLikelihoodCh.three++;
                    break;
                case 4:
                    recommendationLikelihoodCh.four++;
                    break;
                case 5:
                    recommendationLikelihoodCh.five++;
                    break;
            }
        }
        // get average per category
        for (int i = 0; i < 9; i ++) {
            double subTotal = 0;
            for (int j = 0; j < responseList.size(); j ++) {
                switch(i) {
                    case 0:
                        subTotal += responseList.get(j).overallExperience;
                        break;
                    case 1:
                        subTotal += responseList.get(j).ambianceRating;
                        break;
                    case 2:
                        subTotal += responseList.get(j).staffInteraction;
                        break;
                    case 3:
                        subTotal += responseList.get(j).foodQuality;
                        break;
                    case 4:
                        subTotal += responseList.get(j).menuVariety;
                        break;
                    case 5:
                        subTotal += responseList.get(j).waitingTime;
                        break;
                    case 6:
                        subTotal += responseList.get(j).cleanlinessRating;
                        break;
                    case 7:
                        subTotal += responseList.get(j).valueForMoney;
                        break;
                    case 8:
                        subTotal += responseList.get(j).recommendationLikelihood;
                        break;
                }
            }
            subTotal /= responseList.size();
            categoryAverage.add(subTotal);
        }
    }
}