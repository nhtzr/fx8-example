package sample.model;

/**
 * Created by e.rosas.garcia on 17/11/2016.
 */
public class QueryLine {

    private boolean enabled;
    private String line;

    public QueryLine(String queryLine) {
        this.enabled = !queryLine.startsWith("--");
        this.line = queryLine.replace("--", "");
    }

    public QueryLine(boolean enabled, String line) {
        this.enabled = enabled;
        this.line = line;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "QueryLine{" +
                "enabled=" + enabled +
                ", line='" + line + '\'' +
                '}';
    }
}
