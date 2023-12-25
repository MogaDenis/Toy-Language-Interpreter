module map.interpreter_gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens map.interpreter_gui to javafx.fxml;
    exports map.interpreter_gui;
}