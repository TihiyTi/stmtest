package com.ti.data;

import com.google.common.io.Closeables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RealTimeQueueCreater {
    private ConcurrentLinkedQueue<Number> queue;
    private BlockingQueue<Number> blockingQueue;
    private int i = 0;

    public RealTimeQueueCreater(ConcurrentLinkedQueue<Number> queue1){
        queue = queue1;
    }
    public RealTimeQueueCreater(){}

    public ConcurrentLinkedQueue<Number> autoFillingQueue() {
        if(queue == null){
            queue = new ConcurrentLinkedQueue<Number>();
        }
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                queue.add(i % 100.);
                i++;
            }
        }, 0L, 20L, TimeUnit.MILLISECONDS);
        return queue;
    }

    public BlockingQueue<Number> autoFillingBlockingQueue(BlockingQueue<Number> blQueue){
        if(blockingQueue == null){
            blockingQueue = blQueue;
        }
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                blockingQueue.add(i % 100.);
                i++;
            }
        }, 0L, 10L, TimeUnit.MICROSECONDS);
        return blockingQueue;
    }

    public BlockingQueue<Number> fromTxtBlockingQueue(BlockingQueue<Number> blQueue){
        if(blockingQueue == null){
            blockingQueue = blQueue;
        }
        final List<Double> list = readFile();
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                blockingQueue.add(2*list.get(i));
                if(i > (list.size()-20)){
                    i=0;
                }else{
                    i=i+1;
                }
            }
        }, 0L, 10L, TimeUnit.MILLISECONDS);
        return blockingQueue;
    }

    public static List<Double> readFile(){
        List<Double> xList = new ArrayList<Double>(); // создание объекта списка xList
        BufferedReader reader = null; // буффер для прочтения
        System.out.println("Read");
        try {
            System.out.println("Read almost");
            reader = new BufferedReader(new FileReader("first.txt")); // загрузка файла в буфер
            System.out.println("Read complete");
            Scanner scanner = new Scanner(reader); // создание переменной для чтения строк из файла
            while (scanner.hasNextLine()) {
                xList.add(Double.valueOf(scanner.nextLine().trim())); // запись строки в i-ую ячейку списка
            }
            System.out.println("Read end?");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                Closeables.close(reader, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Read end!!!");
        return xList; // возвращает список данных, записанных из файла
    }

}
