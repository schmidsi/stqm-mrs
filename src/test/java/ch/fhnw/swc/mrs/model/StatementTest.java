package ch.fhnw.swc.mrs.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class StatementTest {
    
    private Statement s;
    private List<Rental> rentals;

    @Before
    public void setup() {
        Rental r1 = mock(Rental.class);
        Rental r2 = mock(Rental.class);
        Rental r3 = mock(Rental.class);

        rentals = new ArrayList<>(3);
        rentals.add(r1);
        rentals.add(r2);
        rentals.add(r3);
    }

    /**
     * Test method for
     * {@link ch.fhnw.swc.mrs.model.Statement#Statement(String, String, List<Rental>)}
     *
     */
    @Test
    public void testStatement() {
        Statement s = spy(new Statement("Muster","Hans", rentals){
            @Override
            public String print() {
                return null;
            }
        });

        assertEquals("Muster", s.getLastName());
        assertEquals("Hans", s.getFirstName());
        assertEquals(3, s.getRentals().size());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testFirstName() {
        //new Statement("Muster", "Maximilian", rentals);
        Statement s = spy(new Statement("Muster", "Maximilan", rentals){
            @Override
            public String print() {
                return null;
            }
        });
    }

    @Test(expected=IllegalArgumentException.class)
    public void testLastName() {
       //new Statement("Mustermann", "Hans", rentals);
       Statement s = spy(new Statement("Mustermann", "Hans", rentals){
           @Override
           public String print() {
               return null;
           }
       });
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRentals() {
       //new Statement("Muster", "Hans", null);
        Statement s = spy(new Statement("Muster", "Hans", null){
            @Override
            public String print() {
                return null;
            }
        });
    }

}
