package example.booking;

import example.booking.tasks.carRental.CarRentalCart;
import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.workflows.BookingResult;
import example.booking.workflows.BookingWorkflow;
import io.infinitic.pulsar.InfiniticClient;
import java.util.UUID;

public class Client {
    public static void main(String[] args) {
        // instantiate Infinitic client based on infinitic.yml config file
        InfiniticClient client = InfiniticClient.fromConfigFile("configs/infinitic.yml");

        // faking some carts
        CarRentalCart carRentalCart = new CarRentalCart(getId());
        FlightBookingCart flightCart = new FlightBookingCart(getId());
        HotelBookingCart hotelCart = new HotelBookingCart(getId());

        // create a stub for BookingWorkflow
        BookingWorkflow bookingWorkflow = client.workflow(BookingWorkflow.class);

        // dispatch workflow
        client.async(
            bookingWorkflow,
            w -> w.book(carRentalCart, flightCart, hotelCart)
        );

        // dispatch workflow and get result
        BookingResult result = bookingWorkflow.book(carRentalCart, flightCart, hotelCart);

        System.out.println(result);

        // closing underlying PulsarClient
        client.close();
    }

    private static String getId() {
        return UUID.randomUUID().toString();
    }
}