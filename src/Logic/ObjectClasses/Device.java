package Logic.ObjectClasses;

import Logic.Simulator;
import gui.EventOccurString;

//прибор
public class Device {
    private static int countRequest = 0;  //количество заявок обработанных всеми приборами
    private static double allTime;       //время обработки всех заявок всеми приборами
    private static double lambda = Simulator.lambda;

    private Source.Request request;     //заявка, которая находится на приборе
    private int number;                 //номер прибора
    private double timeEmpty;           //время простоя (сумма timeAdd текущей заявки - timeOut предыдущей заявки
    private double timeAdd;             //время поступения заявки
    private double timeOut;             //время ухода из прибора
    private double timeInDevice;        //время обработки заявок этим прибором
    private double timeToTreatment;     //время, которое нужно на обработку
    private double tForSource;
    private int numberSource;
    private int countRequestThis;

    public Device(int number) {
        this.number = number;
        lambda = Simulator.lambda;
    }

    public void add(Source.Request request, int numberSource) {
        this.request = request;
        this.numberSource = numberSource;
        request.setInDevice(true);
        timeAdd = Simulator.systemTime;
        timeEmpty = timeAdd - timeOut;

        Simulator.add(new EventOccurString("П" + getNumber(), timeAdd, "поступление", 0, 0));//Todo Поменять на string

        treatment();
    }

    public void delete() {
        if (request != null)
            Simulator.add(new EventOccurString("П" + getNumber(), timeAdd, "удаление " + numberSource + " . " + request.getNumber(), 0, 0));

        Simulator.add(new EventOccurString("П" + getNumber(), timeAdd, "ожидает", 0, 0));

        timeOut = Simulator.systemTime;
        timeInDevice += timeOut - timeAdd;
        allTime += timeOut - timeAdd;
        tForSource = timeOut - timeAdd;
        countRequest++;
        countRequestThis++;
        request.setInDevice(false);
        request = null;
        numberSource = 0;
    }

    public void clear() {
        this.request = null;
        this.number = 0;
        this.timeEmpty = 0;
        this.timeAdd = 0;
        this.timeOut = 0;
        this.timeInDevice = 0;
        this.timeToTreatment = 0;
        this.tForSource = 0;
        this.numberSource = 0;
        this.countRequestThis = 0;
        countRequest = 0;
        allTime = 0;
    }

    public boolean isEmpty() {
        return request == null;   //находится ли прибор в простое
    }

    /* Обработка заявки: вычисляем время, когда прибор должен закончить обработку */
    public void treatment() {
        timeToTreatment = timeAdd + Math.log(1 - Math.random()) / (-lambda);
    }

    public double gettForSource() {
        return tForSource;
    }

    public int getNumberSource() {
        return numberSource;
    }

    public double getTimeToTreatment() {
        return timeToTreatment;
    }

    public void setTimeToTreatment(double timeToTreatment) {
        this.timeToTreatment = timeToTreatment;
    }

    public static int getCountRequest() {
        return countRequest;
    }

    public static void setCountRequest(int countRequest) {
        Device.countRequest = countRequest;
    }

    public static double getAllTime() {
        return allTime;
    }

    public static void setAllTime(double allTime) {
        Device.allTime = allTime;
    }

    public Source.Request getRequest() {
        return request;
    }

    public void setRequest(Source.Request request) {
        this.request = request;
    }

    public int getNumber() {
        return number;
    }

    public int getCountRequestThis() {
        return countRequestThis;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTimeEmpty() {
        return timeEmpty;
    }

    public void setTimeEmpty(double timeEmpty) {
        this.timeEmpty = timeEmpty;
    }

    public double getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(double timeAdd) {
        this.timeAdd = timeAdd;
    }

    public double getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(double timeOut) {
        this.timeOut = timeOut;
    }

    public double getTimeInDevice() {
        return timeInDevice;
    }

    public void setTimeInDevice(double timeInDevice) {
        this.timeInDevice = timeInDevice;
    }
}
