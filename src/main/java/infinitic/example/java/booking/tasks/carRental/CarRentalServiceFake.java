package infinitic.example.java.booking.tasks.carRental;

public class CarRentalServiceFake implements CarRentalService {
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
            println(cart, "exception! (retry in " + getRetryDelay() + " seconds)");
            throw new RuntimeException("failing request");
        }

        println(cart, "succeeded");
        return CarRentalResult.SUCCESS;
    }

    @Override
    public void cancel(CarRentalCart cart) {
        println(cart, "canceled");
    }

    public Float getRetryDelay() {
        return 5F;
    }

    private void println(CarRentalCart cart, String msg) {
        System.out.println(this.getClass().getSimpleName() + "     (" + cart.getCartId() + "): " + msg);
    }
}
