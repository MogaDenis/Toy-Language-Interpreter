package map.interpreter_gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import map.interpreter_gui.controller.Controller;
import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.statements.IStatement;
import map.interpreter_gui.model.structures.*;
import map.interpreter_gui.model.values.StringValue;
import map.interpreter_gui.model.values.Value;

import java.util.*;

public class ProgramController {
    @FXML
    private TextField programStatesTextField;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> heapValueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> programStatesListView;

    @FXML
    private TableView<Pair<String, Value>> symbolTableTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> symbolTableNameColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> symbolTableValueColumn;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, String>>> barrierTableTableView;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, Integer> barrierTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, Integer> barrierTableValueColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, String> barrierTableListColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> lockTableTableView;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockTableValueColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> latchTableTableView;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> latchTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> latchTableValueColumn;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, Pair<String, Integer>>>> toySemaphoreTableTableView;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, Pair<String, Integer>>>, Integer> toySemaphoreTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, Pair<String, Integer>>>, Integer> toySemaphoreTableValueColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, Pair<String, Integer>>>, String> toySemaphoreTableListColumn;

    @FXML
    private TableView<Pair<Integer, Pair<Integer, String>>> countSemaphoreTableTableView;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, Integer> countSemaphoreTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, Integer> countSemaphoreTableValueColumn;

    @FXML
    private TableColumn<Pair<Integer, Pair<Integer, String>>, String> countSemaphoreTableListColumn;

    @FXML
    private Button oneStepButton;

    @FXML
    private Controller controller;


    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    private ProgramState getCurrentProgramState(){
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty())
            return null;

        int currentId = programStatesListView.getSelectionModel().getSelectedIndex();
        this.programStatesListView.getFocusModel().focus(programStatesListView.getSelectionModel().getSelectedIndex());
        if (currentId == -1)
            return null;

        if (currentId >= programStates.size())
            currentId = programStates.size() - 1;

        return programStates.get(currentId);
    }

    @FXML
    public void initialize() {
        this.heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        this.symbolTableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        this.symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        this.barrierTableAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.barrierTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().getKey()).asObject());
        this.barrierTableListColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue()));
        this.lockTableAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.lockTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());
        this.latchTableAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.latchTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());
        this.toySemaphoreTableAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.toySemaphoreTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().getKey()).asObject());
        this.toySemaphoreTableListColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue().getKey()));
        this.countSemaphoreTableAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.countSemaphoreTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().getKey()).asObject());
        this.countSemaphoreTableListColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().getValue()));

        this.oneStepButton.setOnAction(actionEvent -> {
            if(this.controller == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            if (!this.controller.programsLeftToExecuteExist()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!", ButtonType.OK);
                alert.showAndWait();

                this.controller.setPrograms(this.controller.removeCompletedPrograms(this.controller.getProgramStates()));
                populate();

                return;
            }

            try {
                this.controller.setPrograms(this.controller.removeCompletedPrograms(this.controller.getProgramStates()));

                this.controller.oneStepForAllPrograms(this.controller.getProgramStates());

                populate();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        this.programStatesListView.setOnMouseClicked(mouseEvent -> populate());
    }

    private void populate() {
        populateHeap();
        populateOutput();
        populateFileTable();
        populateSymbolTable();
        populateExecutionStack();
        populateProgramStatesIDs();
        populateBarrierTable();
        populateLockTable();
        populateLatchTable();
        populateToySemaphoreTable();
        populateCountSemaphoreTable();
    }

    private void populateBarrierTable() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        IBarrierTable barrierTable = programStates.getFirst().getBarrierTable();

        List<Pair<Integer, Pair<Integer, String>>> barrierTableList = new ArrayList<>();
        for (Map.Entry<Integer, Pair<Integer, String>> entry : barrierTable.getContentString().entrySet())
            barrierTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.barrierTableTableView.setItems(FXCollections.observableList(barrierTableList));
        this.barrierTableTableView.refresh();
    }

    private void populateLockTable() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        ILockTable lockTable = programStates.getFirst().getLockTable();

        List<Pair<Integer, Integer>> lockTableList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : lockTable.getContent().entrySet())
            lockTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.lockTableTableView.setItems(FXCollections.observableList(lockTableList));
        this.lockTableTableView.refresh();
    }

    private void populateLatchTable() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        ILatchTable latchTable = programStates.getFirst().getLatchTable();

        List<Pair<Integer, Integer>> latchTableList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : latchTable.getContent().entrySet())
            latchTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.latchTableTableView.setItems(FXCollections.observableList(latchTableList));
        this.latchTableTableView.refresh();
    }

    private void populateToySemaphoreTable() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        IToySemaphoreTable toySemaphoreTable = programStates.getFirst().getToySemaphoreTable();

        List<Pair<Integer, Pair<Integer, Pair<String, Integer>>>> semaphoreTableList = new ArrayList<>();
        for (Map.Entry<Integer, Pair<Integer, Pair<String, Integer>>> entry : toySemaphoreTable.getContentString().entrySet())
            semaphoreTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.toySemaphoreTableTableView.setItems(FXCollections.observableList(semaphoreTableList));
        this.toySemaphoreTableTableView.refresh();
    }

    private void populateCountSemaphoreTable() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        ICountSemaphoreTable countSemaphoreTable = programStates.getFirst().getCountSemaphoreTable();

        List<Pair<Integer, Pair<Integer, String>>> countSemaphoreTableList = new ArrayList<>();
        for (Map.Entry<Integer, Pair<Integer, String>> entry : countSemaphoreTable.getContentString().entrySet())
            countSemaphoreTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.countSemaphoreTableTableView.setItems(FXCollections.observableList(countSemaphoreTableList));
        this.countSemaphoreTableTableView.refresh();
    }

    private void populateHeap() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        IHeap heap = programStates.getFirst().getHeap();

        List<Pair<Integer, Value>> heapTableList = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry : heap.getContent().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.heapTableView.setItems(FXCollections.observableList(heapTableList));
        this.heapTableView.refresh();
    }

    private void populateOutput() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        List<Value> output = programStates.getFirst().getOutput().getList();
        List<String> outputList = new ArrayList<>();

        output.forEach(e -> outputList.add(e.toString()));

        this.outputListView.setItems(FXCollections.observableList(outputList));
        this.outputListView.refresh();
    }

    private void populateFileTable() {
        ProgramState currentProgram = this.getCurrentProgramState();

        if (currentProgram == null) {
            return;
        }

        ReadFileTable readFileTable = currentProgram.getReadFileTable();
        WriteFileTable writeFileTable = currentProgram.getWriteFileTable();

        Set<StringValue> readFileNames = readFileTable.getContent().keySet();
        Set<StringValue> writeFileNames = writeFileTable.getContent().keySet();

        List<String> fileTableList = new ArrayList<>();
        readFileNames.forEach(e -> fileTableList.add(e.getValue() + " - read"));
        writeFileNames.forEach(e -> fileTableList.add(e.getValue() + " - write"));

        this.fileTableListView.setItems(FXCollections.observableList(fileTableList));
        this.fileTableListView.refresh();
    }

    private void populateSymbolTable() {
        ProgramState currentProgram = this.getCurrentProgramState();

        if (currentProgram == null) {
            this.symbolTableTableView.getItems().clear();
            return;
        }

        SymbolTable symbolTable = currentProgram.getSymbolTable();

        List<Pair<String, Value>> symbolTableList = new ArrayList<>();
        for (Map.Entry<String, Value> entry : symbolTable.getContent().entrySet())
            symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.symbolTableTableView.setItems(FXCollections.observableList(symbolTableList));
        this.symbolTableTableView.refresh();
    }

    private void populateExecutionStack() {
        ProgramState currentProgram = this.getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();

        if (currentProgram == null) {
            this.executionStackListView.getItems().clear();

            if (!this.controller.programsLeftToExecuteExist())
                return;

            currentProgram = this.controller.getProgramStates().getFirst();
            this.programStatesListView.getSelectionModel().select(0);
            this.programStatesListView.getFocusModel().focus(0);
        }

        ExecutionStack executionStack = currentProgram.getExecutionStack();

        List<IStatement> executionStackContent = executionStack.getContent();

        executionStackContent.forEach(e -> executionStackListAsString.add(e.toString()));

        this.executionStackListView.setItems(FXCollections.observableList(executionStackListAsString));
        this.executionStackListView.refresh();
    }

    private void populateProgramStatesIDs() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        List<String> ids = programStates.stream().map(e -> String.valueOf(e.getID())).toList();

        this.programStatesListView.setItems(FXCollections.observableList(ids));

        this.programStatesTextField.setText(String.valueOf(ids.size()));
    }
}