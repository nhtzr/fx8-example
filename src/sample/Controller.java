package sample;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import sample.model.QueryLines;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static sample.fx.Properties.columnAreaString;
import static sample.fx.Properties.columnEditableBoolean;
import static sample.streams.Predicates.not;

public class Controller implements Initializable {

    private static final Collection<String> EMPTY_LINES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "select",
            "from",
            "where",
            ";")));

    @FXML
    public TableView<QueryLines> queryTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass()
                        .getResourceAsStream("/base_query.sql")));

        queryTableView.getColumns().setAll(
                columnEditableBoolean(QueryLines::isEnabled, QueryLines::setEnabled),
                columnAreaString(QueryLines::getLine, QueryLines::setLine));
        queryTableView.setItems(new ObservableListWrapper<>(reader
                .lines()
                .map(String::trim)
                .filter(not(String::isEmpty))
                .filter(not(EMPTY_LINES::contains))
                .map(QueryLines::new)
                .collect(Collectors.toList())));

        queryTableView.itemsProperty().addListener((observable, oldValue, newValue) -> System.out.println("newValue = " + newValue));
    }

    public void printAll() {
        queryTableView.getItems().forEach(System.out::println);
    }

}
