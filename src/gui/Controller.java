package gui;


import Logic.Simulator;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.InvalidKeyException;

public class Controller {


    private static int b;
    private static int d;
    private static int s;
    private static double lamb;
    private static double a;
    private static double bu;

    public static int getD() {
        return d;
    }

    public static int getS() {
        return s;
    }


    public static int getB() {
        return b;
    }


    public static int getR() {
        return r;
    }

    public static void setR(int r) {
        Controller.r = r;
    }

    private static int r;

    public static boolean isM() {
        return m;
    }


    @FXML
    private TextField aTextField;

    @FXML
    private TextField bTextField;

    @FXML
    private TextField lambTextField;

    private static boolean m;


    @FXML
    private CheckBox autoPickButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button runSimulationButton;

    @FXML
    private TextField bffNum;

    @FXML
    private TextField devNum;

    @FXML
    private Label errLabel;


    @FXML
    private TextField reqAmount;

    @FXML
    private ComboBox<String> runModeCBox;

    @FXML
    private TextField srcNum;

    public static double getBu() {
        return bu;
    }

    public static double getA() {
        return a;
    }

    public static double getLamb() {
        return lamb;
    }

    @FXML
    void autoPickButtonClick(MouseEvent event) {
        reqAmount.setText("");
        reqAmount.setDisable(autoPickButton.isSelected());

    }

    @FXML
    void clearButtonClick(MouseEvent event) {
        reqAmount.setDisable(false);
        reqAmount.setText("");
        srcNum.setText("");
        devNum.setText("");
        bffNum.setText("");
        runModeCBox.setValue("Automatic");
        errLabel.setText("");
        aTextField.setText("");
        bTextField.setText("");
        lambTextField.setText("");
        autoPickButton.setSelected(false);
    }

    int myIntParser(String text, String num, int i2) throws InvalidKeyException {
        int a;
        try {
            a = Integer.parseInt(text);
            if (a > i2 || a <= 0) {
                throw new InvalidKeyException("Error int tex field " + num + "! Number " + a + " is not valid!");
            }
            return a;
        } catch (NumberFormatException ex) {
            throw new InvalidKeyException("Text field " + num + " is empty!");
        }
    }

    int myIntParser(String text, String num) throws InvalidKeyException {
        return myIntParser(text, num, 100);
    }

    @FXML
    void simButtonClick(MouseEvent event) {
        errLabel.setText("");
        try {
            s = myIntParser(srcNum.getText(), "Source number");
            b = myIntParser(bffNum.getText(), "Buffer number");
            d = myIntParser(devNum.getText(), "Device number");

            String refexpr = "^[-+]?[0-9]*[.]?[0-9]+(?:[eE][-+]?[0-9]+)?$";
            if (aTextField.getText().matches(refexpr)) {
                a = Double.parseDouble(aTextField.getText());
                if (a != Simulator.alpha)
                    Simulator.setAlpha(a);
            } else {
                throw new InvalidKeyException("Invalid value in text field  a");
            }
            if (bTextField.getText().matches(refexpr)) {
                bu = Double.parseDouble(bTextField.getText());
                if (bu != Simulator.beta)
                    Simulator.setBeta(bu);
            } else {
                throw new InvalidKeyException("Invalid value in text field  b");
            }
            if (lambTextField.getText().matches(refexpr)) {
                lamb = Double.parseDouble(lambTextField.getText());
                if (lamb != Simulator.lambda)
                    Simulator.setLambda(lamb);
            } else {
                throw new InvalidKeyException("Invalid value in text field  lamb");
            }
            if (!autoPickButton.isSelected()) {
                r = myIntParser(reqAmount.getText(), "Amount of requests", 1000000);
            } else {
                r = -1;
            }
            m = runModeCBox.getValue().equals("Automatic");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/eventCalForm.fxml"));

            try {
                loader.load();
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> EvCrFormController.onStageClose());
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (InvalidKeyException ex) {
            errLabel.setText(ex.getLocalizedMessage());
        }
    }


    @FXML
    void initialize() {
        runModeCBox.getItems().removeAll(runModeCBox.getItems());
        runModeCBox.getItems().addAll("Automatic", "step-by-step");
        runModeCBox.getSelectionModel().select("Automatic");

        aTextField.setText(String.valueOf(Simulator.alpha));
        bTextField.setText(String.valueOf(Simulator.beta));
        lambTextField.setText(String.valueOf(Simulator.lambda));
        ChangeListener<String> forceNumberListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                ((StringProperty) observable).set(oldValue);
        };

        bffNum.textProperty().addListener(forceNumberListener);
        srcNum.textProperty().addListener(forceNumberListener);
        devNum.textProperty().addListener(forceNumberListener);


    }




}
