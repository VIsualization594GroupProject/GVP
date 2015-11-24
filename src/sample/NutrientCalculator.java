package sample;

import java.util.ArrayList;
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

    public enum ExerciseLevel{Sedentary, Low, Moderate, Active, Strenuous}
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
        allLevels.add(ExerciseLevel.Strenuous);

    }

    public void GenerateNewGoalsUsingAgeEtc(int age, int weight, int height, String gender, String activityLevel)
    {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.male = gender.toLowerCase().startsWith("male");
        this.exerciseLevel = exerciseLookup(activityLevel);
        calculateExerciseFactor();
        calculateBMR();
        calculateGoals();
    }

    private ExerciseLevel exerciseLookup(String activityLevel) {
        for (ExerciseLevel x :
                allLevels) {
            if (activityLevel.compareToIgnoreCase(x.toString()) == 0) return x;
        }
        return ExerciseLevel.Moderate;
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
            case Strenuous: exerciseFactor = 1.9;
                break;
            //TODO: Finish
        }

    }

    private void calculateBMR() {
        //Mifflin-St. Jeor Equations
        if(male){
            BMR = (4.536*weight) + (15.875*height) - (5*age) + 5; // Male BMR
        }
        else{
            BMR = (4.536*weight) + (15.875*height) - (5*age) - 161; // Female BMR
        }
    }

    private void calculateGoals() {
        // 0 - Calories
        double tempCalories = 0;
        tempCalories = (double) Math.round(BMR * exerciseFactor);
        goals.put("Calories", tempCalories);

        // 1 - Saturated Fat (g)
        double tempTotalFat = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.30/9);
        goals.put("Total Fat (g)", tempTotalFat);

        // 2 - Saturated Fat (g)
        double tempSaturatedFat = 0;
        tempSaturatedFat = (double) Math.round(goals.get(model.goalNames[0])*0.07/9);
        if (tempSaturatedFat > 26){
            tempSaturatedFat = 26; // max recommended value for adults
        }
        goals.put("Saturated Fat (g)", tempSaturatedFat);

        // 3 - Cholesterol (mg)
        double tempCholesterol = 300; // max recommended value for everyone? except heart patients =200
        goals.put("Cholesterol (mg)", tempCholesterol);

        // 4 - Sodium (mg)
        double tempSodium = 0;
        if (age <= 3){
            tempSodium = 1000;
        } else if (age > 3 &&  age <= 9 ){
            tempSodium = 12000;
        } else if (age > 9 &&  age <= 50 ){
            tempSodium = 1500;
        } else if (age > 50 &&  age <= 70 ){
            tempSodium = 1300;
        } else if (age > 70 ){
            tempSodium = 1200;
        }
        goals.put("Sodium (mg)", tempSodium);

        // 5 - Carbohydrates (g)
        double tempCarbohydrates = 0;
        tempTotalFat = (double) Math.round(goals.get(model.goalNames[0])*0.50/4);
        goals.put("Carbohydrates (g)", tempTotalFat);

        // 6 - Sugar (g)
        double tempSugar = 0;
        tempSugar = (double) Math.round(goals.get(model.goalNames[0])*0.10/4);
        goals.put("Sugar (g)", tempSugar);

        // 7 - Dietary Fiber (g)
        double tempFiber = 0;
        if (age <= 50 && male){
            tempFiber = 38;
        } else if (age <= 50 && !male){
            tempFiber = 25;
        } else if (age > 50 && male){
            tempFiber = 30;
        } else if (age > 50 && !male){
            tempFiber = 21;
        }
        goals.put("Dietary Fiber (g)", tempFiber);

        // 8 - Protein (g)
        double tempProtein = 0;
        tempProtein = (double) Math.round(goals.get(model.goalNames[0])*0.20/4);
        goals.put("Protein (g)", tempProtein);

        // 9 - Calcium (mg)
        double tempCalcium = 0;
        if (age > 0 &&  age <= 3){
            tempCalcium = 700;
        } else if (age > 3 &&  age <= 8){
            tempCalcium = 1000;
        } else if (age > 8 &&  age <= 18){
            tempCalcium = 1300;
        } else if (age > 18 &&  age <= 50){
            tempCalcium = 1000;
        } else if (age > 50) {
            tempCalcium = 1200;
        }
        goals.put("Calcium (mg)", tempCalcium);

        // 10 - Potassium (mg)
        double tempPotassium = 0;
        if (age > 0 &&  age <= 3){
            tempPotassium = 3;
        } else if (age > 3 &&  age <= 8){
            tempPotassium = 3.8;
        } else if (age > 8 &&  age <= 13){
            tempPotassium = 4.5;
        } else if (age > 13){
            tempPotassium = 4.7;
        }
        goals.put("Potassium (mg)", tempPotassium);

        // 11 - Iron (mg)
        double tempIron = 0;
        if (age > 0 &&  age <= 3){
            tempIron = 7;
        } else if (age > 3 &&  age <= 8){
            tempIron = 10;
        } else if (age > 8 &&  age <= 13){
            tempIron = 8;
        } else if (age > 13 &&  age <= 18 && male){
            tempIron = 11;
        } else if (age > 13 &&  age <= 18 && !male){
            tempIron = 15;
        } else if (age > 18 && male){
            tempIron = 8;
        } else if (age > 18 &&  age <= 50 && !male){
            tempIron = 18;
        } else if (age > 50 && !male){
            tempIron = 8;
        }
        goals.put("Iron (mg)", tempIron);

        // 12 - Zinc (mg)
        double tempZinc = 0;
        if (age > 0 &&  age <= 3){
            tempZinc = 3;
        } else if (age > 3 &&  age <= 8){
            tempZinc = 5;
        } else if (age > 8 &&  age <= 13){
            tempZinc = 8;
        } else if (age > 13 && male){
            tempZinc = 11;
        } else if (age > 13 && age <= 18 && !male){
            tempZinc = 9;
        } else if (age > 18 && !male){
            tempZinc = 8;
        }
        goals.put("Zinc (mg)", tempZinc);

        // 13 - Vitamin A (IU)
        double tempVitaminA = 0;
        if (age > 0 &&  age <= 3){
            tempVitaminA = 1000;
        } else if (age > 3 &&  age <= 8){
            tempVitaminA = 1300;
        } else if (age > 8 &&  age <= 13){
            tempVitaminA = 2000;
        } else if (age > 13 && age <= 18){
            tempVitaminA = 1000;
        } else if (age > 18 && male){
            tempVitaminA = 3000;
        } else if (age > 18 && !male){
            tempVitaminA = 2300;
        }
        goals.put("Vitamin A (IU)", tempVitaminA);

        // 14 - Vitamin B6 (mg)
        double tempVitaminB6 = 0;
        if (age > 0 &&  age <= 3){
            tempVitaminB6 = 0.5;
        } else if (age > 3 &&  age <= 8){
            tempVitaminB6 = 0.6;
        } else if (age > 8 &&  age <= 13){
            tempVitaminB6 = 1.0;
        } else if (age > 13 && male){
            tempVitaminB6 = 1.3;
        } else if (age > 13 && age <= 18 && !male){
            tempVitaminB6 = 1.2;
        } else if (age > 18 && !male){
            tempVitaminB6 = 1.7;
        }
        goals.put("Vitamin B6 (mg)", tempVitaminB6);

        // 15 - Vitamin B12 (mcg)
        double tempVitaminB12 = 0;
        if (age > 0 &&  age <= 3){
            tempVitaminB12 = 0.9;
        } else if (age > 3 &&  age <= 8){
            tempVitaminB12 = 1.2;
        } else if (age > 8 &&  age <= 13){
            tempVitaminB12 = 1.8;
        } else if (age > 13){
            tempVitaminB12 = 2.4;
        }
        goals.put("Vitamin B12 (mcg)", tempVitaminB12);

        // 16 - Vitamin C (mg)
        double tempVitaminC = 0;
        if (age > 0 &&  age <= 3){
            tempVitaminC = 15;
        } else if (age > 3 &&  age <= 8){
            tempVitaminC = 25;
        } else if (age > 8 &&  age <= 13){
            tempVitaminC = 45;
        } else if (age > 13 &&  age <= 18 && male){
            tempVitaminC = 75;
        } else if (age > 13 &&  age <= 18 && male){
            tempVitaminC = 65;
        } else if (age > 18 && male){
            tempVitaminC = 90;
        } else if (age > 18  && male){
            tempVitaminC = 75;
        }
        goals.put("Vitamin C (mg)", tempVitaminC);

        // 17 - Vitamin D (IU)
        double tempVitaminD = 0;
        tempVitaminD = 600; // for everyone!
        goals.put("Vitamin D (IU)", tempVitaminD);

        // 18 - Vitamin E (mg)
        double tempVitaminE = 0;
        if (age > 0 &&  age <= 3){
            tempVitaminE = 13;
        } else if (age > 3 &&  age <= 8){
            tempVitaminE = 16;
        } else if (age > 8 &&  age <= 13){
            tempVitaminE = 24;
        } else if (age > 13){
            tempVitaminE = 33;
        }
        goals.put("Vitamin E (mg)", tempVitaminE);

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
