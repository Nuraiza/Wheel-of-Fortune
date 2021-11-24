import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Wheel extends StackPane {
    static Integer[] scores = new Integer[]{100, 300, 500, 250, 400, 800, 650, 150, 420, 900, 620, 230};
    private static final double Angle = 360d / (double) 12;
    private static double StartAngle = 270;
    int radius = 220;
    int XCenter = 200;
    int YCenter = 200;
    double angle = (2 * Math.PI) / 12;
    private static Text text;
    static ArrayList<Segment> arrayList = new ArrayList<>();

    public Wheel() {
        DrawSegments();
    }

    public void DrawSegments() {
        Group Segments = new Group();
        Group texts = new Group();
        for (int i = 0; i < scores.length; i++) {
            Segment segment = new Segment();
            segment.setStartAngle(StartAngle);


            if (i % 2 == 0) {
                segment.setFill(Color.DARKRED);
            } else {
                segment.setFill(Color.ANTIQUEWHITE);
            }
            StartAngle += Angle;
            Segments.getChildren().add(segment);
            double x = Math.sin(angle * i) * radius,
                    y = Math.cos(angle * i) * radius;

            Label label = new Label(scores[i].toString());
            label.relocate(x, y);
            label.setTextFill(Color.WHITE);
            label.setFont(new Font("Arial", 25));
            segment.setLabel(label);
            if (i % 2 == 0) {

                label.setStyle("-fx-background-color: IndianRed");
            } else {

                label.setStyle("-fx-background-color: DarkRed");
            }
            System.out.println(segment.getStartAngle() +
                    "  " + segment.getLabel().getText());
            arrayList.add(segment);

            texts.getChildren().add(label);
        }
        getChildren().addAll(texts, Segments);
    }


}
