package Logic.ObjectClasses;

import Logic.Simulator;
import gui.EventOccurString;

//источник
public class Source {
    private static int countAllRequest;         //количество всех сгенерированных заявок

    private int number;                        //номер источника
    private static  double alpha = Simulator.alpha;   // для равномерного закона распределения при генерации заявки
    private static  double beta = Simulator.beta;
    private double prevTime;               //время генерации предыдущей заявки
    private int countRequest = 0;          //количество заявок сгенерированных этим источником
    private int countRefusal = 0;          //количество заявок в отказе
    private double tObc = 0.0;            //Время обслуживания заявок данного источника
    private double tBP = 0.0;              //Время нахождения в буфере заявок данного источника

    public Source(int number) {
        prevTime = 0.0;
        this.number = number;
        alpha=Simulator.alpha;
        beta=Simulator.beta;
    }

    public Request generate() {
        double timeForGeneration = prevTime + (beta - alpha) * Math.random() + alpha;

        countRequest++;
        countAllRequest++;
        Request request = new Request(timeForGeneration, countRequest, number);
        prevTime = timeForGeneration;

        Simulator.add(new EventOccurString("И" + getNumber(), timeForGeneration, "генерация", countRequest, countRefusal));
        Simulator.add(new EventOccurString("И" + getNumber(), timeForGeneration, "ожидает", countRequest, countRefusal));

        return request;
    }

    public void clear() {
        this.number = 0;
        this.prevTime = 0;
        this.countRequest = 0;
        this.countRefusal = 0;
        this.tObc = 0;
        this.tBP = 0;
        countAllRequest = 0;
    }

    public double gettObc() {
        return tObc;
    }

    public void settObc(double tObc) {
        this.tObc += tObc;
    }

    public double gettBP() {
        return tBP;
    }

    public void settBP(double tBP) {
        this.tBP += tBP;
    }

    public static int getCountAllRequest() {
        return countAllRequest;
    }

    public int getNumber() {
        return number;
    }

    public double getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(double prevTime) {
        this.prevTime = prevTime;
    }

    public int getCountRequest() {
        return countRequest;
    }

    public int getCountRefusal() {
        return countRefusal;
    }

    public void setCountRefusal() {
        countRefusal++;
    }

    public static class Request {             //внутренний класс для заявки
        private static int countRefusal = 0;  //количество заявок, ушедших в отказ со всех источников

        private boolean inBuffer = false;     //сначал считаем, что все заявки попадают сначала в буфер, а потом на прибор
        private boolean inDevice = false;     //находится ли заявка на приборе
        private boolean inRefusal = false;    //ушла ли заявка в отказ

        private double tGener;                //время гинерации заявки
        private static int count = 0;         //количество всех заявок из этого источника
        private int number;                   //номер заявки
        private int sourceNumber;

        public Request(double tGener, int number, int sourceNumber) {
            this.tGener = tGener;
            this.number = number;
            this.sourceNumber = sourceNumber;
            count++;
        }

        public void setInRefusal(boolean inRefusal, int numberSource) {
            //System.out.println("Заявка " + numberSource + "." + number + " ушла в отказ");
            this.inRefusal = inRefusal;
        }

        public int getSourceNumber() {
            return sourceNumber;
        }

        public static int getCountRefusal() {
            return countRefusal;
        }

        public static void setCountRefusal() {
            Request.countRefusal++;
        }

        public static int getCount() {
            return count;
        }

        public static void setCount(int count) {
            Request.count = count;
        }

        public boolean isInBuffer() {
            return inBuffer;
        }

        public void setInBuffer(boolean inBuffer) {
            this.inBuffer = inBuffer;
        }

        public boolean isInDevice() {
            return inDevice;
        }

        public void setInDevice(boolean inDevice) {
            this.inDevice = inDevice;
        }

        public boolean isInRefusal() {
            return inRefusal;
        }

        public double gettGener() {
            return tGener;
        }

        public void settGener(double tGener) {
            this.tGener = tGener;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
