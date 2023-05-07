package app.com.isw2dsprov.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import app.com.isw2dsprov.utils.RecurUtils;

public class Commit {
	
	public static final int INVALID_FLAG = -1;
	
	private ObjectId idc;
	private String author;
	private Date cdate;
	private String msg;
	private String shortmsg;
	private String prjn;
	private Integer issueid;

	public Commit(RevCommit commit, String projectname) {
		this.idc = commit.getId();
		this.author = commit.getAuthorIdent().getName();
		this.cdate = commit.getAuthorIdent().getWhen();
		this.msg = commit.getFullMessage();
		this.shortmsg = commit.getShortMessage();
		this.prjn = projectname;
		this.issueid = getJiraTickIdentifier(projectname);

	}
	
	private Integer getJiraTickIdentifier(String projectname) {
	
		List<Integer> idxes = searchProjIdfierIn(prjn, shortmsg);
	
		if(idxes == null) {
			idxes = searchProjIdfierIn(prjn, msg);
		}
		/* For simplicity consider only the first. 
		 * Eventually may come handy to keep all the matches.
		 * In which case the above shall change too.*/
		if(idxes != null) return idxes.get(0);
		
		return INVALID_FLAG;
		
	}
	private List<Integer> searchProjIdfierIn(String projectname, String m){
		List<Integer> issueidxes = new ArrayList<Integer>();
		projectname = projectname.toUpperCase();
		List<String> lmatches = RecurUtils.patternBasedSearch(projectname + "-\\d+", m);
		if(lmatches == null) return null;
		for(String match : lmatches) {
			int offset = projectname.length() + 1;
			int startidx = match.indexOf(projectname + "-") + offset;
			int endidx = RecurUtils.getFirstNonDigitIdx(match.substring(startidx)) 
					     + startidx;
			issueidxes.add(Integer.valueOf(match.substring(startidx, endidx)));
		}
		return issueidxes;
	}
	
	public void printCommit() {
		System.out.println("id: " + getIdcS());
		System.out.println("author: " + getAuthorS());
		System.out.println("msg: " + getMsgS());
	}
	
	public String getCommitIssueIdentifier() {
		return this.prjn + "-" + this.issueid;
	}

	public void printMessage() {
		System.out.println("Message: " + this.shortmsg);
	}	
	public String getIdcS() { return idc.getName(); }
	public String getAuthorS() { return this.author; }
	public String getProjectName() { return this.prjn; }
	public Date getCDate() { return this.cdate; }
	public String getMsgS() { return this.shortmsg; }
	public int getIssueid() {return this.issueid;}

}
