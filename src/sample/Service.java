package sample;

import org.apache.commons.lang3.StringUtils;
import sample.model.QueryLine;

import java.util.Collection;

/**
 * Created by e.rosas.garcia on 23/11/2016.
 */
public class Service {

    public String buildSqlQuery(Collection<QueryLine> lines) {
        StringBuilder sqlQuery = new StringBuilder();
        lines.stream()
                .filter(QueryLine::isEnabled)
                .map(QueryLine::getLine)
                .forEach(sqlQuery::append);
        return StringUtils.normalizeSpace(sqlQuery.toString());
    }

}
