package example.booking.tasks.flight;

import io.infinitic.tasks.Task;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class FlightBookingServiceFake extends Task implements FlightBookingService {
    @Override
    public FlightBookingResult book(FlightBookingCart cart) {
        // fake emulation of success/failure
        println("start flight booking ...");

        long r = (long) (Math.random() * 5000);
        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            println("flight booking failed");
            return FlightBookingResult.FAILURE;
        }

        if (r >= 3000 ) {
            println("flight booking threw exception!");
            throw new RuntimeException("failing request");
        }

        println("flight booking succeeded");
        return FlightBookingResult.SUCCESS;
    }

    @Override
    public void cancel(FlightBookingCart cart) {
        println("flight booking canceled");
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