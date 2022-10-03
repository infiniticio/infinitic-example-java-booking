package example.booking.tasks.flight;

import io.infinitic.annotations.Name;

@Name(name = "FlightBooking")
public interface FlightBookingService {
    FlightBookingResult book(FlightBookingCart cart);

    void cancel(FlightBookingCart cart);
}