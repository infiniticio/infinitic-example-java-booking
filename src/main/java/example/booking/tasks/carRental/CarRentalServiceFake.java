package example.booking.tasks.carRental;

import io.infinitic.tasks.Task;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class CarRentalServiceFake extends Task implements CarRentalService {
    @Override
    public CarRentalResult book(CarRentalCart cart) {
        // fake emulation of success/failure
        println(cart, "booking ...");

        long r = (long) (Math.random() * 5000);
        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            println(cart, "failed");
            return CarRentalResult.FAILURE;
        }

        if (r >= 3000 ) {
            println(cart, "exception!");
            throw new RuntimeException("failing request");
        }

        println(cart, "succeeded");
        return CarRentalResult.SUCCESS;
    }

    @Override
    public void cancel(CarRentalCart cart) {
        println(cart, "canceled");
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

    private void println(CarRentalCart cart, String msg) {
        System.out.println(this.getClass().getSimpleName() + "     (" + cart.getCartId() + "): " + msg);
    }
}
