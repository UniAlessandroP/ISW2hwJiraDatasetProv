package app.com.isw2dsprov;

import app.com.isw2dsprov.control.GitCommitRetrievalControl;
import app.com.isw2dsprov.control.TicketRetrievalControl;
import app.com.isw2dsprov.entity.Ticket;

public class Main {

    public static void main(String[] args) {
        TicketRetrievalControl trc = new TicketRetrievalControl();
        for (Ticket tk : trc.retrieveTickets("BOOKKEEPER")) {
            tk.printTick();
        }

		String url = "https://github.com/UniAlessandroP/bookkeeper.git";
		String projname = "BOOKKEEPER";
		String token = "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";
		GitCommitRetrievalControl gcrc = new GitCommitRetrievalControl(projname, url, token);
    }
}
