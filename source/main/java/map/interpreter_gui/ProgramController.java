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
    private Button oneStepButton;

    @FXML
    private Controller controller;


    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    private ProgramState getCurrentProgramState(){
        if (this.controller.getProgramStates().isEmpty())
            return null;

        int currentId = programStatesListView.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return this.controller.getProgramStates().getFirst();

        return this.controller.getProgramStates().get(currentId);
    }

    @FXML
    public void initialize() {
        this.heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        this.symbolTableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        this.symbolTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        this.oneStepButton.setOnAction(actionEvent -> {
            if(this.controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            boolean emptyExecutionStack = Objects.requireNonNull(getCurrentProgramState()).getExecutionStack().isEmpty();

            if (emptyExecutionStack) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            try {
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
    }

    private void populateHeap() {
        List<ProgramState> programStates = this.controller.getProgramStates();

        if (programStates.isEmpty()) {
            this.heapTableView.getItems().clear();
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
            this.outputListView.getItems().clear();
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
            this.fileTableListView.getItems().clear();
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
            return;
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