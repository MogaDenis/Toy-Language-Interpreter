package map.interpreter_gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import map.interpreter_gui.controller.Controller;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.repository.InMemoryRepository;
import map.interpreter_gui.repository.ProgramsRepository;

import java.util.List;

public class ProgramsListController {
    @FXML
    private Button displayButton;
    private ProgramController programController;

    @FXML
    private ListView<String> programsListView;

    public void setProgramController(ProgramController programController) {
        this.programController = programController;
    }

    @FXML
    public void initialize() {
        List<String> programsCode = ProgramsRepository.getProgramsStates().stream().map(ProgramState::toStringCode).toList();

        this.programsListView.setItems(FXCollections.observableArrayList(programsCode));
        this.displayButton.setOnAction(actionEvent -> {
            int index = this.programsListView.getSelectionModel().getSelectedIndex();

            if (index < 0)
                return;

            Controller controller;
            try {
                controller = new Controller(new InMemoryRepository(new ProgramState(ProgramsRepository.programs.get(index)), "log.txt"));
            } catch (TypeException e) {
                throw new RuntimeException(e);
            }

            programController.setController(controller);
        });
    }
}