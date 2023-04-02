package jiraticketsmanager;

import jiraticketsmanager.control.TicketRetrievalControl;
import jiraticketsmanager.entity.Ticket;

public class Main {

	public static void main(String[] args) {
	    TicketRetrievalControl trc = new TicketRetrievalControl();
	    for (Ticket tk : trc.retrieveTickets("BOOKKEEPER") ) {
	        tk.printTick();
	    }
	}
}
