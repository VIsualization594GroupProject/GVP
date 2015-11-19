package sample;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Isaac on 11/17/2015.
 */
public class NutrientCalculator {
    int weight, height, age;
    boolean male;
    double BMR;
    Hashtable<String, Double> goals = new Hashtable<String, Double>();

    public enum ExerciseLevel{Sedentary, Low, Moderate, Active, Strenous}
    ExerciseLevel exerciseLevel = ExerciseLevel.Moderate;
    double exerciseFactor;

    Model model;

    //String[] goalNames = {0"Calories",1"Protein",2"Fats",3"Carbohydrates",4"Fiber",5"Sugar",6"Calcium",7"Iron",8"Potassium",
    //        9"Sodium",10"Zinc",11"Vitamin C",12"Vitamin B6",13"Folic Acid",14"Vitamin B12",15"Vitamin A (IU)",
     //       16"Vitamin E",17"Vitamin D",18"Vit_K",19"Saturated Fats",20"Monounsaturated Fats",
     //       21"Polyunsaturated Fats",22"Cholesterol"};


    NutrientCalculator(Model m){
        model = m;

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
        tempCalories = (double)Math.round(BMR * exerciseFactor);
        goals.put(model.goalNames[0], tempCalories);

        //
        tempCalories = (double)Math.round(BMR * exerciseFactor);
        goals.put(model.goalNames[0], tempCalories);

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
