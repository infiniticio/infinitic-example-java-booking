package example.booking.tasks.hotel;

import io.infinitic.tasks.Task;
import java.time.Duration;

public class HotelBookingServiceFake extends Task implements HotelBookingService {
    @Override
    public HotelBookingResult book(HotelBookingCart cart) {
        // fake emulation of success/failure
        println(cart, "booking...");

        long r = (long) (Math.random() * 5000);

        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            println(cart, "failed");
            return HotelBookingResult.FAILURE;
        }

        // uncomment lines below to test task retries
//        if (r >= 3000 ) {
//            println(cart, "exception! (retry in " + getRetryDelay() + " seconds)");
//            throw new RuntimeException("failing request");
//        }

        println(cart, "succeeded");
        return HotelBookingResult.SUCCESS;
    }

    @Override
    public void cancel(HotelBookingCart cart) {
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

    private void println(HotelBookingCart cart, String msg) {
        System.out.println(this.getClass().getSimpleName() + "     (" + cart.getCartId() + "): " + msg);
    }
}