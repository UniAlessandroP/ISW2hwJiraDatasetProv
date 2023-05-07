package app.com.isw2dsprov.entity;

import java.util.Date;
import java.util.List;

import app.com.isw2dsprov.utils.RecurUtils;

public class Ticket {
    private String projectName;
    private Integer unversalid;
    private Integer id;
    private String type;
    private String status;
    private Date created;
    
    List<Version> affectedver;
    List<Version> fixedver;

    public Ticket(String key, String type, String status, Integer uid, Date datecreated, List<Version> affectedver, List<Version> fixedver) {
        if (uid == null || key == null || key.equals("") || uid < 0)
            throw new NullPointerException("Ticket name or JiraId invalid.");

        String[] spl = key.split("-");
        if (spl.length == 2 && RecurUtils.isInteger(spl[1])) {

            this.id = Integer.parseInt(spl[1]);
            this.projectName = spl[0];
        }

        this.type = type;
        this.unversalid = uid;
        this.status = status;
        this.created = datecreated;
        
        this.affectedver = affectedver;
        this.fixedver = fixedver;
    }

    public void printTick() {
        String outline = "Jid: " + this.unversalid + "\nName: " + this.projectName + '-' + this.id + "\nType: "
                + this.type + "\nStatus: " + this.status;
        System.out.println(outline);
    }

    public String getTicketName() {return this.projectName + '-' + this.id;}
    
    public int getId() {
        return this.id;
    }

	public void printDateCreated() {
		System.out.println(this.created.toString());
		
	}

	public Date getCreated() {
		return this.created;
	}
	
	public void printVersions() {
		if(this.affectedver.isEmpty()) {
			System.out.println("No affected versions for: " + this.getKey());
		}else {
			printAffectedVersions();
		}
		if(this.fixedver.isEmpty()) {
			System.out.println("No fixed versions for: " + this.getKey());
			return;
		}
		printFixedVersions();
	}
	
	private String getKey() {
		return this.projectName + "-" + this.getId();
	}

	public void printAffectedVersions() {
		for(Version v : this.affectedver) {
			v.printInfo();
		}
	}
	
	public void printFixedVersions() {
		for(Version v : this.fixedver) {
			v.printInfo();
		}
	}	
	
}
