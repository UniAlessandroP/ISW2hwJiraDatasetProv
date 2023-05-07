package app.com.isw2dsprov.control;

public class TicketRetrievalConstants {

    private TicketRetrievalConstants() {
        // No need to instantiate the class, we can hide its constructor
    }

    // Url generation specific constants
    public static final String JIRA_BASE_URL = "https://issues.apache.org/jira/rest/api/2/";
    public static final String D_Q = "%22";
    public static final String QUERYSTART_COMPONENT = "search?jql=";
    public static final String AND_C = "AND";
    public static final String OR_C = "OR";
    public static final String EQU_C = "=";
    public static final String NEXT_MULT_FILTER = AND_C + "(";
    public static final String CLOSE_MULT_FILTER = ")";
    public static final String ISSUE_TYPE_CLAUSE = D_Q + "issueType" + D_Q + EQU_C;
    public static final String PROJ_NAME_CLAUSE = D_Q + "project" + D_Q + EQU_C;
    public static final String STATUS_CLAUSE = D_Q + "status" + D_Q + EQU_C;
    public static final String RESOLUTION_CLAUSE = D_Q + "resolution" + D_Q + EQU_C;
    public static final String STARTAT_CLAUSE = "&startAt=";
    public static final String MAXRES_CLAUSE = "&maxResults=";
    public static final String FIX_VER_FIELD = "fixVersions";
    public static final String AFFECTED_VER_FIELD = "versions";
    
    public static final String FIELDS_CLAUSE = "&fields=issuetype,status,key,resolutiondate," +
    											AFFECTED_VER_FIELD + "," + FIX_VER_FIELD + ",created";

}
