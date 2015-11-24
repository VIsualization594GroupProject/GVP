package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Isaac on 11/12/2015.
 * This is where all the logic for the app will reside.
 * The nutrition algorithm that Sofy wrote will find its home here.
 * Essentially, anything that depends on internal logic goes here.
 *
 */
public class Model extends Observable{


    NutrientCalculator nutrients = new NutrientCalculator(this);
    ArrayList<String> labels= new ArrayList<String>(),
            nutrientHeaders = new ArrayList<String>(),//These are the nutrient headers
            categories = new ArrayList<String>();
    Hashtable<String, ArrayList<String>> categoryToItemsList = new Hashtable<String, ArrayList<String>>();

    /*String[] goalNames = {"Calories","Total Fat (g)","Saturated Fat (g)","Cholesterol (mg)","Sodium (mg)",
            "Carbohydrates (g)", "Sugar (g)","Dietary Fiber (g)","Protein (g)", "Calcium (mg)","Potassium (mg)",
            "Iron (mg)","Zinc (mg)","Vitamin A (IU)","Vitamin B6 (mg)", "Vitamin B12 (mcg)","Vitamin C (mg)",
            "Vitamin D (IU)", "Vitamin E (mg)"};
    */

    ArrayList<Integer> nutrientsToTrack = new ArrayList<Integer>(5);//Correspond to the columns we'll be using to index into table
    ArrayList<ArrayList<String>> stringTable= new ArrayList<ArrayList<String>>();//holds the strings for categories and foods
    ArrayList<ArrayList<Double>> table = new ArrayList<ArrayList<Double>>();//Holds all the numerical nutritional data
    Hashtable<String, Integer> labelToIndex = new Hashtable<String, Integer>(),//quickly looks up the column from the string label
            nameToRowIndex = new Hashtable<String, Integer>();//quickly looks up the row from the string name of the item
    ArrayList<Double> totalNutrients = new ArrayList<Double>();//Holds total nutrients from added items
    ArrayList<String> Breakfast = new ArrayList<String>();
    ArrayList<String> Lunch = new ArrayList<String>();
    ArrayList<String> Dinner = new ArrayList<String>();

    private ArrayList<Observer> observerList = new ArrayList<Observer>();
        int SHORTDESC_INDEX = 1;

        int CATEGORY_INDEX = 2;
    Model(String file){
      try{
          load(file);
      }
      catch (IOException e){
          System.err.println("IO failed, "+ e.getMessage());
          //System.exit(-1);
      }
        for (int i = 0; i < 5; i++) {
            nutrientsToTrack.add(i);
        }
        for(int i = 0; i < table.get(0).size(); i++) { //Set totalNutrients size to table size
            totalNutrients.add(0.0);
        }
        for(String x : categories){
            ArrayList<String> temp = new ArrayList<String>();

            for (int i = 0; i < stringTable.size(); ++i) {
                if(stringTable.get(i).get(CATEGORY_INDEX).equals(x)){
                    temp.add(stringTable.get(i).get(SHORTDESC_INDEX));
                }

            }
            categoryToItemsList.put(x, temp);
        }


    }


    public Hashtable<String, ArrayList<String>> getCategoryToItemsList(){
        return categoryToItemsList;
    }
    public void updateNutrientCalcBasedonPersonalDetails(int age, int height, int weight, String gender, String activityLevel){
        nutrients.GenerateNewGoalsUsingAgeEtc(age, height, weight, gender, activityLevel);
    }


    Model(){
        this("USDAmodifiedFile.txt");
        //Do stuff
    }

    public void load(String file) throws IOException {


        Scanner line, in;
        in = new Scanner(new BufferedReader(new FileReader(file)));
        String firstLine = in.nextLine();
        int lineNumber=-1;
        int stringColumns = 4;//Used as a constant, to selectively ignore the first (three) columns of the csv file
        String[] headerLabels = firstLine.split("\t");
        for (int i = stringColumns; i < headerLabels.length; i++) {
            nutrientHeaders.add(headerLabels[i]);
        }

        labelToIndex = new Hashtable<String, Integer>(nutrientHeaders.size());
        for (int i = 0; i < nutrientHeaders.size(); ++i)
            labelToIndex.put(nutrientHeaders.get(i), i);
        while (in.hasNextLine()) {
            ArrayList<Double> tempData = new ArrayList<Double>(labels.size());
            line = new Scanner(in.nextLine());
            line.useDelimiter("\t");
            //Except for the first line, csv should have a couple of strings which go in stringTable, then the rest
            //go in table; if I missed other string descriptors, change i's bound for that case.
            ArrayList<String> tempString = new ArrayList<String>(stringColumns);
            for (int i = 0; i < stringColumns; ++i) {

                if(line.hasNext())
                    tempString.add(line.next());

            }//Now that strings are done, grab numbers:

            nameToRowIndex.put(tempString.get(SHORTDESC_INDEX), ++lineNumber);
            if(!categories.contains(tempString.get(CATEGORY_INDEX))) categories.add(tempString.get(CATEGORY_INDEX));//Add categories if they aren't already there
            stringTable.add(tempString);
            while (line.hasNext()){
                if (line.hasNextDouble()) {
                    tempData.add(line.nextDouble());
                } else {
                    tempData.add(0.0);//Still setting missing data to 0, may not be desirable.
                    line.next();
                }
            }
            table.add(tempData);


        }

    }
    //Adds an observer to be notified by following method (Swing-like)
    public void addObserver(Observer r){
        observerList.add(r);
    }

    //Notifies all observers, which is essentially
    public void notifyObservers(){
        super.notifyObservers();
        for (Observer x : observerList)
            x.update(this, "dirt");
    }

    public List<String> getNutrients() {
        return nutrientHeaders;
    }

    //Adds the selected item to the totalNutrients array
    public void addSelectedItem(String selectedItem, int meal) {
        double holds;
        int row = nameToRowIndex.get(selectedItem);
        for(int i = 0; i < table.get(row).size(); i++) {
            holds = totalNutrients.get(i);
            holds += table.get(row).get(i);
            totalNutrients.set(i, holds);
        }
        switch(meal)
        {
            case 0: Breakfast.add(selectedItem);
                break;
            case 1: Lunch.add(selectedItem);
                break;
            case 2: Dinner.add(selectedItem);
        }
        notifyObservers();
    }

    public void deleteSelectedItem(String selectedItem, int meal) {
        double holds;
        int index;
        int row = nameToRowIndex.get(selectedItem);
        for(int i = 0; i < table.get(row).size(); i++) {
            holds = totalNutrients.get(i);
            holds -= table.get(row).get(i);
            totalNutrients.set(i, holds);
        }

        switch(meal)
        {
            case 0: index = Breakfast.indexOf(selectedItem);
                Breakfast.remove(index);
                break;
            case 1: index = Lunch.indexOf(selectedItem);
                Lunch.remove(index);
                break;
            case 2: index = Dinner.indexOf(selectedItem);
                Dinner.remove(index);
                break;
        }
        notifyObservers();
    }

    public ArrayList<ArrayList<String>> getStringTable(){
        return stringTable;
    }


//Getting Goals and running Totals for each nutrient

    // 0 - Calories
    public double getCalorieGoal()
    { return nutrients.getGoal("Calories");}
    public double getCalorieTotal()
    { return totalNutrients.get(0);
    }

    // 1 - Total Fat (g)
    public double getTotalFatGoal()
    { return nutrients.getGoal("Calories");}
    public double getTotalFatTotal()
    { return totalNutrients.get(1);
    }

    // 2 - Saturated Fat (g)
    public double getSaturatedFatGoal()
    { return nutrients.getGoal("Saturated Fat (g)");}
    public double getSaturatedFatTotal()
    { return totalNutrients.get(2);
    }

    // 3 - Cholesterol (mg)
    public double getCholesterolGoal()
    { return nutrients.getGoal("Cholesterol (mg)");}
    public double getCholesterolTotal()
    { return totalNutrients.get(3);
    }

    // 4 - Sodium (mg)
    public double getSodiumGoal()
    { return nutrients.getGoal("Sodium (mg)");}
    public double getSodiumTotal()
    { return totalNutrients.get(4);
    }

    // 5 - Carbohydrates (g)
    public double getCarbohydratesGoal()
    { return nutrients.getGoal("Carbohydrates (g)");}
    public double getCarbohydratesTotal()
    { return totalNutrients.get(5);
    }

    // 6 - Sugar (g)
    public double getSugarGoal()
    { return nutrients.getGoal("Sugar (g)");}
    public double getSugarTotal()
    { return totalNutrients.get(6);
    }

    // 7 - Dietary Fiber (g)
    public double getDietaryFiberGoal()
    { return nutrients.getGoal("Dietary Fiber (g)");}
    public double getDietaryFiberTotal()
    { return totalNutrients.get(7);
    }

    // 8 - Protein (g)
    public double getProteinGoal()
    { return nutrients.getGoal("Protein (g)");}
    public double getProteinTotal()
    { return totalNutrients.get(8);
    }

    // 9 - Calcium (mg)
    public double getCalciumGoal()
    { return nutrients.getGoal("Calcium (mg)");}
    public double getCalciumTotal()
    { return totalNutrients.get(9);
    }

    // 10 - Potassium (mg)
    public double getPotassiumGoal()
    { return nutrients.getGoal("Potassium (mg)");}
    public double getPotassiumTotal()
    { return totalNutrients.get(10);
    }

    // 11 - Iron (mg)
    public double getIronGoal()
    { return nutrients.getGoal("Iron (mg)");}
    public double getIronTotal()
    { return totalNutrients.get(11);
    }

    // 12 - Zinc (mg)
    public double getZincGoal()
    { return nutrients.getGoal("Zinc (mg)");}
    public double getZincTotal()
    { return totalNutrients.get(12);
    }

    // 13 - Vitamin A (IU)
    public double getVitaminAGoal()
    { return nutrients.getGoal("Vitamin A (IU)");}
    public double geVitaminATotal()
    { return totalNutrients.get(13);
    }

    // 14 - Vitamin B6 (mg)
    public double getVitaminB6Goal()
    { return nutrients.getGoal("Vitamin B6 (mg)");}
    public double getVitaminB6Total()
    { return totalNutrients.get(14);
    }

    // 15 - Vitamin B12 (mcg)
    public double getVitaminB12Goal()
    { return nutrients.getGoal("Vitamin B12 (mcg)");}
    public double getVitaminB12Total()
    { return totalNutrients.get(15);
    }

    // 16 - Vitamin C (mg)
    public double getVitaminCGoal()
    { return nutrients.getGoal("Vitamin C (mg)");}
    public double getVitaminCTotal()
    { return totalNutrients.get(16);
    }

    // 17 - Vitamin D (IU)
    public double getVitaminDGoal()
    { return nutrients.getGoal("Vitamin D (IU)");}
    public double getVitaminDTotal()
    { return totalNutrients.get(17);
    }

    // 18 - Vitamin E (mg)
    public double getVitaminEGoal()
    { return nutrients.getGoal("Vitamin E (mg)");}
    public double getVitaminETotal()
    { return totalNutrients.get(18);
    }

    public void print()
    {
        String desc, servSize, print;
        int row, col;
        String nutrient;
        double goal, reached;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Let's Create a Meal Plan!%n"));
        sb.append(String.format("Daily Meal Plan Summary%n"));
        sb.append("-----------------------------");
        sb.append(String.format("%n%n%n"));
        sb.append(String.format("Meal         Food Description          Serving Size%n"));
        sb.append(String.format("-----        ----------------          ------------%n%n"));
        for(int i = 0; i < Breakfast.size(); i++)
        {
            row = nameToRowIndex.get(Breakfast.get(i));
            servSize = stringTable.get(row).get(3);
            sb.append(String.format("Breakfast            %s            %s%n", Breakfast.get(i), servSize));
        }
        for(int i = 0; i < Lunch.size(); i++)
        {
            row = nameToRowIndex.get(Lunch.get(i));
            servSize = stringTable.get(row).get(3);
            sb.append(String.format("Lunch            %s            %s%n", Lunch.get(i), servSize));
        }
        for(int i = 0; i < Dinner.size(); i++)
        {
            row = nameToRowIndex.get(Dinner.get(i));
            servSize = stringTable.get(row).get(3);
            sb.append(String.format("Dinner            %s            %s%n", Dinner.get(i), servSize));
        }
        sb.append(String.format("%n%n"));
        sb.append(String.format("Nutrient             Today's Goals           Percent Reached%n"));
        sb.append(String.format("--------             -------------           ---------------%n"));
        for(int i = 0; i < nutrientHeaders.size(); i++)
        {


            col = labelToIndex.get(nutrientHeaders.get(i));
            nutrient = nutrientHeaders.get(i);
            goal = nutrients.getGoal(nutrient);
            reached = totalNutrients.get(col)/goal;

            sb.append(String.format("%s          %s            %s%n", nutrient, goal, reached));
            sb.append(String.format("----------------------------------------------------------%n"));
        }
        print = sb.toString();
        System.out.print(print);
    }
}
