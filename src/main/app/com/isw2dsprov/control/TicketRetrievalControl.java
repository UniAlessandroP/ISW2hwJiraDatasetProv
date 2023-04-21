package app.com.isw2dsprov.control;

import static app.com.isw2dsprov.control.TicketRetrievalConstants.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.isw2dsprov.entity.Ticket;
import app.com.isw2dsprov.utils.NetUtils;

public class TicketRetrievalControl {

    public String provideUrlForTks(String projectName, String tkType, String[] statuses, String resol, Integer startAt,
            Integer maxRes) {

        StringBuilder composedUrl = new StringBuilder();

        composedUrl.append(JIRA_BASE_URL);
        composedUrl.append(QUERYSTART_COMPONENT);
        // Add filter by project
        composedUrl.append(PROJ_NAME_CLAUSE).append(D_Q).append(projectName).append(D_Q);
        if (!(tkType == null || tkType.equals(""))) {
            composedUrl.append(AND_C).append(ISSUE_TYPE_CLAUSE).append(D_Q).append(tkType).append(D_Q);
        }
        // Add filter by status
        if (statuses != null) {
            int stsize = statuses.length;
            if (statuses.length > 1)
                composedUrl.append(NEXT_MULT_FILTER);
            else if (statuses.length == 1)
                composedUrl.append(AND_C);

            for (String s : statuses) {
                composedUrl.append(STATUS_CLAUSE).append(D_Q).append(s).append(D_Q);
                stsize--;
                if (stsize != 0)
                    composedUrl.append(OR_C);
            }
            if (statuses.length > 1)
                composedUrl.append(CLOSE_MULT_FILTER);
        }
        // Add filter by resolution
        if (resol != null && !resol.equals(""))
            composedUrl.append(AND_C).append(RESOLUTION_CLAUSE).append(D_Q).append(resol).append(D_Q);

        // Requested fields
        composedUrl.append(FIELDS_CLAUSE);

        // Set start and max for non premium reasons
        composedUrl.append(STARTAT_CLAUSE).append(startAt.toString());
        composedUrl.append(MAXRES_CLAUSE).append(maxRes.toString());

        return composedUrl.toString();
    }

    public List<Ticket> retrieveTickets(String projectName) {
        Integer j = 0, i = 0, total = 1;
        List<Ticket> ticklis = new ArrayList<Ticket>();
        do {
            // Only gets a max of 1000 at a time, so must do this multiple times if bugs
            // >1000
            j = i + 1000;
            String url = provideUrlForTks(projectName, null, null, null, i, j);
            System.out.println("Url: " + url);
            JSONObject json;
            try {
                json = NetUtils.getJsonFromUrl(url);
            } catch (JSONException | IOException e) {
                String[] msgl = e.getMessage().split(":");
                if(msgl.length < 2 || !msgl[1].contains("400")) {
                    Thread.dumpStack();
                    throw new UnknownError(e.getMessage());
                }
                throw new RuntimeException("Error 400. Bad Request.");
            }

            JSONArray issues = json.getJSONArray("issues");
            System.out.println(issues.toString());
            total = json.getInt("total");
            for (; i < total && i < j; i++) {
                // Iterate through each ticket and create its abstraction
                JSONObject jO = issues.getJSONObject(i % 1000);
                String key = jO.get("key").toString();
                Integer jiraId = jO.getInt("id");
                String typetk = jO.getJSONObject("fields").getJSONObject("issuetype").getString("name");
                String statustk = jO.getJSONObject("fields").getJSONObject("status").getString("name");
                ticklis.add(new Ticket(key, typetk, statustk, jiraId));
            }
        } while (i < total);

        return ticklis;
    }
}