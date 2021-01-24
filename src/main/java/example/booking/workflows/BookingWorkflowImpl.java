package example.booking.workflows;

import example.booking.tasks.carRental.CarRentalCart;
import example.booking.tasks.carRental.CarRentalResult;
import example.booking.tasks.carRental.CarRentalService;
import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.flight.FlightBookingResult;
import example.booking.tasks.flight.FlightBookingService;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.tasks.hotel.HotelBookingResult;
import example.booking.tasks.hotel.HotelBookingService;
import io.infinitic.workflows.*;

public class BookingWorkflowImpl extends AbstractWorkflow implements BookingWorkflow {
    private final CarRentalService carRentalService = task(CarRentalService.class);
    private final FlightBookingService flightService = task(FlightBookingService.class);
    private final HotelBookingService hotelService = task(HotelBookingService.class);

    @Override
    public BookingResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    ) {
        // parallel bookings using car rental, flight and hotel services

        Deferred<CarRentalResult> carRental = async(carRentalService, t -> t.book(carRentalCart));
        Deferred<FlightBookingResult> flight = async(flightService, t -> t.book(flightCart));
        Deferred<HotelBookingResult> hotel = async(hotelService, t -> t.book(hotelCart));

        // wait and assign results
        CarRentalResult carRentalResult = carRental.result(); // wait and assign result for CarRentalService::book
        FlightBookingResult flightResult = flight.result(); // wait and assign result for FlightService::book method
        HotelBookingResult hotelResult = hotel.result(); // wait and assign result for HotelService::book method

        // if at least one of the booking is failed than cancel all successful bookings
        if (carRentalResult == CarRentalResult.FAILURE ||
            flightResult == FlightBookingResult.FAILURE ||
            hotelResult == HotelBookingResult.FAILURE
        ) {
            if (carRentalResult == CarRentalResult.SUCCESS) { carRentalService.cancel(carRentalCart); }
            if (flightResult == FlightBookingResult.SUCCESS) { flightService.cancel(flightCart); }
            if (hotelResult == HotelBookingResult.SUCCESS) { hotelService.cancel(hotelCart); }

            inline(() -> println("book canceled"));
            return BookingResult.FAILURE;
        }

        // everything went fine
        inline(() -> println("book succeeded"));

        return BookingResult.SUCCESS;
    }

    private Object println(String msg) {
        System.out.println(this.getClass().getSimpleName() + ": " + msg);
        return null;
    }
}
