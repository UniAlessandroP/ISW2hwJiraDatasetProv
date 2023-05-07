package app.com.isw2dsprov.control;

import java.util.ArrayList;
import java.util.List;

import app.com.isw2dsprov.entity.Commit;
import app.com.isw2dsprov.entity.IssueCommit;
import app.com.isw2dsprov.entity.Ticket;
import app.com.isw2dsprov.utils.CommitUtils;
import app.com.isw2dsprov.utils.IssueCommitUtils;
import app.com.isw2dsprov.utils.TicketUtils;

public class DatasetGenerationControl {
	public static final String token = "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";
	
	public void generateDataset(String projectname) {
        TicketRetrievalControl trc = new TicketRetrievalControl();
        List<Ticket> ticklist = trc.retrieveTickets(projectname);
        TicketUtils.sortByDate(ticklist, true);
        TicketUtils.printCreatedDates(ticklist);
        
		String url = "https://github.com/UniAlessandroP/"  + projectname.toLowerCase() +  ".git";
		GitCommitRetrievalControl gcrc = new GitCommitRetrievalControl(projectname, url, token);
		List<Commit> commlist = gcrc.getCommitList();
		CommitUtils.sortByDate(commlist);
		
		//Filter commit by issue reference
		List<Commit> issuecommits = CommitUtils.filterByIdValidity(commlist, true);
		List<Commit> nonissuecommits = CommitUtils.filterByIdValidity(commlist, false);

		List<IssueCommit> itcl = IssueCommitUtils.getLinkedIssueCommits(commlist, ticklist);

		//IssueCommitUtils.fillInCommitRevision();
		for(Ticket t : ticklist) {
			t.printVersions();
		}
	}

	public void exportToCSV() {
		//TODO
	};
	
	public static void main(String[] args) {

		DatasetGenerationControl dgc = new DatasetGenerationControl();
		dgc.generateDataset("BOOKKEEPER");
		
		
	}
}
