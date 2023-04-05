package app.com.isw2ticketjira.entity;

import app.com.isw2ticketjira.utils.RecurUtils;

public class Ticket {
    private String projectName;
    private Integer unversalid;
    private Integer id;
    private String type;
    private String status;

    public Ticket(String key, String type, String status, Integer uid) {
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
    }

    public void printTick() {
        String outline = "Jid: " + this.unversalid + "\nName: " + this.projectName + '-' + this.id + "\nType: "
                + this.type + "\nStatus: " + this.status;
        System.out.println(outline);
    }

    public int getId() {
        return this.id;
    }
}
