package app.com.isw2dsprov.utils;

import java.util.ArrayList;
import java.util.List;

import app.com.isw2dsprov.entity.Commit;
import app.com.isw2dsprov.entity.IssueCommit;
import app.com.isw2dsprov.entity.Ticket;

public class IssueCommitUtils {
	
	public static List<IssueCommit> getLinkedIssueCommits(List<Commit> commlist, List<Ticket> ticklist) {
		List<IssueCommit> itc = new ArrayList<IssueCommit>();
		for(Commit c : commlist) {
			Integer cid = c.getIssueid();
			if(cid == -1) {
				itc.add(new IssueCommit(c.getIdcS(), c, null));
				continue;
			}
			boolean foundticket = false;
			for(Ticket t : ticklist) {
				if(cid.equals(t.getId())) {
					itc.add(new IssueCommit(c.getProjectName() + "-" + cid, c, t));
					foundticket = true;
					break;
				}
			}
			if(! foundticket) {
				itc.add(new IssueCommit(c.getIdcS(), c, null));
			} 
		}
		return itc;
	}

}
