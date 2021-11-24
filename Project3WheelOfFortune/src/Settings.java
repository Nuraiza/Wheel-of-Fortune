import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.*;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;


public class Settings extends Application {
    static ListView<String> view = null;
    ListView<String> view2 = null;
    Button add = new Button("Add");
    Button a = new Button("->");
    Button b = new Button("<-");
    Button done = new Button("Done");
    String string = null;
    private static String phrase;

    public static String getPhrase() {
        return phrase;
    }

    public static void setPhrase(String phrase) {
        Settings.phrase = phrase;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ObservableList<String> list1 = FXCollections.observableArrayList();
        ObservableList<String> list2 = FXCollections.observableArrayList("TroubleMaker", "Coca-Cola", "Footprints");

        BufferedReader br = new BufferedReader(new FileReader(".\\src\\History"));
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.equals("")) {  //C:\Users\test\IdeaProjects\Project3WheelOfFortune\src\History
                list1.add(s);
            }
        }

        br.close();

        view = new ListView<>(list2);
        view2 = new ListView<>(list1);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New guessing word");
        dialog.setHeaderText("Enter word/phrase");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Too long word/phrase:)");
        add.setOnAction(event -> {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                string = dialog.getEditor().getText();
                if (string.length() <= 25) {
                    list1.add(string);
                    BufferedWriter b = null;
                    try {
                        b = new BufferedWriter(new FileWriter("C:\\Users\\test\\IdeaProjects\\ProjectGame\\src\\History", true));
                        b.write("\n" + string);
                        b.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (b != null)
                            try {
                                b.close();
                            } catch (IOException e2) {
                            }
                    }
                    dialog.getEditor().clear();
                } else {
                    alert.show();
                }
            }
        });


        EventHandler<ActionEvent> hHandler =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ListView<String> fromView = null;
                        ObservableList<String> fromList = null,
                                toList = null;
                        if (event.getSource().equals(b)) {
                            fromView = view2;
                            fromList = list1;
                            toList = list2;
                        } else if (event.getSource().equals(a)) {
                            fromView = view;
                            fromList = list2;
                            toList = list1;
                        } else return;
                        string = fromView.getSelectionModel().getSelectedItem();
                        if (string != null) {
                            fromView.getSelectionModel().clearSelection();
                            fromList.remove(string);
                            toList.add(string);
                        }
                    }
                };

        a.setOnAction(hHandler);
        b.setOnAction(hHandler);

        done.setOnAction(event -> {
            int count = 0;
            String current;
            for (ListIterator<String> iterator = list2.listIterator(); iterator.hasNext(); current = iterator.next()) {
                count++;
            }
            int minimum = 0;
            int maximum = count - 1;
            Random rn = new Random();
            int range = maximum - minimum + 1;
            int index = rn.nextInt(range) + minimum;

            phrase = view.getItems().get(index);
            System.out.println(phrase);

            Stage stage1 = new Stage();
            stage.getScene().getWindow().hide();
            try {
                new NewGame().start(stage1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        BorderPane root = new BorderPane();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 20, 10, 20));
        pane.setHgap(10);
        pane.setVgap(10);

        VBox box = new VBox();
        box.getChildren().addAll(add, a, b, done);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        Label label1 = new Label("  Active");
        Label label2 = new Label("  Inactive");
        label1.setFont(new Font("Arial", 20));
        label2.setFont(new Font("Arial", 20));
        GridPane.setHalignment(label1, HPos.CENTER);
        GridPane.setHalignment(label2, HPos.CENTER);

        ColumnConstraints c1 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        ColumnConstraints c2 = new ColumnConstraints(80);
        ColumnConstraints c3 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        c1.setHgrow(Priority.ALWAYS);
        c3.setHgrow(Priority.ALWAYS);
        pane.getColumnConstraints().addAll(c1, c2, c3);

        pane.add(label1, 0, 0);
        pane.add(label2, 2, 0);
        pane.add(view, 0, 1);
        pane.add(box, 1, 1);
        pane.add(view2, 2, 1);

        root.setCenter(pane);
        Scene scene = new Scene(root, 450, 450);
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.show();


    }
}

