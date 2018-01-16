package cs2410.assn5.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;

/**
 * Contains all of the methods, member variables, and logic needed for the paint program besides the tool pane
 * provided by Chad Mano.
 *
 * @author devey
 * @version 1.0
 */
public class Interface extends Application {

    private ToolPane tpObj = new ToolPane();

    private Pane rootPane = new Pane();
    private Pane drawPane = new Pane();
    private Pane toolBar = new Pane();
    private Rectangle tempRect;
    private Ellipse tempEll;
    private Path tempPath;
    static Shape currentShape;
    private double origX;
    private double origY;
    private double shapeOrigX;
    private double shapeOrigY;

    /**
     * Contains the steps necessary to get the paint program up and running
     *
     * @param primaryStage Given this parameter by default when launched
     * @throws Exception Throws an exception if the application fails to start
     */
    @Override
    public void start (Stage primaryStage) throws Exception {

        // Making the panes
        // Because we have both the drawing and toolbar panes, we must put them both into one master pane
        rootPane.getChildren().addAll(drawPane, toolBar);

        drawPane.setOnMousePressed(event -> paneClick(event));
        drawPane.setOnMouseDragged(event -> paneDrag(event));

        // Making the scene and stage
        Scene scene1 = new Scene(rootPane, 600, 600);
        primaryStage.setScene(scene1);
        primaryStage.setResizable(false);
        primaryStage.show();

        drawPane.setPrefSize(600, 575);
        drawPane.setLayoutX(0);
        drawPane.setLayoutY(25);

        Rectangle clip1 = new Rectangle(14, 18, 580, 555);
        drawPane.setClip(clip1);

        toolBar.getChildren().add(tpObj);
    }

    /**
     * Method called whenever the user clicks somewhere in the draw pane, can do one of five things
     *
     * @param event Triggered by some mouse event with known properties
     */
    private void paneClick(MouseEvent event) {
        origX = event.getX();
        origY = event.getY();

        if (tpObj.ellBtnSelected()) {
            tempEll = new Ellipse(origX, origY, 0, 0);
            initShape(tempEll);
            drawPane.getChildren().add(tempEll);
        }
        else if (tpObj.rectBtnSelected()) {
            tempRect = new Rectangle(origX, origY, 0, 0);
            initShape(tempRect);
            drawPane.getChildren().add(tempRect);
        }
        else if (tpObj.freeBtnSelected()) {
            tempPath = new Path();
            initShape(tempPath);
            drawPane.getChildren().add(tempPath);
            //Creating the first point in the path
            tempPath.getElements().add(new MoveTo(origX, origY));
        }
    }

    /**
     * Method called whenever the mouse is dragged on the pane, indicating that a shape is being drawn
     *
     * @param event A mouse event
     */
    private void paneDrag(MouseEvent event) {

        if (tpObj.ellBtnSelected()) {
            double width = Math.abs(event.getX() - origX);
            double height = Math.abs(event.getY() - origY);

            tempEll.setCenterX(origX + (event.getX()-origX)/2);
            tempEll.setCenterY(origY + (event.getY()-origY)/2);

            tempEll.setRadiusX(width/2);
            tempEll.setRadiusY(height/2);
        }
        else if (tpObj.rectBtnSelected()) {
            double width = Math.abs(event.getX() - origX);
            double height = Math.abs(event.getY() - origY);

            tempRect.setWidth(width);
            tempRect.setHeight(height);
            tempRect.setX(Math.min(event.getX(), origX));
            tempRect.setY(Math.min(event.getY(), origY));
        }
        else if (tpObj.freeBtnSelected()) {
            tempPath.getElements().add(new LineTo(event.getX(), event.getY()));
        }
    }

    /**
     * Method called to initialize a few basic characteristics of each shape, and set some event handlers for the shape
     *
     * @param shape A shape object, which may be one of three types
     */
    private void initShape(Shape shape) {
        if (!shape.equals(tempPath)) shape.setFill(tpObj.getFillPickerValue());
        shape.setStroke(tpObj.getStrokePickerValue());
        shape.setStrokeWidth(tpObj.getStrokeSizeValue());

        //Setting the current shape to the one being created so we can edit it directly after making it
        currentShape = shape;

        //Setting event handlers for the shape
        shapeHandler(shape);

        //Allowing the shapes to be edited as soon as they are created and the edit button is selected
        tpObj.setFillPickerAction();
        tpObj.setStrokePickerAction();
        tpObj.setStrokeSizeAction();
    }

    /**
     * Setting the event handlers for each shape
     *
     * @param shape A shape object
     */
    private void shapeHandler(Shape shape) {
        shape.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                shapeOrigX = event.getX();
                shapeOrigY = event.getY();

                //Code that handles editing shapes
                if (tpObj.editBtnSelected()) {
                    //Moving the shape to the front
                    currentShape = shape;
                    drawPane.getChildren().remove(shape);
                    drawPane.getChildren().add(shape);

                    //Changing the color picker and other settings to match the selected shape
                    tpObj.setFillPickerValue((Color)currentShape.getFill());
                    tpObj.setStrokePickerValue((Color)currentShape.getStroke());
                    Integer intermediateInt = (int)currentShape.getStrokeWidth();
                    tpObj.setStrokeSizeValue(intermediateInt);
                }
                //Code that handles deleting objects
                else if (tpObj.eraseBtnSelected()) {
                    drawPane.getChildren().remove(shape);
                }
            }
        });
        //Code that handles dragging objects around
        shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle (MouseEvent pevent) {
                if (tpObj.editBtnSelected()) {
                    shape.setTranslateX(shape.getTranslateX() + pevent.getX() - shapeOrigX);
                    shape.setTranslateY(shape.getTranslateY() + pevent.getY() - shapeOrigY);
                }
            }
        });
    }
}