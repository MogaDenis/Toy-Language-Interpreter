package map.interpreter_gui.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.expressions.ArithmeticExpression;
import map.interpreter_gui.model.expressions.ReadHeapExpression;
import map.interpreter_gui.model.expressions.RelationalExpression;
import map.interpreter_gui.model.expressions.ValueExpression;
import map.interpreter_gui.model.expressions.VariableExpression;
import map.interpreter_gui.model.statements.*;
import map.interpreter_gui.model.types.BoolType;
import map.interpreter_gui.model.types.CharType;
import map.interpreter_gui.model.types.IntType;
import map.interpreter_gui.model.types.ReferenceType;
import map.interpreter_gui.model.types.StringType;
import map.interpreter_gui.model.values.BoolValue;
import map.interpreter_gui.model.values.CharValue;
import map.interpreter_gui.model.values.IntValue;
import map.interpreter_gui.model.values.StringValue;

public class ProgramsRepository 
{
    private static final IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(2))), 
            new PrintStatement(new VariableExpression("v"))));

    private static final IStatement example2 = new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
            new ValueExpression(new IntValue(2)),new ArithmeticExpression('*',
            new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))), 
            new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression('+',
            new VariableExpression("a"), new ValueExpression(new IntValue(1)))), 
            new PrintStatement(new VariableExpression("b"))))));

    private static final IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
            new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
            new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
            IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
            VariableExpression("v"))))));

    private static final IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))), 
            new WhileStatement(new VariableExpression("a"), new CompoundStatement(new AssignmentStatement("x", 
            new ArithmeticExpression('+', new VariableExpression("x"), new ValueExpression(new IntValue(1)))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("x")), new AssignmentStatement("a", 
            new ValueExpression(new BoolValue(false)))))))));

    private static final IStatement example5 = new WhileStatement(new ValueExpression(new BoolValue(true)),
            new PrintStatement(new ValueExpression(new IntValue(0))));

    private static final IStatement example6 = new CompoundStatement(new VariableDeclarationStatement("var", new IntType()),
            new CompoundStatement(new AssignmentStatement("var", new ValueExpression(new IntValue(5))), 
            new WhileStatement(new RelationalExpression(new VariableExpression("var"), new ValueExpression(new IntValue(0)), 
            "!="), new CompoundStatement(new PrintStatement(new VariableExpression("var")), new AssignmentStatement("var", 
            new ArithmeticExpression('-', new VariableExpression("var"), new ValueExpression(new IntValue(1))))))));

    private static final IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()),
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))), 
            new CompoundStatement(new VariableDeclarationStatement("str", new StringType()), 
            new CompoundStatement(new AssignmentStatement("str", new ValueExpression(new StringValue("Hello, World!"))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("letter")), 
            new PrintStatement(new VariableExpression("str")))))));

    private static final IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("text", new StringType()),
            new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()), 
            new CompoundStatement(new AssignmentStatement("text", new ValueExpression(new StringValue("Hello,"))), 
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))),
            new CompoundStatement(new AssignmentStatement("text", new ArithmeticExpression('+', 
            new VariableExpression("text"), new ValueExpression(new StringValue(" World!")))), 
            new CompoundStatement(new AssignmentStatement("letter", new ArithmeticExpression('+', 
            new VariableExpression("letter"), new ValueExpression(new IntValue(1)))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("text")), new PrintStatement(new VariableExpression("letter")))))))));

    private static final IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("text", new StringType()),
            new CompoundStatement(new VariableDeclarationStatement("letter", new CharType()), 
            new CompoundStatement(new AssignmentStatement("text", new ValueExpression(new StringValue("Hello "))), 
            new CompoundStatement(new AssignmentStatement("letter", new ValueExpression(new CharValue('a'))), 
            new CompoundStatement(new AssignmentStatement("text", new ArithmeticExpression('+', new VariableExpression("text"),
            new VariableExpression("letter"))), new PrintStatement(new VariableExpression("text")))))));

    private static final IStatement example10 = new CompoundStatement(new VariableDeclarationStatement("filePath", new StringType()),
            new CompoundStatement(new AssignmentStatement("filePath", new ValueExpression(new StringValue("test.in"))),
            new CompoundStatement(new OpenReadFileStatement(new VariableExpression("filePath")), 
            new CompoundStatement(new VariableDeclarationStatement("value", new IntType()), 
            new CompoundStatement(new ReadFileStatement(new VariableExpression("filePath"), "value"),
            new CompoundStatement(new PrintStatement(new VariableExpression("value")),
            new CompoundStatement(new ReadFileStatement(new VariableExpression("filePath"), "value"),
            new CompoundStatement(new PrintStatement(new VariableExpression("value")), 
            new CloseReadFileStatement(new VariableExpression("filePath"))))))))));

    private static final IStatement example11 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(10))), 
            new PrintStatement(new VariableExpression("v"))));

    private static final IStatement example12 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), 
            new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))), 
            new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))), 
            new PrintStatement(new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v")), 
            new ValueExpression(new IntValue(5))))))));

    private static final IStatement example13 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), 
            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))), 
            new CompoundStatement(new NewStatement("a", new VariableExpression("v")), 
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))), 
            new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a")))))))));

    private static final IStatement example14 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())), 
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))), 
            new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))), 
            new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", 
            new ValueExpression(new IntValue(30))), new CompoundStatement(new AssignmentStatement("v", 
            new ValueExpression(new IntValue(32))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("v")), 
            new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))))), 
            new CompoundStatement(new PrintStatement(new VariableExpression("v")), 
            new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))))))));

    private static final IStatement example15 = new CompoundStatement(new VariableDeclarationStatement("var", new IntType()),
            new CompoundStatement(new AssignmentStatement("var", new ValueExpression(new IntValue(5))),
            new CompoundStatement(new OpenWriteFileStatement(new ValueExpression(new StringValue("output15.txt"))),
            new CompoundStatement(new WriteFileStatement(new ValueExpression(new StringValue("output15.txt")), new VariableExpression("var")),
            new CompoundStatement(new WriteFileStatement(new ValueExpression(new StringValue("output15.txt")), new ValueExpression(new StringValue("Hello, World!"))),
            new CloseWriteFileStatement(new ValueExpression(new StringValue("output15.txt"))))))));

    private static final IStatement example16 = new CompoundStatement(new VariableDeclarationStatement("var", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("filePath", new StringType()),
            new CompoundStatement(new AssignmentStatement("filePath", new ValueExpression(new StringValue("output16.txt"))),
            new CompoundStatement(new AssignmentStatement("var", new ValueExpression(new IntValue(5))),
            new CompoundStatement(new OpenWriteFileStatement(new VariableExpression("filePath")),
            new CompoundStatement(new WriteFileStatement(new VariableExpression("filePath"), new VariableExpression("var")),
            new CompoundStatement(new WriteFileStatement(new VariableExpression("filePath"), new ValueExpression(new StringValue("Hello, World!"))),
            new CloseWriteFileStatement(new VariableExpression("filePath")))))))));

    public static final List<IStatement> programs = Arrays.asList(example1, example2, example3, example4, example5, example6, example7, example8, example9,
            example10, example11, example12, example13, example14, example15, example16);

    public static List<IStatement> getProgramsAsStatements() {
        return programs;
    }

    public List<ProgramState> getProgramsStates()
    {
        List<ProgramState> programsStates = new ArrayList<>();

        for (IStatement program : programs)
        {
            try
            {
                ProgramState newProgramState = new ProgramState(program);
                programsStates.add(newProgramState);
            }
            catch (TypeException ignored)
            {

            }
        }

        return programsStates;
    }
}
