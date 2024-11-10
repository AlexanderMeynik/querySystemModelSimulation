package gui;

import Logic.Simulator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultsFormController {


    @FXML
    private Label sLabel;

    @FXML
    private Label bLabel;

    @FXML
    private Label dLabel;

    @FXML
    private Label aLabel;

    @FXML
    private Label buLabel;
    @FXML
    private Label lambLabel;

    @FXML
    private Label rLabel;


    @FXML
    private TableView<DispertionsInfoString> TableDispertions;

    @FXML
    private TableColumn<DispertionsInfoString, Double> dProcCol;

    @FXML
    private TableColumn<DispertionsInfoString, Double> dStayCol;

    @FXML
    private TableView<DeviceEfficiencyResultString> deviceEfficiencyTable;

    @FXML
    private TableView<sourcesEfficiencyResultString> efficiencyTable;

    @FXML
    private TableColumn<sourcesEfficiencyResultString, Double> refusalProbabilityCol;

    @FXML
    private TableColumn<sourcesEfficiencyResultString, Integer> requestAmountCol;

    @FXML
    private TableColumn<sourcesEfficiencyResultString, String> sourceCol;

    @FXML
    private TableColumn<sourcesEfficiencyResultString, Double> timeMakeCol;

    @FXML
    private TableColumn<sourcesEfficiencyResultString, Double> timeWaitCol;

    @FXML
    private TableColumn<sourcesEfficiencyResultString, Double> timeStayCol;


    @FXML
    private Button openChartButton;


    @FXML
    void openChartButtonClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/plotForm.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }



    @FXML
    private TableColumn<DeviceEfficiencyResultString, String> sourcesCol;

    @FXML
    private TableColumn<DeviceEfficiencyResultString, Double> usageCoefficientCol;

    @FXML
    void initialize() {

        aLabel.setText("a = "+Simulator.alpha);
        buLabel.setText("b = "+Simulator.beta);
        lambLabel.setText("λ = "+Simulator.lambda);
        sLabel.setText("Source number = "+Controller.getS());
        bLabel.setText("Buffer number = "+Controller.getB());
        dLabel.setText("Device number = "+Controller.getD());
        rLabel.setText("Requests amount = "+Controller.getR());
        sourceCol.setCellValueFactory(new PropertyValueFactory<>("source"));
        requestAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfRequests"));
        refusalProbabilityCol.setCellValueFactory(new PropertyValueFactory<>("probabilityOfRefusal"));
        timeStayCol.setCellValueFactory(new PropertyValueFactory<>("tStay"));
        timeWaitCol.setCellValueFactory(new PropertyValueFactory<>("tWait"));
        timeMakeCol.setCellValueFactory(new PropertyValueFactory<>("tProc"));

        sourcesCol.setCellValueFactory(new PropertyValueFactory<>("source"));
        usageCoefficientCol.setCellValueFactory(new PropertyValueFactory<>("efficiency"));

        dStayCol.setCellValueFactory(new PropertyValueFactory<>("dStay"));
        dProcCol.setCellValueFactory(new PropertyValueFactory<>("dProc"));
        ObservableList<sourcesEfficiencyResultString> s1 = Simulator.getInfoSources();
        ObservableList<DispertionsInfoString> adds = EvCrFormController.getDds();
        efficiencyTable.setItems(s1);
        TableDispertions.setItems(adds);
        deviceEfficiencyTable.setItems(Simulator.getInfoDevices());
        Simulator.clear();
        System.out.println(Simulator.getPav()+"\t"+Simulator.getTav()+"\t"+Simulator.getEav()+"\n");

    }

}
