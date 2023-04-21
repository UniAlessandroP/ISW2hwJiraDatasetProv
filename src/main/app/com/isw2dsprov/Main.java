package app.com.isw2dsprov;

import app.com.isw2dsprov.control.TicketRetrievalControl;
import app.com.isw2dsprov.entity.Ticket;

public class Main {

    public static void main(String[] args) {
        TicketRetrievalControl trc = new TicketRetrievalControl();
        for (Ticket tk : trc.retrieveTickets("BOOKKEEPER")) {
            tk.printTick();
        }
    }
}
