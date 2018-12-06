package beans;

import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import operation.SessionBean;

@ManagedBean
@SessionScoped
public class HistoryManagedBean {

    private String table;
    SessionBean session = new SessionBean();
    private ArrayList<ArrayList<String>> history;

    public HistoryManagedBean() {
        table = "";
        history = new ArrayList<ArrayList<String>>();
    }

    public void search() {
        history = session.getHistory(table);
    }

    public Map<String, String> getTables() {
        return session.getTables();
    }

    public ArrayList<String> getColumns() {
        return session.getColumns(table);
    }

    public ArrayList<ArrayList<String>> getHistory() {
        return history;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getColumnsNumber() {
        return getColumns().size();
    }

}
