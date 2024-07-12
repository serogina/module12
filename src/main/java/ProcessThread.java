import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

class ProcessThread extends Thread {
    private int number;
    private final AtomicBoolean processed = new AtomicBoolean(true);
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Consumer<Integer> consumer;

    public ProcessThread(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }

    public synchronized void process(int number) {
        this.number = number;
        processed.set(false);
        notify();
    }

    public synchronized boolean isProcessed() {
        return processed.get();
    }

    public void terminate() {
        running.set(false);
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        while (running.get()) {
            synchronized (this) {
                try {
                    while (processed.get() && running.get()) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (!running.get()) {
                    break;
                }

                consumer.accept(number);
                processed.set(true);

                synchronized (Task2.class) {
                    Task2.class.notify();
                }
            }
        }
    }
}