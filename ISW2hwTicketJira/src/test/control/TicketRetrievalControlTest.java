package test.control;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jiraticketsmanager.control.TicketRetrievalControl;
import jiraticketsmanager.entity.Ticket;

class TicketRetrievalControlTest {
    private static TicketRetrievalControl trc;
    
    @BeforeAll
    static void TicketRetrievalControlBeforeAll() {
        System.out.println("Beforeall statement");
        trc = new TicketRetrievalControl();
        assertNotNull(trc);
    }
    
    @Test
    void retrieveTicketsTest() {
        String[] correctprojnames = {"BOOKKEEPER", "SYNCOPE"};
        String[] wrongprojnames = {"BOO", "SSYNCOPE", "", null};

        for(String proj : correctprojnames) {
            List<Ticket> tks = trc.retrieveTickets(proj);
            assertNotEquals(null, tks);
            if(!tks.isEmpty()) {
                for(Ticket tk : tks) {
                    assertNotNull(tk.getId());
                    assertTrue(tk.getId() > -1);
                }
            }
        }
        

        for(String proj : wrongprojnames) {
            List<Ticket> tks = trc.retrieveTickets(proj);
            assertEquals(null, tks);
        }
    }

}
