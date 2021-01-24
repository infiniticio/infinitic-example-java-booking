package infinitic.example.java.booking.tasks.hotel;

public interface HotelBookingService {
    HotelBookingResult book(HotelBookingCart cart);

    void cancel(HotelBookingCart cart);
}