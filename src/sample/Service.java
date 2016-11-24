package sample;

import sample.model.QueryLine;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by e.rosas.garcia on 23/11/2016.
 */
public class Service {

    private Set<String> keywords = new HashSet<>(Arrays.asList("select", "from", "where"));
    private Pattern keywordPattern = Pattern.compile("^\\s*(\\w+)");

    public String buildSqlQuery(Collection<QueryLine> lines) {
        final Set<String> keyWordsUsed = new HashSet<>();
        String sqlQuery = lines.stream()
                .filter(QueryLine::isEnabled)
                .map(QueryLine::getLine)
                .map(String::trim)
                .map(line -> processKeyWords(line, keyWordsUsed))
                .collect(Collectors.joining("\n"));
        return sqlQuery;
    }


    public String processKeyWords(String line, Set<String> keyWordsUsed) {
        String keyword = keywordOf(line);
        if (keyword == null) return line;
        if (keyWordsUsed.contains(keyword)) {
            return line.replaceFirst(keyword, replacementOf(keyword));
        }
        keyWordsUsed.add(keyword);
        return line;
    }

    private String keywordOf(String line) {
        Matcher matcher = keywordPattern.matcher(line);
        if (!matcher.find()) return null;

        String keyword = matcher.group();
        if (!keywords.contains(keyword)) return null;

        return keyword;
    }

    private String replacementOf(String keyword) {
        switch (keyword) {
            case "select":
                return ",";
            case "from":
                return "from";
            case "where":
                return "and";
            default:
                return keyword;
        }
    }

}
