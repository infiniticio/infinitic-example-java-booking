package example.booking.workflows;

import example.booking.tasks.carRental.*;
import example.booking.tasks.flight.*;
import example.booking.tasks.hotel.*;
import io.infinitic.workflows.*;

import static io.infinitic.workflows.DeferredKt.and;

public class BookingWorkflowImpl extends Workflow implements BookingWorkflow {
    private final CarRentalService carRentalService = newTask(CarRentalService.class);
    private final FlightBookingService flightService = newTask(FlightBookingService.class);
    private final HotelBookingService hotelService = newTask(HotelBookingService.class);

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

        // wait for completion of all deferred
        and(carRental, flight, hotel).await();

        // wait and assign results
        CarRentalResult carRentalResult = carRental.await(); // assign result for CarRentalService::book
        FlightBookingResult flightResult = flight.await(); // assign result for FlightService::book method
        HotelBookingResult hotelResult = hotel.await(); // assign result for HotelService::book method

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
