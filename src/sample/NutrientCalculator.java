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
        } else if (age > 3 and  age <= 9 ){
            tempSodium = 12000;
        } else if (age > 9 and  age <= 50 ){
            tempSodium = 1500;
        } else if (age > 50 and  age <= 70 ){
            tempSodium = 1300;
        } else if (age > 70 ){
            tempSodium = 1200;
        }
        goals.put(model.goalNames[4], tempSodium);

        // 5 - Total Carbohydrates
        double tempCarbohydrates = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.50/4);
        goals.put(model.goalNames[5], tempTotalFat);

        // 6 - Sugars
        double tempSugars = 0;
        tempSugars = (double) Math.round(goals.get(model.goalNames[0])*0.10/4);
        goals.put(model.goalNames[6], tempSugars);

        // 7 - Dietary Fiber
        double tempFiber = 0;
        if (age <= 50 and male){
            tempFiber = 38;
        } else if (age <= 50 and not male){
            tempFiber = 25;
        } else if (age > 50 and male){
            tempFiber = 30;
        } else if (age > 50 and not male){
            tempFiber = 21;
        }
        goals.put(model.goalNames[7], tempFiber);

        // 8 - Protein
        double tempProtein = 0;
        tempProtein = (double) Math.round(goals.get(model.goalNames[0])*0.20/4);
        goals.put(model.goalNames[8], tempProtein);

        // 9 - Calcium
        double tempCalcium = 0;
        if (age > 0 and  age <= 3){
            tempCalcium = 700;
        } else if (age > 3 and  age <= 8){
            tempCalcium = 1000;
        } else if (age > 8 and  age <= 18){
            tempCalcium = 1300;
        } else if (age > 18 and  age <= 50){
            tempCalcium = 1000;
        } else if (age > 50) {
            tempCalcium = 1200;
        }
        goals.put(model.goalNames[9], tempCalcium);

        // 10 - Potassium
        double tempPotassium = 0;
        if (age > 0 and  age <= 3){
            tempPotassium = 3;
        } else if (age > 3 and  age <= 8){
            tempPotassium = 3.8;
        } else if (age > 8 and  age <= 13){
            tempPotassium = 4.5;
        } else if (age > 13){
            tempPotassium = 4.7;
        }
        goals.put(model.goalNames[10], tempPotassium);

        // 11 - Iron
        double tempIron = 0;
        if (age > 0 and  age <= 3){
            tempIron = 7;
        } else if (age > 3 and  age <= 8){
            tempIron = 10;
        } else if (age > 8 and  age <= 13){
            tempIron = 8;
        } else if (age > 13 and  age <= 18 and male){
            tempIron = 11;
        } else if (age > 13 and  age <= 18 and not male){
            tempIron = 15;
        } else if (age > 18 and male){
            tempIron = 8;
        } else if (age > 18 and  age <= 50 and not male){
            tempIron = 18;
        } else if (age > 50 and not male){
            tempIron = 8;
        }
        goals.put(model.goalNames[11], tempIron);

        // 12 - Zinc
        double tempZinc = 0;
        if (age > 0 and  age <= 3){
            tempZinc = 3;
        } else if (age > 3 and  age <= 8){
            tempZinc = 5;
        } else if (age > 8 and  age <= 13){
            tempZinc = 8;
        } else if (age > 13 and male){
            tempZinc = 11;
        } else if (age > 13 and age <= 18 and not male){
            tempZinc = 9;
        } else if (age > 18 and not male){
            tempZinc = 8;
        }
        goals.put(model.goalNames[12], tempZinc);

        // 13 - Vitamin A
        double tempVitaminA = 0;
        if (age > 0 and  age <= 3){
            tempVitaminA = 1000;
        } else if (age > 3 and  age <= 8){
            tempVitaminA = 1300;
        } else if (age > 8 and  age <= 13){
            tempVitaminA = 2000;
        } else if (age > 13 and age <= 18){
            tempVitaminA = 1000;
        } else if (age > 18 and male){
            tempVitaminA = 3000;
        } else if (age > 18 and not male){
            tempVitaminA = 2300;
        }
        goals.put(model.goalNames[13], tempVitaminA);

        // 14 - Vitamin B6
        double tempVitaminB6 = 0;
        if (age > 0 and  age <= 3){
            tempVitaminB6 = 0.5;
        } else if (age > 3 and  age <= 8){
            tempVitaminB6 = 0.6;
        } else if (age > 8 and  age <= 13){
            tempVitaminB6 = 1.0;
        } else if (age > 13 and male){
            tempVitaminB6 = 1.3;
        } else if (age > 13 and age <= 18 and not male){
            tempVitaminB6 = 1.2;
        } else if (age > 18 and not male){
            tempVitaminB6 = 1.7;
        }
        goals.put(model.goalNames[14], tempVitaminB6);

        // 15 - Vitamin B12
        double tempVitaminB12 = 0;
        if (age > 0 and  age <= 3){
            tempVitaminB12 = 0.9;
        } else if (age > 3 and  age <= 8){
            tempVitaminB12 = 1.2;
        } else if (age > 8 and  age <= 13){
            tempVitaminB12 = 1.8;
        } else if (age > 13){
            tempVitaminB12 = 2.4;
        }
        goals.put(model.goalNames[15], tempVitaminB12);

        // 16 - Vitamin C
        double tempVitaminC = 0;
        if (age > 0 and  age <= 3){
            tempVitaminC = 15;
        } else if (age > 3 and  age <= 8){
            tempVitaminC = 25;
        } else if (age > 8 and  age <= 13){
            tempVitaminC = 45;
        } else if (age > 13 and  age <= 18 and male){
            tempVitaminC = 75;
        } else if (age > 13 and  age <= 18 and male){
            tempVitaminC = 65;
        } else if (age > 18 and male){
            tempVitaminC = 90;
        } else if (age > 18  and male){
            tempVitaminC = 75;
        }
        goals.put(model.goalNames[16], tempVitaminC);

        // 17 - Vitamin D
        double tempVitaminD = 0;
        tempVitaminD = 600; // for everyone!
        goals.put(model.goalNames[17], tempVitaminD);

        // 18 - Vitamin E
        double tempVitaminE = 0;
        if (age > 0 and  age <= 3){
            tempVitaminE = 13;
        } else if (age > 3 and  age <= 8){
            tempVitaminE = 16;
        } else if (age > 8 and  age <= 13){
            tempVitaminE = 24;
        } else if (age > 13){
            tempVitaminE = 33;
        }
        goals.put(model.goalNames[18], tempVitaminE);




    }



    //String[] goalNames = {0"Calories,1Total Fat (g),2Saturated Fat (g),3Cholesterol (g),4Sodium (mg),5Carbohydrates (g),
    // 6Sugar (g),7Dietary Fiber (g),8Protein (g),9Calcium (mg),10Potassium (mg),11Iron (mg),12Zinc (mg),13Vitamin A (IU),
    // 14Vitamin B6 (mg),15Vitamin B12 (mcg),16Vitamin C (mg),17Vitamin D (IU),18Vitamin E (mg)


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
