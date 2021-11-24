import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import static javafx.scene.control.ButtonType.OK;


public class NewGame extends Application {
    HBox upper = new HBox();
    BorderPane lower = new BorderPane();
    Label label;
    Button play = new Button("Play");
    Button exit = new Button("Exit");
    BorderPane bottom = new BorderPane();
    Group arrow = new Group();
    Group score1 = new Group();
    static Wheel wheeel;
    BorderPane b = new BorderPane();
    static TextField t;
    static Integer randomScore;
    static Integer currentScore = 9;

    static ArrayList<Label> array = new ArrayList<>();
    static RotateTransition rotate;

    @Override
    public void start(Stage stage) throws Exception {
        upper.setAlignment(Pos.CENTER);
        upper.setSpacing(5);
        play.setPrefSize(100, 40);
        exit.setPrefSize(60, 30);
        play.setTextFill(Color.BLACK);
        exit.setTextFill(Color.BLACK);
        play.setFont(new Font("Arial", 30));
        exit.setFont(new Font("Arial", 15));
        bottom.setRight(play);
        lower.setBottom(bottom);
        bottom.setBottom(exit);

        wheeel = new Wheel();
        for (int i = 0; i < Settings.getPhrase().length(); i++) {
            Character current = Settings.getPhrase().charAt(i);
            label = new Label(current.toString());
            label.setFont(new Font("Arial", 30));
            label.setPrefSize(35, 50);
            label.setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, null, null)));
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-text-fill: white; -fx-background-color: white;");
            upper.getChildren().add(label);
            upper.setHgrow(label, Priority.ALWAYS);
            array.add(label);

        }


        play.setOnAction(event -> {

            RandomRotation();
            rotate.setOnFinished(event1 -> {
                Platform.runLater(NewGame::Game);
            });

        });

        exit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("The game will not be saved " +
                    "\nAre you sure you want to exit?"
            );
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == OK) {
                Stage stage1 = new Stage();
                stage.getScene().getWindow().hide();
                try {
                    new Main().start(stage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        Rectangle rectangle = new Rectangle(50.0, 350.0, 70.0, 40.0);
        rectangle.setFill(Color.DARKRED);
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                120.0, 330.0, 200.0, 370.0, 120.0, 410.0);
        triangle.setFill(Color.DARKRED);
        arrow.getChildren().addAll(triangle, rectangle);
        b.setCenter(arrow);
        lower.setLeft(b);


        Label score = new Label(" Your score:");
        score.setFont(new Font("Arial", 16));
        t = new TextField("0");
        t.setEditable(false);
        t.setFont(new Font("Arial", 20));
        t.setStyle("-fx-font-weight: Bold");
        t.setPrefSize(120, 60);
        score.setLabelFor(t);
        score1.getChildren().addAll(t, score);


        lower.setTop(score1);
        lower.setCenter(wheeel);
        BorderPane root = new BorderPane();
        root.setTop(upper);
        root.setCenter(lower);
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);
        stage.setTitle("Game is starting");
        stage.show();
    }


    public static void RandomRotation() {
        Integer[] arr = new Integer[]{
                360, 390, 420, 450, 480, 510, 540, 570, 600, 630, 660, 690
        };


        rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setDuration(new Duration(2500));
        int minimum = 0;
        int maximum = 11;
        Random rn = new Random();
        int range = maximum - minimum + 1; //+1
        int randomAngle = arr[rn.nextInt(range)];
        rotate.setByAngle(randomAngle);
        randomAngle %= 360;
        randomAngle = randomAngle / 30;
        randomScore = currentScore + randomAngle;
        if (randomScore >= 12) {
            randomScore = randomScore - 12;
        }
        currentScore = randomScore;
        rotate.setNode(wheeel);
        rotate.play();

    }


    public static Boolean CheckingLabels() {
        for (Label a : array) {
            if (a.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")) {
                return true;
            }
        }
        return false;
    }

    public static void CalculatingScore() {

        Integer current = Integer.parseInt(t.getText());
        Integer sum = Wheel.scores[randomScore] + current;
        t.setText(sum.toString());

    }

    public static void Game() {
        String guessed;
        TextInputDialog e = new TextInputDialog();
        e.setHeaderText("Good luck!");
        e.setTitle("Heey");
        e.setContentText("Guess a letter");
        Optional<String> result = e.showAndWait();

        guessed = e.getEditor().getText();
        if (result.isPresent()) {
            if (Settings.getPhrase().contains(guessed)) {
                for (Label l : array) {
                    if (l.getText().contains(guessed) && l.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")) {
                        l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                        break;
                    }
                }
                CalculatingScore();
            } else {
                TextInputDialog e1 = new TextInputDialog();
                e1.setContentText("Wrong:( \n Now you have 2 attempts");
                e1.setHeaderText("Good luck!");
                e1.setTitle("Heey");
                Optional<String> result1 = e1.showAndWait();
                guessed = e1.getEditor().getText();
                if (result1.isPresent()) {
                    if (Settings.getPhrase().contains(guessed)) {
                        for (Label l : array) {
                            if (l.getText().contains(guessed) && l.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")) {
                                l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                                break;
                            }
                        }
                        CalculatingScore();
                    } else {
                        TextInputDialog e2 = new TextInputDialog();
                        e2.setContentText("Wrong:( \n Now you have 1 attempt");
                        e2.setHeaderText("Good luck!");
                        e2.setTitle("Heey");
                        Optional<String> result2 = e2.showAndWait();
                        guessed = e2.getEditor().getText();
                        if (result2.isPresent()) {
                            if (Settings.getPhrase().contains(guessed)) {
                                for (Label l : array) {
                                    if (l.getText().contains(guessed) && l.getStyle().equals("-fx-text-fill: white; -fx-background-color: white;")) {
                                        l.setStyle("-fx-text-fill: Black; -fx-background-color: White");
                                        break;
                                    }
                                }
                                CalculatingScore();
                            } else {
                                Alert m = new Alert(Alert.AlertType.INFORMATION);
                                m.setTitle("Heyy");
                                m.setHeaderText("Wrong:(");
                                m.setContentText(" Try one more time:)");
                                m.show();

                            }
                        }
                    }
                }
            }
        }
        if (!CheckingLabels()) {
            Alert congrats = new Alert(Alert.AlertType.INFORMATION);
            congrats.setTitle("The end");
            congrats.setHeaderText("Finish");
            congrats.setContentText("Wow your score is " + t.getText());
            congrats.showAndWait();
        }

    }
}



