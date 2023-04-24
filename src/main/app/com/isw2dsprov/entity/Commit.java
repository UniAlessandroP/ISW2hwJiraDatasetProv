package app.com.isw2dsprov.entity;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class Commit {
	
	private ObjectId idc;
	private String author;
	private String msg;
	private String shortmsg;

	public Commit(RevCommit commit) {
		this.idc = commit.getId();
		this.author = commit.getAuthorIdent().getName();
		this.msg = commit.getFullMessage();
		this.shortmsg = commit.getShortMessage();
	}

	public void printCommit() {
		System.out.println("id: " + getIdcS());
		System.out.println("author: " + getAuthorS());
		System.out.println("msg: " + getMsgS());
	}
	
	public String getIdcS() { return idc.getName(); }
	public String getAuthorS() { return author; }
	public String getMsgS() { return shortmsg; }
}
