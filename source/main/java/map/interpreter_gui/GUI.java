package map.interpreter_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader programsListLoader = new FXMLLoader();
        programsListLoader.setLocation(getClass().getResource("programs-list-view.fxml"));
        Parent root = programsListLoader.load();
        ProgramsListController listController = programsListLoader.getController();
        primaryStage.setTitle("Select");
        primaryStage.setScene(new Scene(root, 700, 700));

        FXMLLoader programLoader = new FXMLLoader();
        programLoader.setLocation(getClass().getResource("program-view.fxml"));
        Parent programRoot = programLoader.load();
        ProgramController programController = programLoader.getController();
        listController.setProgramController(programController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(new Scene(programRoot, 1100, 800));

        secondaryStage.show();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}