package infinitic.example.java.booking.tasks.carRental;

public interface CarRentalService {
    CarRentalResult book(CarRentalCart cart);

    void cancel(CarRentalCart cart);
}
