package Logic.ObjectClasses;

import Logic.Simulator;

import java.util.ArrayList;

public class Handler {
    private static int lastDevice = 0;

    public void toDevice(ArrayList<Buffer> buffers, ArrayList<Device> devices,
                         ArrayList<Source.Request> requestList, ArrayList<Source> sources) {
        boolean isEmptyAnyone = false; //Проверяем, есть ли свободный прибор
        //Т.К. выборка по приоритету, то смотрим c последнего прибора, до начала
        for (int i = devices.size() - 1; i >= 0; i--) {
            if (devices.get(i).isEmpty()) {
                isEmptyAnyone = true;                    //Если нашли свободный, меняем флаг и
                lastDevice = devices.get(i).getNumber(); //Запоминаем его номер
                break;
            }
        }


        //Если нашли свободный прибор, то кидаем туда выбранную из буфера заявку, если свободных приборов нет, то ничего не делаем
        if (isEmptyAnyone) {
            //Проверяем есть ли у нас заявки в буферах, если есть, то кидаем их на приборы, если нет, то ничего не делаем
            boolean isBuffersEmpty = true;  //Если все буферы пустые
            for (Buffer buffer : buffers) {
                if (!buffer.isEmpty())
                    isBuffersEmpty = false;
            }

            if (!isBuffersEmpty) {
                Source.Request req = null;

                int numberSourse = -1;//Botv

                int numberRequest = Simulator.countRequests;   //Максимальный номер заявки

                //Находим заявку от источника с самым высоким приоритетом, если таких заявок несколько, то выбираем
                //самую раннюю (наименьший номер) и запоминаем ссылку на неё

                for (Buffer buffer : buffers) {
                    if (buffer.getNumberSource() > numberSourse && buffer.getNumberSource() != 0)
                        numberSourse = buffer.getNumberSource();
                }

                int numberBuffer = 0; //индекс буфера из которого забрали заявку (далее сдвигаем все заявки влево)
                for (int i = 0; i < buffers.size(); i++) {
                    if (buffers.get(i).getNumberSource() == numberSourse && buffers.get(i).getRequest().getNumber() < numberRequest
                            && buffers.get(i).getRequest().getNumber() != 0) {
                        numberRequest = buffers.get(i).getRequest().getNumber();
                        req = buffers.get(i).getRequest();
                        numberBuffer = i;
                    }
                }
                buffers.get(numberBuffer).delete(); //удаляем найденую наявку из буфера
                //Добавляем информацию о нахождении заявки из нужного источника
                sources.get(numberSourse - 1).settBP(buffers.get(numberBuffer).getForSource());

                Simulator.systemTime = buffers.get(numberBuffer).getTimeOut(); //Фисксируем время удаления

                //Если нашли заявку, т.е. буферы НЕ пустые, то отправляем в свободный прибор на обработку
                if (req != null) {
                    devices.get(lastDevice - 1).add(req, numberSourse);
                    Simulator.systemTime = devices.get(lastDevice - 1).getTimeAdd(); //Фиксируем время поступления заявки на прибор
                }


                //очищаем последний, чтобы он стал пустым и устанавливаем его номер
                buffers.get(numberBuffer).clear();
                buffers.get(numberBuffer).setNumber(numberBuffer + 1);


            }
        }


        //Проверяем есть ли прибор, который закончит обработку заявки раньше,
        //чем сгенерировалась самая ранняя из заявок, находящихся в листе
        Device device = devices.get(0);
        for (Device d : devices) {
            if (!d.isEmpty()) {
                device = d;
                break;
            }
        }

        double minTr = device.getTimeToTreatment();
        int numberDevice = device.getNumber();
        for (Device d : devices) {
            if (d.getTimeToTreatment() < minTr && !d.isEmpty()) {
                minTr = d.getTimeToTreatment();
                numberDevice = d.getNumber();
            }
        }

        double minGr = requestList.get(0).gettGener();
        for (Source.Request req : requestList) {
            if (req.gettGener() < minGr)
                minGr = req.gettGener();
        }


        //Если нашли такой прибор, то фиксируем системное время и удаляем заявку из прибора
        if (minTr < minGr) {
            Simulator.systemTime = minTr;  //Фиксируем событие - Завершение обработки и удаление заявки из прибора.
            int numberSource = device.getNumberSource();
            devices.get(numberDevice - 1).delete();
            sources.get(numberSource - 1).settObc(devices.get(numberDevice - 1).gettForSource());
        }

        if (Simulator.generateIsReady) {
            boolean isAllDevicesEmpty = false;
            int count = 0;
            for (Device d : devices) {
                if (d.isEmpty())
                    count++;
            }
            if (count == devices.size())
                isAllDevicesEmpty = true;

            if (!isAllDevicesEmpty) {
                Simulator.systemTime = minTr;      //Фиксируем событие - Завершение обработки и удаление заявки из прибора.
                int numberSource = device.getNumberSource();
                devices.get(numberDevice - 1).delete();
                sources.get(numberSource - 1).settObc(devices.get(numberDevice - 1).gettForSource());
            }
        }
    }
}
