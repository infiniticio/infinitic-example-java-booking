package infinitic.example.java.booking.tasks.flight;

public class FlightBookingServiceFake implements FlightBookingService {
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

        if (r >= 3000 ) {
            println(cart, "exception! (retry in " + getRetryDelay() + " seconds)");
            throw new RuntimeException("failing request");
        }

        println(cart, "succeeded");
        return FlightBookingResult.SUCCESS;
    }

    @Override
    public void cancel(FlightBookingCart cart) {
        println(cart, "canceled");
    }

    public Float getRetryDelay() {
        return 5F;
    }

    private void println(FlightBookingCart cart, String msg) {
        System.out.println(this.getClass().getSimpleName() + "     (" + cart.getCartId() + "): " + msg);
    }
}