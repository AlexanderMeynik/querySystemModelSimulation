package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.security.InvalidKeyException;
import java.util.ArrayList;

public class plotFormController {


    @FXML
    private TextField abDiff;


    @FXML
    private Button drawPlotsButton;

    @FXML
    private LineChart<String, Double> eChart;

    @FXML
    private NumberAxis eCol;

    @FXML
    private CategoryAxis lambCol;

    @FXML
    private CategoryAxis lambCol1;

    @FXML
    private CategoryAxis lambCol2;

    @FXML
    private TextField leftLimTextF;

    @FXML
    private LineChart<String, Double> pChart;

    @FXML
    private NumberAxis pCol;

    @FXML
    private TextField rightLimTextF;

    @FXML
    private ComboBox<String> selectVariableComboBox;

    @FXML
    private TextField stepTextF;

    @FXML
    private LineChart<String, Double> tChart;

    @FXML
    private NumberAxis tCol;

    @FXML
    private Label errLabel;

    @FXML
    private Tab tabE;

    @FXML
    private Tab tabP;

    @FXML
    private Tab tabT;

    Double myDoubleParser(String text, String num, int i2) throws InvalidKeyException {
        double a;
        try {
            if (text.matches("^[-+]?[0-9]*[.]?[0-9]+(?:[eE][-+]?[0-9]+)?$")) {
                a = Double.parseDouble(text);
                if (a > i2 || a <= 0) {
                    throw new InvalidKeyException("Error int tex field " + num + "! Number " + a + " is not valid!");
                }
            } else {
                throw new InvalidKeyException("Invalid  double format in field " + num + "!");
            }
            return a;
        } catch (NumberFormatException ex) {
            throw new InvalidKeyException("Text field " + num + " is empty!");
        }
    }


    @FXML
    void drawPlotsButtonClick(MouseEvent event) {
        errLabel.setText("");
        pChart.getData().clear();
        tChart.getData().clear();
        eChart.getData().clear();

        try {
            int type = -1;
            switch (selectVariableComboBox.getValue()) {
                case "a":
                    type = 0;
                    break;
                case "b":
                    type = 1;
                    break;
                case "λ":
                    type = 2;
                    break;
                case "s":
                    type = 3;
                    break;
                case "bu":
                    type = 4;
                    break;
                case "d":
                    type = 5;
            }
            double left = myDoubleParser(leftLimTextF.getText(), "Left limit", (type <= 2) ? 10 : 1000);
            double right = myDoubleParser(rightLimTextF.getText(), "Right limit", (type <= 2) ? 10 : 1000);
            double step = myDoubleParser(stepTextF.getText(), "Step", (type <= 2) ? 1 : 100);
            double abdiff = 0;
            if (type < 2)
                abdiff = myDoubleParser(abDiff.getText(), "ab Difference",  100);


            if (right <= left) {
                throw new InvalidKeyException("Field right limit can't be less or equal to left limit!");
            }
            if (step > (right - left)) {
                throw new InvalidKeyException("Step is bigger than gap length!");
            }
            if (type > 2) {
                if ((int) step != step || step == 0) {
                    throw new InvalidKeyException("Step in that case must be natural number!");
                }
            }

            double stept = step;
            int pow1 = 0;
            while (stept < 1) {
                stept *= 10;
                pow1++;
            }


            pChart.setCreateSymbols(false);
            tChart.setCreateSymbols(false);
            eChart.setCreateSymbols(false);

            ArrayList<ArrayList<Double>> info = EvCrFormController.getCoord(left, right, step, type, abdiff);
            XYChart.Series<String, Double> serP = new XYChart.Series<>();
            XYChart.Series<String, Double> serT = new XYChart.Series<>();
            XYChart.Series<String, Double> serE = new XYChart.Series<>();
            XYChart.Series<String, Double> serP2 = new XYChart.Series<>();
            XYChart.Series<String, Double> serT2 = new XYChart.Series<>();
            XYChart.Series<String, Double> serE2 = new XYChart.Series<>();
            int ssize = info.get(0).size();
            double[] mins = {100, 100, 100};
            double[] maxs = {-100, -100, -100};
            for (int i = 0; i < ssize; i++) {
                double par = Math.round(info.get(0).get(i) * Math.pow(10, pow1)) / Math.pow(10, pow1);
                serP.getData().add(new XYChart.Data<>(String.valueOf(par), info.get(1).get(i)));
                serP2.getData().add(new XYChart.Data<>(String.valueOf(par), 0.12));
                if (mins[0] > info.get(1).get(i)) {
                    mins[0] = info.get(1).get(i);
                }

                if (maxs[0] < info.get(1).get(i)) {
                    maxs[0] = info.get(1).get(i);
                }
                serT.getData().add(new XYChart.Data<>(String.valueOf(par), info.get(2).get(i)));
                serT2.getData().add(new XYChart.Data<>(String.valueOf(par), 2.6));
                if (mins[1] > info.get(2).get(i)) {
                    mins[1] = info.get(2).get(i);
                }

                if (maxs[1] < info.get(2).get(i)) {
                    maxs[1] = info.get(2).get(i);
                }

                serE.getData().add(new XYChart.Data<>(String.valueOf(par), info.get(3).get(i)));

                serE2.getData().add(new XYChart.Data<>(String.valueOf(par), 0.75));
                if (mins[2] > info.get(3).get(i)) {
                    mins[2] = info.get(3).get(i);
                }
                if (maxs[2] < info.get(3).get(i)) {
                    maxs[2] = info.get(3).get(i);
                }
            }
            pCol.setAutoRanging(false);
            tCol.setAutoRanging(false);
            eCol.setAutoRanging(false);
            pCol.setLowerBound(mins[0] - 0.01);
            tCol.setLowerBound(mins[1] - 0.1);
            eCol.setLowerBound(mins[2] - 0.01);
            pCol.setUpperBound(maxs[0] + 0.01);
            tCol.setUpperBound(maxs[1] + 0.1);
            eCol.setUpperBound(maxs[2] + 0.01);
            serP.setName("P=P(" + selectVariableComboBox.getValue() + ")");
            serT.setName("T=T(" + selectVariableComboBox.getValue() + ")");
            serE.setName("E=E(" + selectVariableComboBox.getValue() + ")");
            serP2.setName("p=0.12");
            serT2.setName("t=2.6");
            serE2.setName("e=0.75");
            pChart.getData().addAll(serP, serP2);
            tChart.getData().addAll(serT, serT2);
            eChart.getData().addAll(serE, serE2);
            tabP.setDisable(false);
            tabE.setDisable(false);
            tabT.setDisable(false);


        } catch (InvalidKeyException ex) {
            errLabel.setText(ex.getLocalizedMessage());
        }
    }

    @FXML
    void initialize() {
        selectVariableComboBox.getItems().removeAll(selectVariableComboBox.getItems());
        selectVariableComboBox.getItems().addAll("λ", "a", "b", "s", "bu", "d");
        selectVariableComboBox.setValue("λ");
        tabP.setDisable(true);
        tabE.setDisable(true);
        tabT.setDisable(true);
    }

}
