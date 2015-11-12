package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Not a title");
        primaryStage.setScene(new Scene(root));//This change made on github in  Vis... /GVP
        primaryStage.show();//This is more complicated than it needs to be.ggg

    }


    public static void main(String[] args) {
        launch(args);
    }

    /*
    Load function, core.  Needs touching up for our specific needs:



     //Reads data in from a file.  Non-numerics are translated as 0, expects comma[space] as delimeter, probably unnecessary
    public static void load(String file) throws IOException {

        Scanner line, in;
        in = new Scanner(new File(file));
        String firstLine = in.nextLine();
        for (String x : firstLine.split(", ")) labels.add(x);
        labelToIndex = new Hashtable<String, Integer>(labels.size());
        for (int i = 0; i < labels.size(); ++i)
            labelToIndex.put(labels.get(i), i);

        while (in.hasNextLine()) {
            ArrayList<Double> tempData = new ArrayList<Double>(labels.size());
            line = new Scanner(in.nextLine());
            line.useDelimiter(", ");
            while (line.hasNext()) {
                if (line.hasNextDouble()) {
                    tempData.add(line.nextDouble());
                } else {
                    tempData.add(0.0);
                    line.next();
                }
            }
            data.add(tempData);


        }

    }
     */
}
