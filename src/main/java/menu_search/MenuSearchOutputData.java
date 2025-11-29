package menu_search;

import entity.MenuItem;
import java.util.List;

public class MenuSearchOutputData {
    private final String query;
    private final List<MenuItem> results;

    public MenuSearchOutputData(String query, List<MenuItem> results) {
        this.query = query;
        this.results = results;
    }

    public String getQuery() {
        return query;
    }

    public List<MenuItem> getResults() {
        return results;
    }
}
