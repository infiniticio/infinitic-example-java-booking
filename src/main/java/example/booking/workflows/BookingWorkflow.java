package example.booking.workflows;

import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.tasks.carRental.CarRentalCart;

public interface BookingWorkflow {
    BookingResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    );
}
