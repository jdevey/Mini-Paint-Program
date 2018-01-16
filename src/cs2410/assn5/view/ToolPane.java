package cs2410.assn5.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**  Helper Class file for Assignment #6.
 *
 * You can use this without any modification.
 * It's the file I used to create the program in the demo video.
 * @author Chad
 * @version 1.0
 */
class ToolPane extends HBox {

    private final ColorPicker fillPicker = new ColorPicker();
    private final ColorPicker strokePicker = new ColorPicker();
    private ComboBox<Integer> strokeSize = new ComboBox<>(FXCollections.observableArrayList(1, 3, 5));
    private ToggleButton editBtn = new ToggleButton("Edit");
    private ToggleButton eraseBtn = new ToggleButton("Erase");
    private ToggleButton ellBtn = new ToggleButton("Ellipse");
    private ToggleButton rectBtn = new ToggleButton("Rectangle");
    private ToggleButton freeBtn = new ToggleButton("Freehand");

    /**
     * Constructor for the tool pane, is in charge of building each button, initializing values, and setting
     * event handlers
     */
    ToolPane() {
        Text fillText = new Text("Fill");
        Text strokeText = new Text("Stroke");
        this.getChildren().addAll(
            fillText, fillPicker, strokeText, strokePicker, strokeSize, editBtn, eraseBtn, ellBtn, rectBtn, freeBtn);

        //adding ToggleButtons to a ToggleGroup makes it so only one can be selected at a time.
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(editBtn, eraseBtn, ellBtn, rectBtn, freeBtn);

        //Setting some initial values for when the program starts
        rectBtn.setSelected(true);
        fillPicker.setValue(Color.LIME);
        strokePicker.setValue(Color.OLIVE);
        fillPicker.setStyle("-fx-color-label-visible: false");
        strokePicker.setStyle("-fx-color-label-visible: false");
        strokeSize.setValue(3);

        //Setting spacing for the buttons
        this.setPadding(new Insets(5, 5, 5, 5));
        this.setSpacing(5);

        //Initializing the event handlers for the color picker and other similar buttons
        setFillPickerAction();
        setStrokePickerAction();
        setStrokeSizeAction();
    }

    /**
     * Setting an action for when a color in the color picker is chosen
     */
    void setFillPickerAction() {
        fillPicker.setOnAction((ActionEvent t) -> {
            if (this.editBtnSelected()) {
                Interface.currentShape.setFill(getFillPickerValue());
            }
        });
    }

    /**
     * This function sets the selected color in the color picker to whatever shape is currently selected
     *
     * @param color Must be a color value, not paint
     */
    void setFillPickerValue(Color color) {fillPicker.setValue(color);}

    /**
     * Setting an action for when the stroke color is chosen
     */
    void setStrokePickerAction() {
        strokePicker.setOnAction((ActionEvent t) -> {
            if (this.editBtnSelected()) {
                Interface.currentShape.setStroke(getStrokePickerValue());
            }
        });
    }

    /**
     * Setting the color for the stroke color picker whenever an object is selected
     *
     * @param color Again, must be a color value, not paint
     */
    void setStrokePickerValue(Color color) {strokePicker.setValue(color);}

    /**
     * Setting an action for when a new stroke size is selected
     */
    void setStrokeSizeAction() {
        strokeSize.setOnAction((ActionEvent t) -> {
            if (this.editBtnSelected()) {
                Interface.currentShape.setStrokeWidth(getStrokeSizeValue());
            }
        });
    }

    /**
     * Setting the size for the stroke size picker whenever an object is selected
     *
     * @param val Must be an Integer value, not int or float
     */
    void setStrokeSizeValue(Integer val) {strokeSize.setValue(val);}

    /**
     * Current fill for the color picker
     *
     * @return returns a color value
     */
    Color getFillPickerValue() {return fillPicker.getValue();}

    /**
     * Current fill for the stroke color picker
     *
     * @return returns a color value
     */
    Color getStrokePickerValue() {return strokePicker.getValue();}

    /**
     * Current size for the stroke
     *
     * @return returns and Integer value
     */
    Integer getStrokeSizeValue() {return strokeSize.getValue();}

    /**
     * Determine whether the edit button is selected
     *
     * @return a boolean value
     */
    boolean editBtnSelected() {return editBtn.isSelected();}

    /**
     * Determine whether the erase button is selected
     *
     * @return a boolean value
     */
    boolean eraseBtnSelected() {return eraseBtn.isSelected();}

    /**
     * Determine whether the ellipse button is selected
     *
     * @return a boolean value
     */
    boolean ellBtnSelected() {return ellBtn.isSelected();}

    /**
     * Determine whether the rectangle button is selected
     *
     * @return a boolean value
     */
    boolean rectBtnSelected() {return rectBtn.isSelected();}

    /**
     * Determine whether the free-hand button is selected
     *
     * @return a boolean value
     */
    boolean freeBtnSelected() {return freeBtn.isSelected();}
}