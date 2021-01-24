package example.booking;

import example.booking.tasks.carRental.CarRentalCart;
import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.workflows.BookingWorkflow;
import io.infinitic.pulsar.InfiniticClient;
import java.util.UUID;

public class Client {
    public static void main(String[] args) {
        // instantiate Infinitic client based on infinitic.yml config file
        InfiniticClient client = InfiniticClient.fromFile("configs/infinitic.yml");

        // faking some carts
        CarRentalCart carRentalCart = new CarRentalCart(getId());
        FlightBookingCart flightCart = new FlightBookingCart(getId());
        HotelBookingCart hotelCart = new HotelBookingCart(getId());

        // starting a workflow
        client.startWorkflowAsync(
                BookingWorkflow.class,
                w -> w.book(carRentalCart, flightCart, hotelCart)
        ).join();

        // closing underlying PulsarClient
        client.close();
    }

    private static String getId() {
        return UUID.randomUUID().toString();
    }
}