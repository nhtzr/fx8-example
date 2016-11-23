package sample;

import sample.model.QueryLine;

import java.util.Collection;

import static sample.streams.Predicates.not;

/**
 * Created by e.rosas.garcia on 23/11/2016.
 */
public class Service {

    public String buildSqlQuery(Collection<QueryLine> lines) {
        StringBuilder sqlQuery = new StringBuilder();
        lines.stream()
                .filter(not(QueryLine::isEnabled))
                .forEach(queryLine -> sqlQuery.append(queryLine.getLine()));
        return sqlQuery.toString();
    }

}
