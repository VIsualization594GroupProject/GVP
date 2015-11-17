package sample;

import java.io.File;
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
            headers = new ArrayList<String>(),
            categories = new ArrayList<String>();
    String[] goalNames = {"Calories","Protein","Fats","Carbohydrates","Fiber","Sugar","Calcium","Iron","Potassium",
            "Sodium","Zinc","Vitamin C","Vitamin B6","Folic Acid","Vitamin B12","Vitamin A (IU)",
            "Vitamin E","Vitamin D","Vit_K","Saturated Fats","Monounsaturated Fats",
            "Polyunsaturated Fats","Cholesterol"};


    ArrayList<Integer> nutrientsToTrack = new ArrayList<Integer>(5);//Correspond to the columns we'll be using to index into table
    ArrayList<ArrayList<String>> stringTable= new ArrayList<ArrayList<String>>();//holds the strings for categories and foods
    ArrayList<ArrayList<Double>> table = new ArrayList<ArrayList<Double>>();//Holds all the numerical nutritional data
    Hashtable<String, Integer> labelToIndex = new Hashtable<String, Integer>(),//quickly looks up the column from the string label
            nameToRowIndex = new Hashtable<String, Integer>();//quickly looks up the row from the string name of the item
    private ArrayList<Observer> observerList = new ArrayList<Observer>();

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



    }


    public void updateNutrientCalcBasedonPersonalDetails(){
        nutrients.GenerateNewGoalsUsingAgeEtc();
    }


    Model(){
        this("foodFile.csv");
        //Do stuff
    }

    public void load(String file) throws IOException {

        Scanner line, in;
        in = new Scanner(new File(file));
        String firstLine = in.nextLine();
        int stringColumns = 3;//Used as a constant, to selectively ignore the first (three) columns of the csv file
        String[] headerLabels = firstLine.split(", ");
        for (int i = stringColumns; i < headerLabels.length; i++) {
            headers.add(headerLabels[i]);
        }
        labelToIndex = new Hashtable<String, Integer>(headers.size());
        for (int i = 0; i < headers.size(); ++i)
            labelToIndex.put(labels.get(i), i);

        while (in.hasNextLine()) {
            ArrayList<Double> tempData = new ArrayList<Double>(labels.size());
            line = new Scanner(in.nextLine());
            line.useDelimiter(", ");
            //Except for the first line, csv should have a couple of strings which go in stringTable, then the rest
            //go in table; if I missed other string descriptors, change i's bound for that case.
            ArrayList<String> tempString = new ArrayList<String>(stringColumns);
            for (int i = 0; i < stringColumns; ++i) {

                if(line.hasNext())
                    tempString.add(line.next());

            }//Now that strings are done, grab numbers:


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
        return labels;
    }
}
