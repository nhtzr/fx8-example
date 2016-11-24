package sample;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import sample.model.QueryLine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static sample.fx.Properties.columnAreaString;
import static sample.fx.Properties.columnEditableBoolean;
import static sample.streams.Predicates.not;

public class Controller implements Initializable {

    private static final Collection<String> EMPTY_LINES = Collections.unmodifiableSet(Collections.singleton(";"));

    @FXML
    public TableView<QueryLine> queryTableView;

    private Service service = new Service();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        queryTableView.getColumns().setAll(
                columnEditableBoolean(QueryLine::isEnabled, QueryLine::setEnabled),
                columnAreaString(QueryLine::getLine, QueryLine::setLine));
        queryTableView.setItems(new ObservableListWrapper<>(
                readFrom("/base_query.sql")
                        .lines()
                        .map(String::trim)
                        .filter(not(String::isEmpty))
                        .filter(not(EMPTY_LINES::contains))
                        .map(QueryLine::new)
                        .collect(Collectors.toList())));
    }

    public void printAll() {
        String query = service.buildSqlQuery(queryTableView.getItems());
        System.out.println("query = " + query);
    }

    private BufferedReader readFrom(String filePath) {
        return new BufferedReader(
                new InputStreamReader(getClass()
                        .getResourceAsStream(filePath)));
    }
}
