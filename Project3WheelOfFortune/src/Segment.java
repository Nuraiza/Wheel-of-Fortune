import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;


public class Segment extends Arc {
    private static final double Angle = 360d / (double) 12;
    private double StartAngle;
    int radius = 200;
    int XCenter = 200;
    int YCenter = 200;
    private Label label;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }


    public Segment() {

        setCenterX(XCenter);
        setCenterY(YCenter);
        setRadiusX(radius);
        setRadiusY(radius);
        setStartAngle(StartAngle);
        setLength(Angle);
        setType(ArcType.ROUND);
        setStroke(Color.BLACK);


    }

}
