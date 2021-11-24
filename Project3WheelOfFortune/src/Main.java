import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    ArrayList<String> hey;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        hey = new ArrayList<String>();
        hey.add("Footprints");
        hey.add("Coca-cola");
        hey.add("Troublemaker");


        BorderPane panel = new BorderPane();

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Button newgame = new Button("New Game");
        Button settings = new Button("Settings");
        Button exit = new Button("Exit");
        box.getChildren().addAll(newgame, settings, exit);


        Label label = new Label("     Wheel of \n      Fortune ");
        label.setTextAlignment(TextAlignment.CENTER);
        Font font = new Font("Arial", 38);
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
        label.setPrefSize(250, 100);
        panel.setTop(label);
        panel.setCenter(box);

        newgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                int minimum = 0;
                int maximum = 2;
                Random rn = new Random();
                int range = maximum - minimum + 1;
                int index = rn.nextInt(range);

                Settings.setPhrase(hey.get(index));

                Stage stage1 = new Stage();
                stage.getScene().getWindow().hide();
                try {
                    new NewGame().start(stage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage stage1 = new Stage();
                stage.getScene().getWindow().hide();
                try {
                    new Settings().start(stage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });


        Scene scene = new Scene(panel, 300, 300);
        stage.setTitle("Wheel of Fortune");
        stage.setScene(scene);
        stage.show();
    }
}


