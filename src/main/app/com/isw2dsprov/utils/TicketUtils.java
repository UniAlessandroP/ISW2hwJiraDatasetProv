package app.com.isw2dsprov.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.com.isw2dsprov.entity.Ticket;

public class TicketUtils {

	public static void sortByDate(List<Ticket> ticks, boolean b) {
        Collections.sort(ticks, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket t1, Ticket t2) {
                return t1.getCreated().compareTo(t2.getCreated());
            }
        });
	}

	public static void printCreatedDates(List<Ticket> tkl) {
		for(Ticket t : tkl) {
			t.printDateCreated();
		}
	}

}
