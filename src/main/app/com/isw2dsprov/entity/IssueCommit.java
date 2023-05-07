package app.com.isw2dsprov.entity;

public class IssueCommit {
	private String id;
	private Commit commit;
	private Ticket ticket;
	
	
	public IssueCommit(String name, Commit c, Ticket t) {
		commit = c;
		ticket = t;
		this.id  = name;
	}
	
	public void printIssueInfo() {
		System.out.println("id: " + id);
		System.out.println("commit: " + this.commit.getIssueid());
		System.out.println("ticket: " + this.ticket.getId());
	}
}
