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

    //String[] goalNames = {0"Calories,1Total Fat (g),2Saturated Fat (g),3Cholesterol (g),4Sodium (mg),5Carbohydrates (g),
    // 6Sugar (g),7Dietary Fiber (g),8Protein (g),9Calcium (mg),10Potassium (mg),11Iron (mg),12Zinc (mg),13Vitamin A (IU),
    // 14Vitamin B6 (mg),15Vitamin B12 (mcg),16Vitamin C (mg),17Vitamin D (IU),18Vitamin E (mg)


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

    private void calculateGoals() {
        // Calories
        double tempCalories = 0;
        tempCalories = (double) Math.round(BMR * exerciseFactor);
        goals.put(model.goalNames[0], tempCalories);

        // Total Fat
        double tempTotalFat = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.30/4);
        goals.put(model.goalNames[1], tempTotalFat);

        // Total Fat
        double tempTotalFat = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.30/4);
        goals.put(model.goalNames[1], tempTotalFat);
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
