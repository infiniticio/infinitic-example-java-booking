package example.booking.tasks.carRental;

import io.infinitic.tasks.Task;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class CarRentalServiceFake extends Task implements CarRentalService {
    @Override
    public CarRentalResult book(CarRentalCart cart) {
        // fake emulation of success/failure
        println("start car rental ...");

        long r = (long) (Math.random() * 5000);
        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            println("car rental failed");
            return CarRentalResult.FAILURE;
        }

        if (r >= 3000 ) {
            println("car rental threw exception!");
            throw new RuntimeException("failing request");
        }

        println("car rental succeeded");
        return CarRentalResult.SUCCESS;
    }

    @Override
    public void cancel(CarRentalCart cart) {
        println("car rental canceled");
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
