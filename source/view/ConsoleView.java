package source.view;

import java.util.Scanner;
import java.util.Vector;
import source.controller.Controller;
import source.model.ProgramState;
import source.model.statements.*;
import source.model.expressions.*;
import source.model.types.BoolType;
import source.model.types.IntType;
import source.model.values.BoolValue;
import source.model.values.IntValue;
import source.repository.*;


public class ConsoleView 
{
    private Scanner scanner;
    private Controller controller;
    private ProgramState currentProgram;
    private Vector<Integer> validOptions;
    private Repository repository;

    private IStatement chosenExampleProgram;

    private IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), 
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), 
            new PrintStatement(new VariableExpression("v"))));

    private IStatement example2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
            new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',
            new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), 
            new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',
            new VariableExpression("a"), new ValueExpression(new IntValue(1)))), 
            new PrintStatement(new VariableExpression("b"))))));

    private IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
            new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
            new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
            IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
            VariableExpression("v"))))));

    private IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("x", new IntType()), 
            new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), 
            new WhileStatement(new VariableExpression("a"), new CompoundStatement(new AssignmentStatement("x", 
            new ArithmeticExpression('+', new VariableExpression("x"), new ValueExpression(new IntValue(1)))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("x")), new AssignmentStatement("a", 
            new ValueExpression(new BoolValue(false)))))))));

    private IStatement example5 = new WhileStatement(new ValueExpression(new BoolValue(true)), 
            new PrintStatement(new ValueExpression(new IntValue(0))));

    private IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("var", new IntType()), 
            new CompoundStatement(new AssignmentStatement("var", new ValueExpression(new IntValue(5))), 
            new WhileStatement(new RelationalExpression(new VariableExpression("var"), new ValueExpression(new IntValue(0)), 
            "!="), new CompoundStatement(new PrintStatement(new VariableExpression("var")), new AssignmentStatement("var", 
            new ArithmeticExpression('-', new VariableExpression("var"), new ValueExpression(new IntValue(1))))))));

    public ConsoleView()
    {
        this.scanner = new Scanner(System.in);
        this.controller = null;
        this.currentProgram = null;
        this.validOptions = new Vector<Integer>();
        this.repository = null;
        this.chosenExampleProgram = null;

        for (int i = 0; i < 6; i++)
            this.validOptions.add(i);
    }

    public void showMenu()
    {
        System.out.println("\n~ Main menu ~\n");

        System.out.println("1 - Show the current's program source code.");
        System.out.println("2 - Change the program.");
        System.out.println("3 - Show the current state of the program.");
        System.out.println("4 - Execute a step.");
        System.out.println("5 - Execute the entire program.");
        System.out.println("0 - Close the interpreter.\n");
    }

    public void showProgramsMenu()
    {
        System.out.println("\n~ Choose program ~\n");

        System.out.println("1 - Program 1");
        System.out.println("2 - Program 2");
        System.out.println("3 - Program 3");
        System.out.println("4 - program 4");
        System.out.println("5 - program 5");
        System.out.println("6 - program 6");
        System.out.println("0 - Cancel\n");
    }

    public Integer readOption(Vector<Integer> validOptions) throws Exception
    {
        System.out.print(">> ");

        Integer userOption = this.scanner.nextInt();

        if (validOptions.contains(userOption) == false)
            throw new Exception("Invalid input!");

        return userOption;
    }

    public void printCurrentProgramState()
    {
        System.out.println(this.controller.getProgramStateString());
    }

    public void changeCurrentProgram()
    {
        Vector<Integer> validProgramOptions = new Vector<>();
        for (int i = 0; i < 7; i++)
            validProgramOptions.add(i);

        Integer programChoice = 0;

        try 
        {
            this.showProgramsMenu();
            programChoice = this.readOption(validProgramOptions);
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        if (programChoice == 0)
            return;

        else if (programChoice == 1)
        {
            this.currentProgram = new ProgramState(this.example1);
            this.chosenExampleProgram = this.example1;
        }
        else if (programChoice == 2)
        {
            this.currentProgram = new ProgramState(this.example2);
            this.chosenExampleProgram = this.example2;
        }
        else if (programChoice == 3)
        {
            this.currentProgram = new ProgramState(this.example3);
            this.chosenExampleProgram = this.example3;
        }
        else if (programChoice == 4)
        {
            this.currentProgram = new ProgramState(this.example4);
            this.chosenExampleProgram = this.example4;
        }
        else if (programChoice == 5)
        {
            this.currentProgram = new ProgramState(this.example5);
            this.chosenExampleProgram = this.example5;
        }
        else if (programChoice == 6)
        {
            this.currentProgram = new ProgramState(this.example6);
            this.chosenExampleProgram = this.example6;
        }

        this.repository = new InMemoryRepository(this.currentProgram);
        this.controller = new Controller(this.repository);
    }

    public void run()
    {
        this.currentProgram = new ProgramState(this.example1);
        this.chosenExampleProgram = this.example1;

        this.repository = new InMemoryRepository(this.currentProgram);
        this.controller = new Controller(this.repository);

        while (true)
        {
            try 
            {
                this.showMenu();

                Integer userOption = this.readOption(this.validOptions);

                if (userOption == 0)
                    break;

                else if (userOption == 1)
                    System.out.println("\nSource code:\n\n" + this.chosenExampleProgram.toString());

                else if (userOption == 2)
                    this.changeCurrentProgram();

                else if (userOption == 3)
                    this.printCurrentProgramState();

                else if (userOption == 4)
                    this.controller.oneStep();
                
                else if (userOption == 5)
                    this.controller.allSteps();
                
            }
            catch (Exception exception)
            {
                System.out.println(exception.getMessage());
            }
        }
    }
}
