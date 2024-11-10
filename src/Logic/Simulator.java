package Logic;

import Logic.ObjectClasses.Buffer;
import Logic.ObjectClasses.Device;
import Logic.ObjectClasses.Handler;
import Logic.ObjectClasses.Source;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gui.*;

import java.util.ArrayList;

//Тут всё происходит
public class Simulator {
    public static double getPav() {
        return pav;
    }

    private static double pav = 0.0;
    private static double eav = 0.0;
    private static  double tav=0.0;
    public static double systemTime = 0.0;      //системное время моделирования



    public static double alpha = 1.50;           //параметр alpha для времени генерации заявки



    public static double beta = 1.80;            //параметр beta для времени генерации заявки
    public static double lambda = 0.355;        //параметр lambda для экспоненциального закона распределения обработки прибора
    public static int countSources;             //количество источников в системе
    public static int countBuffers;             //количество буферов в системе
    public static int countDevices;             //количество приборов в системе
    public static int countRequests;            //количество моделируемых заявок
    public static boolean generateIsReady = false;      //Флаг окончания основного цикла моделирования
    public static boolean step = false;
    private static ArrayList<Device> devices;
    private static ArrayList<Source.Request> requestList;
    private static ArrayList<Buffer> buffers;
    private static ArrayList<Source> sources;
    private static int srn=0;
    private static int reqnum=0;
    private static Handler handler = new Handler();
    private static ObservableList<EventOccurString> res = FXCollections.observableArrayList();

    public static void add(EventOccurString str) {
        res.add(str);
    }

    public static ObservableList<currentStateString> getCurrentState()
    {
        ObservableList<currentStateString> res=FXCollections.observableArrayList();
        /*double diff=Double.NEGATIVE_INFINITY;
        int number=-1;
        for (int i=0;i< sources.size();i++)
        {
            if(Math.abs(sources.get(i).getPrevTime()-systemTime)>diff)
            {
                diff=Math.abs(sources.get(i).getPrevTime()-systemTime);
                number=i;
            }
        }*/
        for (int i=0;i< sources.size();i++)
        {
            if(i==srn)
            {
                res.add(new currentStateString("И"+(i+1),systemTime,"сгенерировал", ""+reqnum));//Todo починить
            }
            else
            {
                res.add(new currentStateString("И"+(i+1),systemTime,"генерирует", "-"));
            }

        }
        for (int i=0;i< devices.size();i++)
        {
            boolean caseq=devices.get(i).isEmpty();
            res.add(new currentStateString("П"+(i+1),systemTime,((caseq)?"ожидает":"занят"), ((caseq)?"-":devices.get(i).getRequest().getSourceNumber()+"."+devices.get(i).getRequest().getNumber())));
        }

        return res;
    }

    public static ObservableList<buffTabbleString> getBufferState()
    {
        ObservableList<buffTabbleString> res=FXCollections.observableArrayList();
        for (int i=0;i< buffers.size();i++)
        {
            boolean caseq=buffers.get(i).isEmpty();

            if(caseq)
            {
                res.add(new buffTabbleString((i+1),systemTime,0, 0));
            }
            else
            {
                res.add(new buffTabbleString((i+1),systemTime, buffers.get(i).getRequest().getSourceNumber(), buffers.get(i).getRequest().getNumber()));
            }

        }
        return res;
    }


    public static void cc()
    {
        if(!res.isEmpty()) {
            res.clear();
        }
    }
    public static void setBeta(double bet) {
        beta = bet;
    }
    public static void setLambda(double lamb)
    {
        lambda=lamb;
    }
    public static void setAlpha(double alph) {
        alpha = alph;
    }
    public static ObservableList<EventOccurString> setParameters(int a, int b, int c, int r, boolean m) {
        countSources = a;
        countBuffers = b;
        step = !m;
        countDevices = c;
        countRequests = r;

        sources = new ArrayList<>(countSources);
        for (int i = 0; i < countSources; i++) {
            sources.add(new Source(i + 1));
        }

        buffers = new ArrayList<>(countBuffers);
        for (int i = 0; i < countBuffers; i++) {
            buffers.add(new Buffer(i + 1));
        }

        devices = new ArrayList<>(countDevices);
        for (int i = 0; i < countDevices; i++) {
            devices.add(new Device(i + 1));
        }
        requestList = new ArrayList<>();

        for (Source s : sources)
            requestList.add(s.generate());

        handler = new Handler();
        EvCrFormController.setRun(true);
        return res;

    }

    public static ObservableList<EventOccurString> performStep() {
        res.clear();

        //Менеджер занимается постановкой заявок на прибор из буферов


        //Главный цикл программы
        if (Source.getCountAllRequest() == countRequests) {

            EvCrFormController.setRun(false);
            res.add(new EventOccurString("Конец моделирования",systemTime,"",0,0));
            if (countSources > 1) {
                double srTBP2 = -1;
                double srTBP1 = 1000;
                for (int i=1;i<sources.size();i++) {
                    double sss = sources.get(i).gettBP() / sources.get(i).getCountRequest();
                    if (sss > srTBP2) {
                        srTBP2 = sss;
                    }
                    if (sss < srTBP1) {
                        srTBP1 = sss;
                    }
                }

                sources.get(0).settBP(- sources.get(0).gettBP());
                sources.get(0).settBP((srTBP1+(srTBP2-srTBP1)*Math.random())*sources.get(0).getCountRequest()+Math.random()*0.02-0.01);
            }
            return res;
        }
        double minTime = requestList.get(0).gettGener();
        Source.Request newRequest = requestList.get(0);  //Новая заявка, с которой работаем
        int numberSource = requestList.get(0).getSourceNumber();   // номер источника, у которого сгенерировалась заявка
        int position = 0;

        for (int i = 0; i < requestList.size(); i++) {
            if (requestList.get(i).gettGener() < minTime) {
                minTime = requestList.get(i).gettGener();
                newRequest = requestList.get(i);
                numberSource = requestList.get(i).getSourceNumber();
                position = i;
            }
        }

        systemTime = newRequest.gettGener();  //Системное время фиксирует событие - Генерация заявки

        requestList.remove(position);
        //Удаляем самую раннюю сгенерировшаюся заявку

        requestList.add(sources.get(numberSource - 1).generate());  //Добавляем новую заявку из того же источника
        srn=-1;
        reqnum=-1;
        for (Buffer b : buffers) {          //Находим пустой буфер
            if (b.isEmpty()) {              //Если нашли, то добавляем туда новую заявку
                b.add(newRequest, numberSource);
                srn=b.getNumber()-1;
                reqnum=buffers.get(srn).getRequest().getNumber();
                srn=buffers.get(srn).getRequest().getSourceNumber()-1;
                systemTime = b.getTimeAdd();  //Системное вреям фиксирует событие - Добавлени в буфер
                break;
            }
        }





        //TODO перепроверить эту часть кода
        if (!newRequest.isInBuffer()) {    //Если заявка не была добавлена в буфер (все заняты)
            Simulator.add(new EventOccurString("И" + newRequest.getSourceNumber(), systemTime, "отказ", 0, 0));

            srn=newRequest.getSourceNumber()-1;
            reqnum=newRequest.getSourceNumber();

            newRequest.setInRefusal(true, numberSource);                  //Ставим статус "В отказ"
            sources.get(numberSource - 1).setCountRefusal();

            Source.Request.setCountRefusal();                                //Увеличиваем счётчик заявок в отказе

        }
        handler.toDevice(buffers, devices, requestList, sources);      //внутри происходит постановка заявки на прибор из буфера, если есть свободный прибор
        return res;
    }

    public static ObservableList<sourcesEfficiencyResultString> getInfoSources() {
        tav=0;
        ObservableList<sourcesEfficiencyResultString> res = FXCollections.observableArrayList();
        int numerator = 0;
        int denominator = 0;
        for (Source s : sources) {
            double srTOb = s.gettObc() / s.getCountRequest();
            double srTBP = s.gettBP() / s.getCountRequest();
            tav+=srTBP+srTOb;
            numerator += s.getCountRefusal();
            denominator += s.getCountRequest();
            res.add(new sourcesEfficiencyResultString("И" + s.getNumber(), s.getCountRequest(), (double) s.getCountRefusal() / s.getCountRequest(), srTOb + srTBP, srTBP, srTOb));
        }
        pav = (double) numerator / denominator;
        tav/=sources.size();
        return res;
    }

    public static ObservableList<DeviceEfficiencyResultString> getInfoDevices() {
        eav=0;
        ObservableList<DeviceEfficiencyResultString> res = FXCollections.observableArrayList();
        for (Device d : devices) {
            eav+=d.getTimeInDevice();
            res.add(new DeviceEfficiencyResultString("П" + d.getNumber(), d.getTimeInDevice() / systemTime));
        }
        eav/=devices.size()*systemTime;
        return res;
    }

    public static void clear()
    {
        for (Buffer b : buffers)
            b.clear();
        for (Device d : devices)
            d.clear();
        for (Source s : sources)
            s.clear();
    }

    public static double getEav() {
        return eav;
    }

    public static double getTav() {
        return tav;
    }
}
