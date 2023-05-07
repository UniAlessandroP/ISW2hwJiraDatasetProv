package app.com.isw2dsprov.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import app.com.isw2dsprov.entity.Commit;

public class CommitUtils {
	public static void sortByDate(List<Commit> comm) {
        Collections.sort(comm, new Comparator<Commit>() {
            @Override
            public int compare(Commit c1, Commit c2) {
                return c1.getCDate().compareTo(c2.getCDate());
            }
        });
    }

    public static List<Commit> filterByIdValidity(List<Commit> cs, boolean valid) {
    	if(valid) {
	        return cs.stream()
	                     .filter(c -> c.getIssueid() != Commit.INVALID_FLAG)
	                     .collect(Collectors.toList());
    	}
        return cs.stream()
                .filter(c -> c.getIssueid() == Commit.INVALID_FLAG)
                .collect(Collectors.toList());
    }
}
