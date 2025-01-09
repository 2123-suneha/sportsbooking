package com.example;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.entity.Booking;
import com.example.entity.BookingDTO;
import com.example.repository.BookingRepository;
import com.example.service.BookingServiceImpl;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        BookingDTO bookingDTO = new BookingDTO(1, 101, 202, Arrays.asList("A1", "A2"));
        Booking booking = new Booking(1, 101, 202, Arrays.asList("A1", "A2"), 0);

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(bookingDTO);

        assertNotNull(result);
        assertEquals(1, result.getBookingId());
        assertEquals(101, result.getUserId());
        assertEquals(202, result.getSportsId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetBookingById_Success() {
        Booking booking = new Booking(1, 101, 202, Arrays.asList("A1", "A2"), 0);
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(1);

        assertNotNull(result);
        assertEquals(1, result.getBookingId());
        verify(bookingRepository, times(1)).findById(1);
    }

    @Test
    void testGetBookingById_NotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.getBookingById(1));

        assertEquals("Booking Not Found!!", exception.getMessage());
        verify(bookingRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllBookings() {
        Booking booking1 = new Booking(1, 101, 202, Arrays.asList("A1", "A2"), 0);
        Booking booking2 = new Booking(2, 102, 203, Arrays.asList("B1", "B2"), 0);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testDeleteBooking_Success() {
        Booking booking = new Booking(1, 101, 202, Arrays.asList("A1", "A2"), 0);
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));

        Booking result = bookingService.deleteBooking(1);

        assertNotNull(result);
        assertEquals(1, result.getBookingId());
        verify(bookingRepository, times(1)).findById(1);
        verify(bookingRepository, times(1)).delete(booking);
    }

    @Test
    void testDeleteBooking_NotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> bookingService.deleteBooking(1));

        assertEquals("Booking Not Found!!", exception.getMessage());
        verify(bookingRepository, times(1)).findById(1);
        verify(bookingRepository, never()).delete(any(Booking.class));
    }

    @Test
    void testByUserName() {
        Booking booking1 = new Booking(1, 101, 202, Arrays.asList("A1", "A2"), 0);
        Booking booking2 = new Booking(2, 102, 203, Arrays.asList("B1", "B2"), 0);
        when(bookingRepository.findByUserName("testUser")).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> result = bookingService.byUserName("testUser");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findByUserName("testUser");
    }
}

