package sample;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Isaac on 11/17/2015.
 */
public class NutrientCalculator {
    int weight, height, age;
    boolean female;
    double BMR;
    Hashtable<String, Double> goals = new Hashtable<String, Double>();

    public enum ExerciseLevel{Sedentary, Low, Moderate, Active, Masochist}
    ExerciseLevel exerciseLevel;
    double exerciseFactor;

    Model model;

    NutrientCalculator(Model m){
        model = m;

    }


    void calculateExerciseFactor(){
        switch(exerciseLevel){
            case Sedentary: exerciseFactor = 1.2;
                break;
            case Low: exerciseFactor = 1.4;
                break;
            //TODO: Finish
        }

    }

    public void GenerateNewGoalsUsingAgeEtc()
    {
        calculateTotalCalories();
        calculateExerciseFactor();
        calculateBMR();
        calculateOtherGoals();
    }

    private void calculateOtherGoals() {
        
    }

    public void setGoal(String key, double newGoal){
        goals.put(key, newGoal);
    }
    public double getGoal(String key){
        return goals.get(key);
    }

    private void calculateTotalCalories() {
        double tempCalories = 0;
        goals.put(model.goalNames[0], tempCalories);
        double newCalories = goals.get(model.goalNames[0]);
    }

    public void setCalories(double newCalories){
        goals.put(model.goalNames[0], newCalories);
    }

    private void calculateBMR() {
        if(female){
            BMR = 1;//Numbers
        }
        else{
            BMR = 2;//Other numbers;
        }
    }

}
