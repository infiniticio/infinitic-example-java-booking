package example.booking.tasks.hotel;

import io.infinitic.tasks.Task;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class HotelBookingServiceFake extends Task implements HotelBookingService {
    @Override
    public HotelBookingResult book(HotelBookingCart cart) {
        // fake emulation of success/failure
        println("start hotel booking...");

        long r = (long) (Math.random() * 5000);

        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            println("hotel booking failed");
            return HotelBookingResult.FAILURE;
        }

        if (r >= 3000 ) {
            println("hotel booking threw exception!");
            throw new RuntimeException("failing request");
        }

        println("hotel booking succeeded");
        return HotelBookingResult.SUCCESS;
    }

    @Override
    public void cancel(HotelBookingCart cart) {
        println("hotel booking canceled");
    }

    // Exponential backoff retry strategy up to 6 attempts
    @Override
    public Duration getDurationBeforeRetry(@NotNull Exception e) {
        int n = context.getRetryIndex();
        if (n < 6) {
            return Duration.ofSeconds((long) (10 * Math.random() * Math.pow(2.0, n)));
        } else {
            return null;
        }
    }

    private void println(String msg) {
        System.out.println(context.getWorkflowId() + " - " + this.getClass().getSimpleName() + " - " + msg);
    }
}