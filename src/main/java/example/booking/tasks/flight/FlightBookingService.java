package example.booking.tasks.flight;

public interface FlightBookingService {
    FlightBookingResult book(FlightBookingCart cart);

    void cancel(FlightBookingCart cart);
}