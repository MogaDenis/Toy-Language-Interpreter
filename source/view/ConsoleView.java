package source.view;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import source.controller.Controller;
import source.model.ProgramState;
import source.model.statements.*;
import source.model.expressions.*;
import source.model.types.BoolType;
import source.model.types.CharType;
import source.model.types.IntType;
import source.model.types.StringType;
import source.model.values.BoolValue;
import source.model.values.CharValue;
import source.model.values.IntValue;
import source.model.values.StringValue;
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

    private IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()),
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))), 
            new CompoundStatement(new VariableDeclarationStatement("str", new StringType()), 
            new CompoundStatement(new AssignmentStatement("str", new ValueExpression(new StringValue("Hello, World!"))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("letter")), 
            new PrintStatement(new VariableExpression("str")))))));

    private IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("text", new StringType()), 
            new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()), 
            new CompoundStatement(new AssignmentStatement("text", new ValueExpression(new StringValue("Hello,"))), 
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))),
            new CompoundStatement(new AssignmentStatement("text", new ArithmeticExpression('+', 
            new VariableExpression("text"), new ValueExpression(new StringValue(" World!")))), 
            new CompoundStatement(new AssignmentStatement("letter", new ArithmeticExpression('+', 
            new VariableExpression("letter"), new ValueExpression(new IntValue(1)))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("text")), new PrintStatement(new VariableExpression("letter")))))))));

    private IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("text", new StringType()), 
            new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()), 
            new CompoundStatement(new AssignmentStatement("text", new ValueExpression(new StringValue("Hello "))), 
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))), 
            new CompoundStatement(new AssignmentStatement("text", new ArithmeticExpression('+', new VariableExpression("text"),
            new VariableExpression("letter"))), new PrintStatement(new VariableExpression("text")))))));

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

        System.out.println("1 - Program 1 - Basic declaration and print.");
        System.out.println("2 - Program 2 - Arithmetic expressions.");
        System.out.println("3 - Program 3 - If else statement.");
        System.out.println("4 - program 4 - While statement.");
        System.out.println("5 - program 5 - While (infinite loop).");
        System.out.println("6 - program 6 - While (print numbers).");
        System.out.println("7 - program 7 - String and Char.");
        System.out.println("8 - program 8 - String and Char arithmetic.");
        System.out.println("9 - program 9 - String and Char concatenation.");
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

    public void changeCurrentProgram() throws IOException
    {
        Vector<Integer> validProgramOptions = new Vector<>();
        for (int i = 0; i < 10; i++)
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
        else if (programChoice == 7)
        {
            this.currentProgram = new ProgramState(this.example7);
            this.chosenExampleProgram = this.example7;
        }
        else if (programChoice == 8)
        {
            this.currentProgram = new ProgramState(this.example8);
            this.chosenExampleProgram = this.example8;
        }
        else if (programChoice == 9)
        {
            this.currentProgram = new ProgramState(this.example9);
            this.chosenExampleProgram = this.example9;
        }

        this.repository = new InMemoryRepository(this.currentProgram, "log.txt");
        this.controller = new Controller(this.repository);
    }

    public void run() throws IOException
    {
        this.currentProgram = new ProgramState(this.example1);
        this.chosenExampleProgram = this.example1;

        this.repository = new InMemoryRepository(this.currentProgram, "log.txt");
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
