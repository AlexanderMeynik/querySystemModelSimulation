package Logic.ObjectClasses;

import Logic.Simulator;
import gui.EventOccurString;

//буфер
public class Buffer {
    private static double allTime;         //время нахождения всех заявок во всех буферах
    private static int countRequest;      //количество заявок в буферах

    private Source.Request request;       //заявка в буфере
    private int numberSource;             //номер источника из которого появилась заявка (нужно для приоритета)
    private double timeAdd;               //время заявки в момент поступления;
    private double timeOut;              //воемя выхода заявки из буфера;
    private double tForSource;
    private int number;                  //номер буфера

    public Buffer(int number) {
        this.number = number;
    }

    public void add(Source.Request request, int numberSource) {
        this.request = request;
        this.numberSource = numberSource;
        timeAdd = Simulator.systemTime;
        request.setInBuffer(true);
        countRequest++;

        Simulator.add(new EventOccurString("Б" + getNumber(), timeAdd, "поступление " + numberSource + " . " + request.getNumber(), 0, 0));//Todo Поменять на string

    }

    public void delete() {

        Simulator.add(new EventOccurString("Б" + getNumber(), timeAdd, "удаление " + numberSource + " . " + request.getNumber(), 0, 0));//Todo Поменять на string

        Simulator.add(new EventOccurString("Б" + getNumber(), timeAdd, "ожидает", 0, 0));//Todo Поменять на string

        request.setInBuffer(false);
        timeOut = Simulator.systemTime;
        allTime += timeOut - timeAdd;
        tForSource = timeOut - timeAdd;
        request = null;
    }

    public boolean isEmpty() {
        return request == null;
    }

    public double getForSource() {
        return tForSource;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void clear() {
        request = null;
        numberSource = 0;
        timeAdd = 0;
        timeOut = 0;
        number = 0;
        allTime = 0;
        countRequest = 0;
        tForSource = 0;
    }

    public static int getCountRequest() {
        return countRequest;
    }

    public static double getAllTime() {
        return allTime;
    }

    public static void setAllTime(double allTime) {
        Buffer.allTime = allTime;
    }

    public Source.Request getRequest() {
        return request;
    }

    public void setRequest(Source.Request request) {
        this.request = request;
    }

    public int getNumberSource() {
        return numberSource;
    }

    public void setNumberSource(int numberSource) {
        this.numberSource = numberSource;
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
}
