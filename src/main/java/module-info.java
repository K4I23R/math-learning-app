module pl.michalsnella.mathlearning {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pl.michalsnella.mathlearning to javafx.fxml;
    exports pl.michalsnella.mathlearning;
}