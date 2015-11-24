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

    String[] goalNames = {"Calories","Protein (g)","Total Fat (g)","Carbohydrates (g)","Dietary Fiber (g)","Sugar (g)",
            "Calcium (mg)","Iron (mg)","Potassium (mg)",
            "Sodium (mg)","Zinc (mg)","Vitamin C (mg)","Vitamin B6 (mg)","Vitamin B12 (mcg)","Vitamin A (IU)",
            "Vitamin E (mg)","Vitamin D (IU)","Saturated Fat (g)", "Cholesterol (g)"};


    ArrayList<Integer> nutrientsToTrack = new ArrayList<Integer>(5);//Correspond to the columns we'll be using to index into table
    ArrayList<ArrayList<String>> stringTable= new ArrayList<ArrayList<String>>();//holds the strings for categories and foods
    ArrayList<ArrayList<Double>> table = new ArrayList<ArrayList<Double>>();//Holds all the numerical nutritional data
    Hashtable<String, Integer> labelToIndex = new Hashtable<String, Integer>(),//quickly looks up the column from the string label
            nameToRowIndex = new Hashtable<String, Integer>();//quickly looks up the row from the string name of the item
    ArrayList<Double> totalNutrients = new ArrayList<Double>();//Holds total nutrients from added items
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
    public void addSelectedItem(String selectedItem) {
        double holds;
        int row = nameToRowIndex.get(selectedItem);
        for(int i = 0; i < table.get(row).size(); i++) {
            holds = totalNutrients.get(i);
            holds += table.get(row).get(i);
            totalNutrients.set(i, holds);
        }
        notifyObservers();
    }

    public void deleteSelectedItem(String selectedItem) {
        double holds;
        int row = nameToRowIndex.get(selectedItem);
        for(int i = 0; i < table.get(row).size(); i++) {
            holds = totalNutrients.get(i);
            holds -= table.get(row).get(i);
            totalNutrients.set(i, holds);
        }
        notifyObservers();
    }

    public ArrayList<ArrayList<String>> getStringTable(){
        return stringTable;
    }



    public double getCalorieGoal()
    { return nutrients.getGoal("Calories");}
    public double getCalorieTotal()
    {return totalNutrients.get(0);
    }
    public double getProteinGoal()
    { return 0;}
    public double getProteinTotal()
    {return 0;
    }



}
