package example.booking;

import example.booking.tasks.carRental.CarRentalCart;
import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.workflows.BookingWorkflow;
import io.infinitic.common.clients.InfiniticClient;
import io.infinitic.factory.InfiniticClientFactory;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        try(InfiniticClient client = InfiniticClientFactory.fromConfigFile("configs/infinitic.yml")) {
            // create a stub for BookingWorkflow
            BookingWorkflow bookingWorkflow = client.newWorkflow(BookingWorkflow.class);

            int i = 0;
            while (i < 10) {
                String strI = String.valueOf(i);
                // faking some carts
                CarRentalCart carRentalCart = new CarRentalCart();
                FlightBookingCart flightCart = new FlightBookingCart();
                HotelBookingCart hotelCart = new HotelBookingCart();

                // dispatch workflow
                client.dispatchAsync(bookingWorkflow::book, carRentalCart, flightCart, hotelCart)
                        .thenApply( (deferred) ->  {
                            System.out.println("Workflow " + BookingWorkflow.class.getName() + " " + deferred.getId() + " (" + strI + ") dispatched!");

                            return null;
                        })
                        .exceptionally( (error) -> {
                            System.err.println("Failed to dispatch (" + strI + "): "  + error.toString());

                            return null;
                        });
                i++;
            }
        }
    }
}