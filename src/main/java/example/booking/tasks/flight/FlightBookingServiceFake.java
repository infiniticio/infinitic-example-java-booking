package example.booking.tasks.flight;

import io.infinitic.tasks.Task;
import java.time.Duration;

public class FlightBookingServiceFake extends Task implements FlightBookingService {
    @Override
    public FlightBookingResult book(FlightBookingCart cart) {
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
            return FlightBookingResult.FAILURE;
        }

        // uncomment lines below to test task retries
//        if (r >= 3000 ) {
//            println(cart, "exception! (retry in " + getRetryDelay() + " seconds)");
//            throw new RuntimeException("failing request");
//        }

        println(cart, "succeeded");
        return FlightBookingResult.SUCCESS;
    }

    @Override
    public void cancel(FlightBookingCart cart) {
        println(cart, "canceled");
    }

    // Exponential backoff retry strategy up to 6 attempts
    @Override
    public Duration getDurationBeforeRetry(Exception e) {
        int n = context.getRetryIndex();
        if (n < 6) {
            return Duration.ofSeconds((long) (10 * Math.random() * Math.pow(2.0, n)));
        } else {
            return null;
        }
    }

    private void println(FlightBookingCart cart, String msg) {
        System.out.println(this.getClass().getSimpleName() + "     (" + cart.getCartId() + "): " + msg);
    }
}