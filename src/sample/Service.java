package sample;

import sample.model.QueryLine;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by e.rosas.garcia on 23/11/2016.
 */
public class Service {

    private Map<String, String> keywordReplacements = buildKeywordReplacements();
    private Set<String> keywords = keywordReplacements.keySet();
    private Pattern keywordPattern = Pattern.compile("^\\s*(\\w+)");

    public String buildSqlQuery(Collection<QueryLine> lines) {
        final Set<String> keyWordsUsed = new HashSet<>();
        return lines.stream()
                .filter(QueryLine::isEnabled)
                .map(QueryLine::getLine)
                .map(String::trim)
                .map(line -> processKeyWords(line, keyWordsUsed))
                .collect(Collectors.joining("\n"));
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
        return Optional.ofNullable(keywordReplacements.get(keyword)).orElse("keyword");
    }

    private Map<String, String> buildKeywordReplacements() {
        HashMap<String, String> map = new HashMap<>();
        map.put("select", ",");
        map.put("from", "from");
        map.put("where", "and");
        return map;
    }

}
