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

    ArrayList<String> labels= new ArrayList<String>(),
            headers = new ArrayList<String>(),
            categories = new ArrayList<String>();

    ArrayList<ArrayList<String>> stringTable= new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<Double>> table = new ArrayList<ArrayList<Double>>();
    Hashtable<String, Integer> labelToIndex = new Hashtable<String, Integer>(),
            nameToRowIndex = new Hashtable<String, Integer>();
    private ArrayList<Observer> observerList = new ArrayList<Observer>();

    Model(String file){
      try{
          load(file);
      }
      catch (IOException e){
          System.err.println("IO failed, "+ e.getMessage());
          //System.exit(-1);
      }

    }

    Model(){
        this("foodFile.csv");
        //Do stuff
    }

    public void load(String file) throws IOException {

        Scanner line, in;
        in = new Scanner(new File(file));
        String firstLine = in.nextLine();
        for (String x : firstLine.split(", ")) headers.add(x);
        labelToIndex = new Hashtable<String, Integer>(headers.size());
        for (int i = 0; i < headers.size(); ++i)
            labelToIndex.put(labels.get(i), i);

        while (in.hasNextLine()) {
            ArrayList<Double> tempData = new ArrayList<Double>(labels.size());
            line = new Scanner(in.nextLine());
            line.useDelimiter(", ");
            //Except for the first line, csv should have a couple of strings which go in stringTable, then the rest
            //go in table; if I missed other string descriptors, change i's bound for that case.
            ArrayList<String> tempString = new ArrayList<String>(2);//Size doesn't really matter here, slightly
            //faster if it matches i's bound above, but ArrayList will resize automatically
            for (int i = 0; i < 2; ++i) {

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
}
