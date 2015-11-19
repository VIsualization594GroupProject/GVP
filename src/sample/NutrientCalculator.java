package sample;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Isaac on 11/17/2015.
 */
public class NutrientCalculator {
    int weight, height, age;
    boolean male;
    double BMR;
    Hashtable<String, Double> goals = new Hashtable<String, Double>();

    public enum ExerciseLevel{Sedentary, Low, Moderate, Active, Strenous}
    public static List<ExerciseLevel> allLevels = new ArrayList<ExerciseLevel>(5);
    ExerciseLevel exerciseLevel = ExerciseLevel.Moderate;
    double exerciseFactor;

    Model model;




    NutrientCalculator(Model m){
        model = m;
        allLevels.add(ExerciseLevel.Sedentary);
        allLevels.add(ExerciseLevel.Low);
        allLevels.add(ExerciseLevel.Moderate);
        allLevels.add(ExerciseLevel.Active);
        allLevels.add(ExerciseLevel.Strenous);

    }

    public void GenerateNewGoalsUsingAgeEtc()
    {
        calculateExerciseFactor();
        calculateBMR();
        calculateGoals();
    }

    void calculateExerciseFactor(){
        switch(exerciseLevel){
            case Sedentary: exerciseFactor = 1.2;
                break;
            case Low: exerciseFactor = 1.375;
                break;
            case Moderate: exerciseFactor = 1.55;
                break;
            case Active: exerciseFactor = 1.725;
                break;
            case Strenous: exerciseFactor = 1.9;
                break;
            //TODO: Finish
        }

    }

    private void calculateBMR() {
        if(male){
            BMR = 88.362 + (13.397*weight) + (4.799 *height) - (5.677 *age); // Male BMR
        }
        else{
            BMR = 447.593 + (9.247*weight) + (3.098 *height) - (4.330 *age); // Female BMR
        }
    }

    //String[] goalNames = {0"Calories,1Total Fat (g),2Saturated Fat (g),3Cholesterol (g),4Sodium (mg),5Carbohydrates (g),
    // 6Sugar (g),7Dietary Fiber (g),8Protein (g),9Calcium (mg),10Potassium (mg),11Iron (mg),12Zinc (mg),13Vitamin A (IU),
    // 14Vitamin B6 (mg),15Vitamin B12 (mcg),16Vitamin C (mg),17Vitamin D (IU),18Vitamin E (mg)

    private void calculateGoals() {
        // 0 - Calories
        double tempCalories = 0;
        tempCalories = (double) Math.round(BMR * exerciseFactor);
        goals.put(model.goalNames[0], tempCalories);

        // 1 - Total Fat
        double tempTotalFat = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.30/9);
        goals.put(model.goalNames[1], tempTotalFat);

        // 2 - Saturated Fat
        double tempSaturatedFat = 0;
        tempSaturatedFat = (double) Math.round(goals.get(model.goalNames[0])*0.07/9);
        if (tempSaturatedFat > 26){
            tempSaturatedFat = 26; // max recommended value for adults
        }
        goals.put(model.goalNames[2], tempSaturatedFat);

        // 3 - Cholesterol
        double tempCholesterol = 300; // max recommended value for everyone? except heart patients =200
        goals.put(model.goalNames[3], tempCholesterol);

        // 4 - Sodium
        double tempSodium = 0;
        if (age <= 3){
            tempSodium = 1000;
        } else if
                (age > 3 and  age <= 9 ){
            tempSodium = 12000;
        } else if
                (age > 9 and  age <= 50 ){
            tempSodium = 1500;
        } else if
                (age > 50 and  age <= 70 ){
            tempSodium = 1300;
        } else if
                (age > 70 ){
            tempSodium = 1200;
        }
        goals.put(model.goalNames[4], tempSodium);

        // 5 - Total Carbohydrates
        double tempCarbohydrates = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.30/9);
        goals.put(model.goalNames[5], tempTotalFat);




    }






    public void setGoal(String key, double newGoal){
        goals.put(key, newGoal);
    }

    public double getGoal(String key){
        return goals.get(key);
    }



    public void setCalories(double newCalories){
        goals.put(model.goalNames[0], newCalories);
    }



}
