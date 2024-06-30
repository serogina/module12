import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task2 {
    private static final int n = 20;
    private static List<String> numbers = new ArrayList<>(n);

    public static void main(String[] args) {
        setStringNumbers();
        System.out.println(String.join(", ", numbers) + ".");
    }

    private static void setStringNumbers(){
        for (int i = 0; i < n; i++) {
            numbers.add(""+(i+1));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);
            executorService.execute(() -> fizz());
            executorService.execute(() -> buzz());
            executorService.execute(() -> fizzbuzz());
            executorService.execute(() -> number());

        executorService.shutdown();
    }
    private static void fizz(){
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                synchronized (numbers) {
                    numbers.set(i-1, "fizz");
                }
            }
        }
    }
    private static void buzz(){
        for (int i = 1; i <= n; i++) {
            if (i % 5 == 0 && i % 3 != 0) {
                synchronized (numbers) {
                    numbers.set(i-1, "buzz");
                }
            }
        }
    }
    private static void fizzbuzz(){
        for (int i = 1; i <= n; i++) {
            if (i % 5 == 0 && i % 3 == 0) {
                synchronized (numbers) {
                    numbers.set(i-1, "fizzbuzz");
                }
            }
        }
    }
    private static void number(){
        for (int i = 1; i <= n; i++) {
            if (i % 5 != 0 && i % 3 != 0) {
                synchronized (numbers) {
                    numbers.set(i-1, ""+(i-1));
                }
            }
        }
    }

}
