package infinitic.example.java.booking.tasks.flight;

public interface FlightBookingService {
    FlightBookingResult book(FlightBookingCart cart);

    void cancel(FlightBookingCart cart);
}