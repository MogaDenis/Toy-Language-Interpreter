package source;

import source.controller.Controller;
import source.model.ProgramState;
import source.model.expressions.ArithmeticExpression;
import source.model.expressions.ReadHeapExpression;
import source.model.expressions.RelationalExpression;
import source.model.expressions.ValueExpression;
import source.model.expressions.VariableExpression;
import source.model.statements.AssignmentStatement;
import source.model.statements.CloseFileStatement;
import source.model.statements.CompoundStatement;
import source.model.statements.IStatement;
import source.model.statements.IfStatement;
import source.model.statements.NewStatement;
import source.model.statements.OpenReadFileStatement;
import source.model.statements.PrintStatement;
import source.model.statements.ReadFileStatement;
import source.model.statements.VariableDeclarationStatement;
import source.model.statements.WhileStatement;
import source.model.statements.WriteHeapStatement;
import source.model.types.BoolType;
import source.model.types.CharType;
import source.model.types.IntType;
import source.model.types.ReferenceType;
import source.model.types.StringType;
import source.model.values.BoolValue;
import source.model.values.CharValue;
import source.model.values.IntValue;
import source.model.values.StringValue;
import source.repository.InMemoryRepository;
import source.repository.Repository;
import source.view.ExitCommand;
import source.view.RunExampleCommand;
import source.view.TextMenu;

public class Interpreter 
{
    private static IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()), 
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), 
            new PrintStatement(new VariableExpression("v"))));

    private static IStatement example2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
            new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',
            new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), 
            new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',
            new VariableExpression("a"), new ValueExpression(new IntValue(1)))), 
            new PrintStatement(new VariableExpression("b"))))));

    private static IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
            new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
            new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
            IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
            VariableExpression("v"))))));

    private static IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("x", new IntType()), 
            new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), 
            new WhileStatement(new VariableExpression("a"), new CompoundStatement(new AssignmentStatement("x", 
            new ArithmeticExpression('+', new VariableExpression("x"), new ValueExpression(new IntValue(1)))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("x")), new AssignmentStatement("a", 
            new ValueExpression(new BoolValue(false)))))))));

    private static IStatement example5 = new WhileStatement(new ValueExpression(new BoolValue(true)), 
            new PrintStatement(new ValueExpression(new IntValue(0))));

    private static IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("var", new IntType()), 
            new CompoundStatement(new AssignmentStatement("var", new ValueExpression(new IntValue(5))), 
            new WhileStatement(new RelationalExpression(new VariableExpression("var"), new ValueExpression(new IntValue(0)), 
            "!="), new CompoundStatement(new PrintStatement(new VariableExpression("var")), new AssignmentStatement("var", 
            new ArithmeticExpression('-', new VariableExpression("var"), new ValueExpression(new IntValue(1))))))));

    private static IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()),
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))), 
            new CompoundStatement(new VariableDeclarationStatement("str", new StringType()), 
            new CompoundStatement(new AssignmentStatement("str", new ValueExpression(new StringValue("Hello, World!"))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("letter")), 
            new PrintStatement(new VariableExpression("str")))))));

    private static IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("text", new StringType()), 
            new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()), 
            new CompoundStatement(new AssignmentStatement("text", new ValueExpression(new StringValue("Hello,"))), 
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))),
            new CompoundStatement(new AssignmentStatement("text", new ArithmeticExpression('+', 
            new VariableExpression("text"), new ValueExpression(new StringValue(" World!")))), 
            new CompoundStatement(new AssignmentStatement("letter", new ArithmeticExpression('+', 
            new VariableExpression("letter"), new ValueExpression(new IntValue(1)))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("text")), new PrintStatement(new VariableExpression("letter")))))))));

    private static IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("text", new StringType()), 
            new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()), 
            new CompoundStatement(new AssignmentStatement("text", new ValueExpression(new StringValue("Hello "))), 
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))), 
            new CompoundStatement(new AssignmentStatement("text", new ArithmeticExpression('+', new VariableExpression("text"),
            new VariableExpression("letter"))), new PrintStatement(new VariableExpression("text")))))));

    private static IStatement example10 = new CompoundStatement(new VariableDeclarationStatement("filePath", new StringType()),
            new CompoundStatement(new AssignmentStatement("filePath", new ValueExpression(new StringValue("test.in"))),
            new CompoundStatement(new OpenReadFileStatement(new VariableExpression("filePath")), 
            new CompoundStatement(new VariableDeclarationStatement("value", new IntType()), 
            new CompoundStatement(new ReadFileStatement(new VariableExpression("filePath"), "value"),
            new CompoundStatement(new PrintStatement(new VariableExpression("value")),
            new CompoundStatement(new ReadFileStatement(new VariableExpression("filePath"), "value"),
            new CompoundStatement(new PrintStatement(new VariableExpression("value")), 
            new CloseFileStatement(new VariableExpression("filePath"))))))))));

    private static IStatement example11 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), 
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(10))), 
            new PrintStatement(new VariableExpression("v"))));

    private static IStatement example12 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), 
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), 
            new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), 
            new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))), 
            new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v")), 
            new ValueExpression(new IntValue(5))))))));

    private static IStatement example13 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())), 
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), 
            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))), 
            new CompoundStatement(new NewStatement("a", new VariableExpression("v")), 
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))), 
            new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

    public static void main(String[] args) throws Exception
    {
        ProgramState program1 = new ProgramState(example1);        
        ProgramState program2 = new ProgramState(example2);        
        ProgramState program3 = new ProgramState(example3);        
        ProgramState program4 = new ProgramState(example4);        
        ProgramState program5 = new ProgramState(example5);        
        ProgramState program6 = new ProgramState(example6);        
        ProgramState program7 = new ProgramState(example7);        
        ProgramState program8 = new ProgramState(example8);        
        ProgramState program9 = new ProgramState(example9);        
        ProgramState program10 = new ProgramState(example10);  
        ProgramState program11 = new ProgramState(example11);
        ProgramState program12 = new ProgramState(example12);
        ProgramState program13 = new ProgramState(example13);

        Repository repository1 = new InMemoryRepository(program1, "log1.txt");
        Repository repository2 = new InMemoryRepository(program2, "log2.txt");
        Repository repository3 = new InMemoryRepository(program3, "log3.txt");
        Repository repository4 = new InMemoryRepository(program4, "log4.txt");
        Repository repository5 = new InMemoryRepository(program5, "log5.txt");
        Repository repository6 = new InMemoryRepository(program6, "log6.txt");
        Repository repository7 = new InMemoryRepository(program7, "log7.txt");
        Repository repository8 = new InMemoryRepository(program8, "log8.txt");
        Repository repository9 = new InMemoryRepository(program9, "log9.txt");
        Repository repository10 = new InMemoryRepository(program10, "log10.txt");
        Repository repository11 = new InMemoryRepository(program11, "log11.txt");
        Repository repository12 = new InMemoryRepository(program12, "log12.txt");
        Repository repository13 = new InMemoryRepository(program13, "log13.txt");

        Controller controller1 = new Controller(repository1);
        Controller controller2 = new Controller(repository2);
        Controller controller3 = new Controller(repository3);
        Controller controller4 = new Controller(repository4);
        Controller controller5 = new Controller(repository5);
        Controller controller6 = new Controller(repository6);
        Controller controller7 = new Controller(repository7);
        Controller controller8 = new Controller(repository8);
        Controller controller9 = new Controller(repository9);
        Controller controller10 = new Controller(repository10);
        Controller controller11 = new Controller(repository11);
        Controller controller12 = new Controller(repository12);
        Controller controller13 = new Controller(repository13);

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand(0, "Exit the application."));
        textMenu.addCommand(new RunExampleCommand(1, example1.toString(), controller1));
        textMenu.addCommand(new RunExampleCommand(2, example2.toString(), controller2));
        textMenu.addCommand(new RunExampleCommand(3, example3.toString(), controller3));
        textMenu.addCommand(new RunExampleCommand(4, example4.toString(), controller4));
        textMenu.addCommand(new RunExampleCommand(5, example5.toString(), controller5));
        textMenu.addCommand(new RunExampleCommand(6, example6.toString(), controller6));
        textMenu.addCommand(new RunExampleCommand(7, example7.toString(), controller7));
        textMenu.addCommand(new RunExampleCommand(8, example8.toString(), controller8));
        textMenu.addCommand(new RunExampleCommand(9, example9.toString(), controller9));
        textMenu.addCommand(new RunExampleCommand(10, example10.toString(), controller10));
        textMenu.addCommand(new RunExampleCommand(11, example11.toString(), controller11));
        textMenu.addCommand(new RunExampleCommand(12, example12.toString(), controller12));
        textMenu.addCommand(new RunExampleCommand(13, example13.toString(), controller13));

        textMenu.show();
    }
}
