package ch.fhnw.swc.mrs.model;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ITBill {

    /** users to use in tests. */
    private User u;

    /** movies to use in tests. */
    private Movie m1, m2, m3;

    /** the release date used for the movies m1 and m2. */
    private LocalDate d = LocalDate.now();

    /** the price category used for the movies m1 and m2. */
    private PriceCategory pc = RegularPriceCategory.getInstance();

    private LocalDate today = LocalDate.now();

    private Rental r1, r2;

    /**
     * @throws java.lang.Exception should not be thrown
     */
    @Before
    public void setUp() throws Exception{
        u = new User("Muster", "Hans", today.minusYears(25));
        Movie m1 = mock(Movie.class);
        Movie m2 = mock(Movie.class);
        Movie m3 = mock(Movie.class);
        when(m1.getTitle()).thenReturn("Avatar");
        when(m2.getTitle()).thenReturn("Casablanca");
        when(m3.getTitle()).thenReturn("Tron");

        Rental r1 = mock(Rental.class);
        Rental r2 = mock(Rental.class);
        Rental r3 = mock(Rental.class);
        when(r1.getMovie()).thenReturn(m1);
        when(r2.getMovie()).thenReturn(m2);
        when(r3.getMovie()).thenReturn(m3);
        when(r1.getRentalDays()).thenReturn(1L);
        when(r2.getRentalDays()).thenReturn(2L);
        when(r3.getRentalDays()).thenReturn(3L);
        when(r1.getRentalFee()).thenReturn(8.4);
        when(r2.getRentalFee()).thenReturn(17.2);
        when(r3.getRentalFee()).thenReturn(26.4);

        u.addRental(r1);
        u.addRental(r2);
        u.addRental(r3);
    }

    @Test
    public void IntegrationBillTest(){
        Bill b = new Bill(u.getName(), u.getFirstName(), u.getRentals());
        String s = b.print();
        String[] lines = s.split("\n");
        assertEquals(9, lines.length);
        assertEquals("Statement", lines[0]);
        assertEquals("=========", lines[1]);
        assertEquals("for: Hans Muster", lines[2]);
        assertEquals("", lines[3]);
        assertEquals("Days   Price  Title", lines[4]);
        assertEquals("-------------------", lines[5]);
        assertEquals("   1    8.40  Avatar", lines[6]);
        assertEquals("   2   17.20  Casablanca", lines[7]);
        assertEquals("   3   26.40  Tron", lines[8]);
    }

}
