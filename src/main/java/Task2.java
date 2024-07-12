import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private static final int n = 20;


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
    }

    private static void fizz(int i){
            if (i % 3 == 0 && i % 5 != 0) {
                System.out.print ("fizz"+(i == n ? ".":", "));
            }
    }
    private static void buzz(int i){
            if (i % 5 == 0 && i % 3 != 0) {
                System.out.print ("buzz"+(i == n ? ".":", "));
            }
    }
    private static void fizzbuzz(int i){
            if (i % 5 == 0 && i % 3 == 0) {
                System.out.print ("fizzbuzz"+(i == n ? ".":", "));
            }
    }
    private static void number(int i){
            if (i % 5 != 0 && i % 3 != 0) {
                System.out.print (i+(i == n ? ".":", "));
            }
    }

}
