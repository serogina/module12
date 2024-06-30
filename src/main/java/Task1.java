import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task1 {
    public static void main(String[] args) {
        Instant StartTime = Instant.now();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(
                ()-> printTime(StartTime),
                0,
                1,
                TimeUnit.SECONDS);
        ScheduledExecutorService scheduledExecutorService1 = Executors.newScheduledThreadPool(1);
        scheduledExecutorService1.scheduleAtFixedRate(
                ()-> System.out.println("Пройшло 5 секунд"),
                5,
                5,
                TimeUnit.SECONDS);

    }
    static private void printTime(Instant startTime){
        Duration elapsed = Duration.between(startTime, Instant.now());
        System.out.println(elapsed.toSeconds());
    }

}
