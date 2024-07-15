import java.util.*;

public class Task2 {
    private static final int n = 20000;
    private static int i = 1;
    private static final Object lock = new Object();
    private static final Queue<String> outputQueue = new LinkedList<>();


    public static void main(String[] args) {
        Thread threadFizzBuzz = new Thread(Task2::fizzbuzz);
        Thread threadFizz = new Thread(Task2::fizz);
        Thread threadBuzz = new Thread(Task2::buzz);
        Thread threadNumber = new Thread(Task2::number);

        List<Thread> threads = new ArrayList<>();
        threads.add(threadFizzBuzz);
        threads.add(threadBuzz);
        threads.add(threadFizz);
        threads.add(threadNumber);

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void fizz() {
        while (true) {
            synchronized (lock) {
                while (i <= n && (i % 3 != 0 || i % 5 == 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (i > n) break;
                addToQueue("fizz");
                i++;
                lock.notifyAll();
            }
        }
    }

    private static void buzz() {
        while (true) {
            synchronized (lock) {
                while (i <= n && (i % 5 != 0 || i % 3 == 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (i > n) break;
                addToQueue("buzz");
                i++;
                lock.notifyAll();
            }
        }
    }

    private static void fizzbuzz() {
        while (true) {
            synchronized (lock) {
                while (i <= n && (i % 15 != 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (i > n) break;
                addToQueue("fizzbuzz");
                i++;
                lock.notifyAll();
            }
        }
    }

    private static void number() {
            synchronized (lock) {
                while (i <= n){
                    while (i <= n && outputQueue.isEmpty() && (i % 3 ==0 || i % 5 == 0)) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (i > n && outputQueue.isEmpty()) break;

                    String value = outputQueue.poll();
                        if (value == null) {
                            System.out.print(i + (i >= n ? "." : ", "));
                            i++;
                        } else {
                            System.out.print(value + (i >= n ? "." : ", "));
                        }
                        lock.notifyAll();
                }
            }
    }

    private static void addToQueue(String output) {
        synchronized (outputQueue) {
            outputQueue.add(output);
        }
    }
}
