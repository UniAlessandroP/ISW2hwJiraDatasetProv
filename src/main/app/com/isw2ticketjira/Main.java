package app.com.isw2ticketjira;

import app.com.isw2ticketjira.control.TicketRetrievalControl;
import app.com.isw2ticketjira.entity.Ticket;

public class Main {

    public static void main(String[] args) {
        TicketRetrievalControl trc = new TicketRetrievalControl();
        for (Ticket tk : trc.retrieveTickets("BOOKKEEPER")) {
            tk.printTick();
        }
    }
}
