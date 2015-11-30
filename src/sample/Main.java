package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

//Sets everything up, don't make any changes unless absolutely necessary.
public class Main extends Application {

    static sample.Model model;
    static Controller controller;
    static Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        controller = new Controller();
        fxmlLoader.setController(controller);
        root = fxmlLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle(" ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        controller.setModel(model);
        model.addObserver(controller);
        ImageView dinoImageView = (ImageView) root.lookup("#DinosaurImageView");
        InputStream inDino = new FileInputStream("dino1.png");
        Image dino = new Image(inDino);
        dinoImageView.setImage(dino);

    }


    public static void main(String[] args) {
        model = new Model();
        launch(args);

    }
}
