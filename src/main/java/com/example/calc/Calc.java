package com.example.calc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Map;

public class Calc extends Application {

    private static final String[][] template ={ // initializing digit string
            {"7","8","9","/"},
            {"4","5","6","*"},
            {"1","2","3","-"},
            {"0","clr","=","+"}
    };

    private final Map<String, Button> accelerators = new HashMap<>();

    private final DoubleProperty stackValue = new SimpleDoubleProperty();
    private final DoubleProperty value = new SimpleDoubleProperty();

    private  enum  Op{NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE}

    private Op curOp = Op.NOOP;
    private  Op stackOp = Op.NOOP;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final TextField screen = createScreen();
        final TilePane buttons = createButtons();

        stage.setTitle("Calc");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setScene(new Scene(createLayout(screen, buttons)));
        stage.show();

    }

    private TilePane createButtons() {
        TilePane buttons = new TilePane();
        buttons.setVgap(7);
        buttons.setHgap(7);
        buttons.setPrefColumns(template[0].length);
        for (String[] r : template) {
            for (String s : r) {
                buttons.getChildren().add(createButton(s));
            }
        }
        return buttons;
    }

    private Button createButton(final String s) {
        Button button = new Button();
        return button;
    }

    private VBox createLayout(TextField screen,TilePane buttons){
        final VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: chocolate; -fx-padding: 20; -fx-font-size: 20");
        layout.getChildren().setAll(screen, buttons);
        handleAccelerators(layout);
        screen.prefWidthProperty().bind(buttons.widthProperty());
        return layout;
    }

    private void handleAccelerators(VBox layout) {
        layout.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            Button activated = accelerators.get(keyEvent.getText());
            if (activated != null) {
                activated.fire();
            }
        });
    }

    private TextField createScreen() {
        final TextField screen = new TextField();
        screen.setStyle("-fx-background-color: aquamarine;");
        screen.setAlignment(Pos.CENTER_RIGHT);
        screen.setEditable(false);
        screen.textProperty().bind(Bindings.format("%.0f", value));
        return screen;
    }


}