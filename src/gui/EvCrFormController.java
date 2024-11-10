package gui;

import Logic.Simulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EvCrFormController {

    public static void setRun(boolean run) {
        EvCrFormController.run = run;
    }

    private static boolean run = true;

    public static ObservableList<DispertionsInfoString> getDds() {
        return dds;
    }

    private static ObservableList<DispertionsInfoString> dds = FXCollections.observableArrayList();
    private ObservableList<EventOccurString> storage = FXCollections.observableArrayList();
    @FXML
    private Button performStepButton;

    @FXML
    private Button openResButton;

    @FXML
    private TableView<EventOccurString> tableView;


    @FXML
    private TableColumn<EventOccurString, String> eventCol;

    @FXML
    private TableColumn<EventOccurString, String> actionCol;

    @FXML
    private TableColumn<EventOccurString, Double> timeCol;

    @FXML
    private TableColumn<EventOccurString, Integer> countEvCol;

    @FXML
    private TableColumn<EventOccurString, Integer> countDecCol;


    @FXML
    private TableView<buffTabbleString> bStateTable;

    @FXML
    private TableColumn<buffTabbleString, Integer> buffNum;

    @FXML
    private TableColumn<buffTabbleString, Double> cTimeCol;

    @FXML
    private TableColumn<buffTabbleString, Integer> ssourceCol;

    @FXML
    private TableColumn<buffTabbleString, Integer> reqNumberCol;


    @FXML
    private TableView<currentStateString> currectStateTabble;

    @FXML
    private TableColumn<currentStateString, String> ddevIceCol;

    @FXML
    private TableColumn<currentStateString, Double> c2TimeCol;


    @FXML
    private TableColumn<currentStateString, String> sstateCol;

    @FXML
    private TableColumn<currentStateString, String> requestInfoCol;


    @FXML
    void stepButtonClick(MouseEvent event) {
        if (run) {
            storage.addAll(Simulator.performStep());
            //ObservableList<eventOccurString> old=tableView.getItems();
            tableView.setItems(storage);
            ObservableList<buffTabbleString> tt = Simulator.getBufferState();
            bStateTable.setItems(tt);


            ObservableList<currentStateString> tt2 = Simulator.getCurrentState();
            currectStateTabble.setItems(tt2);
        } else {
            performStepButton.setVisible(false);
            openResButton.setVisible(true);
        }
    }

    @FXML
    void resOpenButtonClick(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/resultsForm.fxml"));

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

    public static ArrayList<ArrayList<Double>> getCoord(double left, double right, double step, int var, double number) {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());
        double ppav = 0;
        double peav = 0;
        double ptav = 0;
        int c = 0;
        for (double lamb = left; lamb <= right + 0.1 * step; lamb += step) {
            ppav = 0;
            peav = 0;
            ptav = 0;
            res.get(0).add(lamb);
            int k = 100;

            switch (var) {
                case 0:
                    Simulator.setAlpha(lamb);
                    Simulator.setBeta(lamb + number);
                    break;
                case 1:
                    Simulator.setAlpha(lamb - number);
                    Simulator.setBeta(lamb);
                    break;
                case 2:
                    Simulator.setLambda(lamb);
                    break;
            }
            for (int i = 0; i < k; i++) {
                int s = Controller.getS();
                int b = Controller.getB();
                int d = Controller.getD();
                int r = Controller.getR();
                boolean automode = Controller.isM();

                switch (var) {
                    case 3:
                        Simulator.setParameters((int) lamb, b, d, r, automode);
                        break;
                    case 4:
                        Simulator.setParameters(s, (int) lamb, d, r, automode);
                        break;
                    case 5:
                        Simulator.setParameters(s, b, (int) lamb, r, automode);
                        break;
                    default:
                        Simulator.setParameters(s, b, d, r, automode);
                }
                while (run) {
                    Simulator.performStep();
                }
                Simulator.getInfoSources();
                Simulator.getInfoDevices();
                Simulator.clear();
                ppav += Simulator.getPav();
                peav += Simulator.getEav();
                ptav += Simulator.getTav();
            }
            ppav /= k;
            peav /= k;
            ptav /= k;
            res.get(1).add(ppav);
            res.get(2).add(ptav);
            res.get(3).add(peav);
            System.out.println(lamb + "\t" + ppav + "\t" + ptav + "\t" + peav);
        }
        Simulator.setAlpha(Controller.getA());
        Simulator.setBeta(Controller.getBu());
        Simulator.setLambda(Controller.getLamb());
        return res;
    }

    public static void getCoord1() {
        try (FileOutputStream fout = new FileOutputStream("res.txt")) {


            double ppav = 0;
            double peav = 0;
            double ptav = 0;
            int c = 0;
            int k = 25;
            double a=1.5;


            for (int s = 4; s <= 20; s += 4) {
                for (int d = (int) ((double) 0.25 * s); d < (int) ((2 * s) + 2); d++) {
                    for (double llamb = 0.3; llamb <= 1.2; llamb += 0.002) {
                        //for (double a = 1.3; a <= 1.8; a += 0.02) {
                            double bb = a + 0.3;
                            Simulator.setLambda(llamb);
                            Simulator.setAlpha(a);
                            Simulator.setBeta(bb);
                            for (int i = 0; i < k; i++) {
                                Simulator.setParameters(s, Controller.getB(), d, Controller.getR(), Controller.isM());
                                while (run) {
                                    Simulator.performStep();
                                }
                                Simulator.getInfoSources();
                                Simulator.getInfoDevices();
                                Simulator.clear();
                                ppav += Simulator.getPav();
                                peav += Simulator.getEav();
                                ptav += Simulator.getTav();
                            }
                            ppav /= k;
                            peav /= k;
                            ptav /= k;
                            String x = s + "\t" + d + "\t" + llamb + "\t" + a + "\t" + bb + "\t" + ppav + "\t" + ptav + "\t" + peav;
                            if(ppav<0.15&&ptav<3.0&&peav>0.74)
                            {
                                fout.write((x+"\n").getBytes());
                            }
                            System.out.println(x);
                       // }
                    }
                }
            }

            Simulator.setAlpha(Controller.getA());
            Simulator.setBeta(Controller.getBu());
            Simulator.setLambda(Controller.getLamb());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

        //getCoord1();

        dds.clear();
        int s = Controller.getS();
        int b = Controller.getB();
        int d = Controller.getD();
        int r = Controller.getR();
        for (int i = 0; i < s; i++) {
            dds.add(new DispertionsInfoString(0.0, 0.0));
        }
        ArrayList<ObservableList<sourcesEfficiencyResultString>> ars = new ArrayList<>();
        openResButton.setVisible(false);
        boolean automode = Controller.isM();
        if (r == -1) {
            double pp = -1.0;
            int N = 100;
            Simulator.setParameters(s, b, d, N, automode);
            while (run) {
                Simulator.performStep();
            }
            Simulator.getInfoSources();
            Simulator.getInfoDevices();
            double pn = Simulator.getPav();
            while (Math.abs(pn - pp) > 0.05 * Math.abs(pp)) {
                pp = pn;
                //N+=164.3*1.643*((1-pn)/pn);
                N *= 10;
                Simulator.setParameters(s, b, d, N, automode);
                while (run) {
                    Simulator.performStep();
                }
                Simulator.getInfoSources();
                Simulator.getInfoDevices();
                Simulator.clear();
                pn = Simulator.getPav();
                System.out.println(pp + "   " + pn);
            }
            r = N;
            Controller.setR(r);
        }
        double[] aW = new double[s];
        double[] aP = new double[s];
        int N = 4;
        for (int i = 0; i < N; i++) {
            Simulator.setParameters(s, b, d, r, automode);
            while (run) {
                Simulator.performStep();
            }
            ars.add(Simulator.getInfoSources());
            for (int j = 0; j < s; j++) {
                aW[j] += ars.get(i).get(j).getTWait() / N;
                aP[j] += ars.get(i).get(j).getTProc() / N;
            }
            Simulator.getInfoDevices();
            Simulator.clear();
        }
        double[] dW = new double[s];
        double[] dP = new double[s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < N; j++) {
                double diff = ars.get(j).get(i).getTWait() - aW[i];
                dW[i] += (diff) * (diff) / N;
                diff = ars.get(j).get(i).getTProc() - aP[i];
                dP[i] += (diff) * (diff) / N;
            }
        }

        for (int i = 0; i < s; i++) {
            dds.get(i).setdStay(Math.sqrt(dW[i]));
            dds.get(i).setdProc(Math.sqrt(dP[i]));
        }

        Simulator.cc();

        //storage.addAll();
        Simulator.setParameters(s, b, d, r, automode);
        eventCol.setCellValueFactory(new PropertyValueFactory<>("event"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        countEvCol.setCellValueFactory(new PropertyValueFactory<>("countEvents"));
        countDecCol.setCellValueFactory(new PropertyValueFactory<>("countDeny"));
        tableView.setItems(storage);


        buffNum.setCellValueFactory(new PropertyValueFactory<>("bufferNum"));
        cTimeCol.setCellValueFactory(new PropertyValueFactory<>("currectTime"));
        ssourceCol.setCellValueFactory(new PropertyValueFactory<>("sourceNumber"));
        reqNumberCol.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));


        ddevIceCol.setCellValueFactory(new PropertyValueFactory<>("device"));
        c2TimeCol.setCellValueFactory(new PropertyValueFactory<>("currentTime"));
        sstateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        requestInfoCol.setCellValueFactory(new PropertyValueFactory<>("requestInfo"));


        if (automode) {
            performStepButton.setVisible(false);
            while (run) {
                storage.addAll(Simulator.performStep());
                //ObservableList<eventOccurString> old=tableView.getItems();
                tableView.setItems(storage);
            }
            openResButton.setVisible(true);
        }


    }

    public static void onStageClose() {
        Simulator.clear();
        //System.out.println("Ура! расходимся! =)");
    }


}
