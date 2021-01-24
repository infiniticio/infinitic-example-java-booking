package example.booking.workflows;

import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.tasks.carRental.CarRentalCart;
import io.infinitic.workflows.Workflow;

public interface BookingWorkflow extends Workflow {
    BookingResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    );
}
