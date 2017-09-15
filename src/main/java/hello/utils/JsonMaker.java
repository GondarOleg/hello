package hello.utils;

import com.google.gson.Gson;
import hello.model.Contact;

import java.util.LinkedList;
import java.util.List;

public class JsonMaker {

    private JsonMaker() {
    }

    private static final String NEW_LINE = "<br>";
    private static final String NAME_FILTER = "?nameFilter=";
    private static final String PAGE = "&page=";

    private static String makePaginatedHeader(int totalPages) {

        return "{" +
                NEW_LINE + "\"meta\": {" +
                NEW_LINE + "\"total-pages\": " + totalPages +
                NEW_LINE + " }," +
                NEW_LINE + "\"Contacts\" :" +
                NEW_LINE;
    }

    private static String makePaginatedFooter(int totalPages, int pageNumber, String regex, String url) {
        pageNumber++;
        return NEW_LINE + "\"links\": {" +
                NEW_LINE + "\"self\": \"" + url + NAME_FILTER + regex + PAGE + pageNumber + "\"" +
                NEW_LINE + "\"first\": \"" + url + NAME_FILTER + regex + PAGE + "1\"" +
                NEW_LINE + "\"prev\": \"" + url + NAME_FILTER + regex + PAGE + ((pageNumber - 1 == 0) ? pageNumber : (pageNumber - 1)) + "\"" +
                NEW_LINE + "\"next\": \"" + url + NAME_FILTER + regex + PAGE + ((pageNumber >= totalPages) ? pageNumber : (pageNumber + 1)) + "\"" +
                NEW_LINE + "\"last\": \"" + url + NAME_FILTER + regex + PAGE + totalPages + "\"" +
                NEW_LINE + "}";
    }

    private static String makeJson(int pageNumber, int pageSize, int totalRecordCount, List<Contact> contacts) {
        List<Contact> temp = new LinkedList<Contact>();
        int endIndex = 0;
        if (pageNumber * pageSize + pageSize >= totalRecordCount) {
            endIndex = totalRecordCount;
        } else {
            endIndex = pageNumber * pageSize + pageSize;
        }
        for (int i = pageNumber * pageSize; i < endIndex; i++) {
            temp.add(contacts.get(i));
        }
        return new Gson().toJson(temp);
    }

    public static String makePaginatedJson(int totalPages, int pageNumber, int pageSize, int totalRecordCount, String regex, String url, List<Contact> contacts){
        return makePaginatedHeader(totalPages) + makeJson(pageNumber, pageSize, totalRecordCount, contacts) + makePaginatedFooter(totalPages, pageNumber, regex, url);
    }
}
