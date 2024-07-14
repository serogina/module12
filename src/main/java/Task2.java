import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Task2 {
    private static final int n = 20;
    private static final Queue<String> outputQueue = new LinkedList<>();


    public static void main(String[] args) {
        ProcessThread threadFizzBuzz = new ProcessThread(Task2::fizzbuzz);
        ProcessThread threadFizz = new ProcessThread(Task2::fizz);
        ProcessThread threadBuzz = new ProcessThread(Task2::buzz);
        ProcessThread threadNumber = new ProcessThread(Task2::number);

        List<ProcessThread> threads = new ArrayList<>();
        threads.add(threadFizzBuzz);
        threads.add(threadBuzz);
        threads.add(threadFizz);
        threads.add(threadNumber);

        for (ProcessThread thread : threads) {
            thread.start();
        }

        for (int i = 1; i <= n; i++) {
            for (ProcessThread thread : threads) {
                thread.process(i);
            }
            while (true) {
                int processedCount = 0;
                for (ProcessThread thread : threads) {
                    if (thread.isProcessed()) {
                        processedCount++;
                    }
                }
                if (processedCount == threads.size()) {
                    break;
                }
            }
        }

        for (ProcessThread thread : threads) {
            thread.terminate();
        }

        while (!outputQueue.isEmpty()) {
            synchronized (outputQueue) {
                System.out.print(outputQueue.poll() + (outputQueue.isEmpty() ? "." : ", "));
            }
        }
    }

    private static void fizz(int i){
            if (i % 3 == 0 && i % 5 != 0) {
                addToQueue("fizz");
            }
    }
    private static void buzz(int i){
            if (i % 5 == 0 && i % 3 != 0) {
                addToQueue("buzz");
            }
    }
    private static void fizzbuzz(int i){
            if (i % 5 == 0 && i % 3 == 0) {
                addToQueue("fizzbuzz");
            }
    }
    private static void number(int i){
            if (i % 5 != 0 && i % 3 != 0) {
                addToQueue(String.valueOf(i));
            }
    }
    private static void addToQueue(String output) {
        synchronized (outputQueue) {
            outputQueue.add(output);
        }
    }

}
