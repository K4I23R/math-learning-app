module pl.michalsnella.mathlearning {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens pl.michalsnella.mathlearning.controller to javafx.fxml;
    opens pl.michalsnella.mathlearning.controller.exercises to javafx.fxml;
    opens pl.michalsnella.mathlearning.model.user to com.fasterxml.jackson.databind;

    exports pl.michalsnella.mathlearning;
    exports pl.michalsnella.mathlearning.controller;
}