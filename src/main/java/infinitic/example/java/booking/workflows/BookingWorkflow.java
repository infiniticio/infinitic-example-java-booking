package infinitic.example.java.booking.workflows;

import infinitic.example.java.booking.tasks.carRental.CarRentalCart;
import infinitic.example.java.booking.tasks.flight.FlightBookingCart;
import infinitic.example.java.booking.tasks.hotel.HotelBookingCart;
import io.infinitic.workflows.Workflow;

public interface BookingWorkflow extends Workflow {
    BookingResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    );
}
