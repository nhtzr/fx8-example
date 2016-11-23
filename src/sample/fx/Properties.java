package sample.fx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import sample.model.QueryLine;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;

/**
 * Created by e.rosas.garcia on 18/11/2016.
 */
public class Properties {

    public static <C> Callback<TableColumn.CellDataFeatures<C, Boolean>, ObservableValue<Boolean>>
    editableBooleanPropertyValueFactory(
            Getter<C, Boolean> getter,
            Setter<C, Boolean> setter
    ) {
        return cellData -> {
            boolean get = getter.invoke(cellData.getValue());
            SimpleBooleanProperty prop = new SimpleBooleanProperty(get);
            prop.addListener((observable, oldValue, newValue) -> setter.invoke(cellData.getValue(), newValue));
            return prop;
        };
    }

    public static <C> Callback<TableColumn.CellDataFeatures<C, String>, ObservableValue<String>>
    editableStringPropertyValueFactory(
            Getter<C, String> getter,
            Setter<C, String> setter
    ) {
        return cellData -> {
            String get = getter.invoke(cellData.getValue());
            SimpleStringProperty prop = new SimpleStringProperty(get);
            prop.addListener((observable, oldValue, newValue) -> setter.invoke(cellData.getValue(), newValue));
            return prop;
        };
    }


    public static TableColumn<QueryLine, Boolean> columnEditableBoolean(
            Getter<QueryLine, Boolean> getter,
            Setter<QueryLine, Boolean> setter) {
        TableColumn<QueryLine, Boolean> column = new TableColumn<>();
        column.setCellFactory(CheckBoxTableCell.forTableColumn(column::getCellObservableValue));
        column.setCellValueFactory(editableBooleanPropertyValueFactory(getter, setter));
        return column;
    }

    public static <T> QueryLine getValue(TableColumn.CellEditEvent<QueryLine, T> t) {
        return t.getTableView().getItems().get(t.getTablePosition().getRow());
    }

    public static TableColumn<QueryLine, String> columnEditableString(
            Getter<QueryLine, String> getter,
            Setter<QueryLine, String> setter) {
        TableColumn<QueryLine, String> column = new TableColumn<>();
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setCellValueFactory(editableStringPropertyValueFactory(getter, setter));
        column.setOnEditCommit(t -> setter.invoke(getValue(t), t.getNewValue()));
        return column;
    }

    public static TableColumn<QueryLine, String> columnAreaString(Getter<QueryLine, String> getter, Setter<QueryLine, String> setter) {
        TableColumn<QueryLine, String> column = new TableColumn<>();
        column.setCellFactory(TextAreaTableCell.forTableColumn(normalizeSpaceConverter()));
        column.setCellValueFactory(editableStringPropertyValueFactory(getter, setter));
        column.setOnEditCommit(t -> setter.invoke(getValue(t), t.getNewValue()));
        return column;
    }

    private static DefaultStringConverter normalizeSpaceConverter() {
        return new DefaultStringConverter() {
            @Override
            public String toString(String value) {
                return normalizeSpace(super.toString(value).replace('\n', ' '));
            }
        };
    }

}
