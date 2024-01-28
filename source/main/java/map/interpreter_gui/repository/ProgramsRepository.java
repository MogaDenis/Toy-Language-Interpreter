package map.interpreter_gui.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import map.interpreter_gui.model.ProgramState;
import map.interpreter_gui.model.exceptions.TypeException;
import map.interpreter_gui.model.expressions.*;
import map.interpreter_gui.model.statements.*;
import map.interpreter_gui.model.statements.barrier_statements.AwaitBarrierStatement;
import map.interpreter_gui.model.statements.barrier_statements.NewBarrierStatement;
import map.interpreter_gui.model.statements.conditional_statements.ConditionalAssignmentStatement;
import map.interpreter_gui.model.statements.conditional_statements.IfStatement;
import map.interpreter_gui.model.statements.conditional_statements.SwitchStatement;
import map.interpreter_gui.model.statements.count_semaphore_statements.AcquireCountSemaphoreStatement;
import map.interpreter_gui.model.statements.count_semaphore_statements.NewCountSemaphoreStatement;
import map.interpreter_gui.model.statements.count_semaphore_statements.ReleaseCountSemaphoreStatement;
import map.interpreter_gui.model.statements.declaration_statements.VariableDeclarationStatement;
import map.interpreter_gui.model.statements.declaration_statements.VariableDeclarationWithInitializerStatement;
import map.interpreter_gui.model.statements.latch_statements.AwaitLatchStatement;
import map.interpreter_gui.model.statements.latch_statements.CountDownLatchStatement;
import map.interpreter_gui.model.statements.latch_statements.NewLatchStatement;
import map.interpreter_gui.model.statements.lock_statements.LockStatement;
import map.interpreter_gui.model.statements.lock_statements.NewLockStatement;
import map.interpreter_gui.model.statements.lock_statements.UnlockStatement;
import map.interpreter_gui.model.statements.procedure_statements.CallProcedureStatement;
import map.interpreter_gui.model.statements.procedure_statements.NewProcedureStatement;
import map.interpreter_gui.model.statements.read_file_statements.CloseReadFileStatement;
import map.interpreter_gui.model.statements.read_file_statements.OpenReadFileStatement;
import map.interpreter_gui.model.statements.read_file_statements.ReadFileStatement;
import map.interpreter_gui.model.statements.repetitive_statements.ForStatement;
import map.interpreter_gui.model.statements.repetitive_statements.RepeatUntilStatement;
import map.interpreter_gui.model.statements.repetitive_statements.WhileStatement;
import map.interpreter_gui.model.statements.toy_semaphore_statements.AcquireToySemaphoreStatement;
import map.interpreter_gui.model.statements.toy_semaphore_statements.NewToySemaphoreStatement;
import map.interpreter_gui.model.statements.toy_semaphore_statements.ReleaseToySemaphoreStatement;
import map.interpreter_gui.model.statements.write_file_statements.CloseWriteFileStatement;
import map.interpreter_gui.model.statements.write_file_statements.OpenWriteFileStatement;
import map.interpreter_gui.model.statements.write_file_statements.WriteFileStatement;
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

    private static final IStatement example17 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("y", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
            new CompoundStatement(new RepeatUntilStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "=="),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                    new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                    new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
            new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
            new CompoundStatement(new NoOperationStatement(),
            new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
            new CompoundStatement(new NoOperationStatement(),
            new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"),
            new ValueExpression(new IntValue(10)))))))))))));

    private static final IStatement example18 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
            new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(2))),
            new CompoundStatement(new NewStatement("v2", new ValueExpression(new IntValue(3))),
            new CompoundStatement(new NewStatement("v3", new ValueExpression(new IntValue(4))),
            new CompoundStatement(new NewBarrierStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitBarrierStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))), new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new AwaitBarrierStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))), new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))), new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))))))),
            new CompoundStatement(new AwaitBarrierStatement("cnt"), new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))))))))))))));

    private static final IStatement example19 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new ForStatement("v", new ValueExpression(new IntValue(0)), new ValueExpression(new IntValue(3)), new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))),
            new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignmentStatement("v", new ArithmeticExpression('+', new VariableExpression("v"), new ValueExpression(new IntValue(1))))))),
            new PrintStatement(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10)))))));

    private static final IStatement example20 = new CompoundStatement(new VariableDeclarationStatement("cond", new BoolType()),
            new CompoundStatement(new VariableDeclarationStatement("number", new IntType()),
            new CompoundStatement(new AssignmentStatement("cond", new ValueExpression(new BoolValue(true))),
            new CompoundStatement(new ConditionalAssignmentStatement("number", new VariableExpression("cond"), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
            new CompoundStatement(new PrintStatement(new VariableExpression("number")),
            new CompoundStatement(new ConditionalAssignmentStatement("number", new ValueExpression(new BoolValue(false)), new ValueExpression(new IntValue(100)), new ValueExpression(new IntValue(200))),
            new PrintStatement(new VariableExpression("number"))))))));

    private static final IStatement example21 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("c", new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
            new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
            new CompoundStatement(new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
            new CompoundStatement(new SwitchStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
            new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")), new ValueExpression(new IntValue(10)),
            new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
            new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
            new PrintStatement(new ValueExpression(new IntValue(300)))),
            new CompoundStatement(new SleepStatement(new IntValue(2)),
            new PrintStatement(new ValueExpression(new IntValue(300)))))))))));

    private static final IStatement example22 = new CompoundStatement(new VariableDeclarationWithInitializerStatement("a", new IntType(), new ValueExpression(new IntValue(1))),
            new CompoundStatement(new VariableDeclarationWithInitializerStatement("b", new IntType(), new ValueExpression(new IntValue(2))),
            new CompoundStatement(new VariableDeclarationWithInitializerStatement("c", new IntType(), new ValueExpression(new IntValue(5))),
            new CompoundStatement(new SwitchStatement(new ArithmeticExpression('*', new VariableExpression("a"), new ValueExpression(new IntValue(10))),
            new ArithmeticExpression('*', new VariableExpression("b"), new VariableExpression("c")), new ValueExpression(new IntValue(10)),
            new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
            new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
            new PrintStatement(new ValueExpression(new IntValue(300)))),
            new PrintStatement(new ValueExpression(new IntValue(300)))))));

    private static final IStatement example23 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("x", new IntType()),
            new CompoundStatement(new VariableDeclarationStatement("q", new IntType()),
            new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(20))),
            new CompoundStatement(new NewStatement("v2", new ValueExpression(new IntValue(30))),
            new CompoundStatement(new NewLockStatement("x"),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("x"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))), new UnlockStatement("x")))),
                    new CompoundStatement(new LockStatement("x"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))), new UnlockStatement("x"))))),
            new CompoundStatement(new NewLockStatement("q"),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new LockStatement("q"), new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression('+', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(5)))), new UnlockStatement("q")))),
                    new CompoundStatement(new LockStatement("q"), new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))), new UnlockStatement("q"))))),
            new CompoundStatement(new NoOperationStatement(),
            new CompoundStatement(new NoOperationStatement(),
            new CompoundStatement(new NoOperationStatement(),
            new CompoundStatement(new NoOperationStatement(),
            new CompoundStatement(new LockStatement("x"),
            new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
            new CompoundStatement(new UnlockStatement("x"),
            new CompoundStatement(new LockStatement("q"),
            new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
            new UnlockStatement("q"))))))))))))))))))));

    private static final IStatement example24 = new IfStatement(new ValueExpression(new IntValue(0)),
            new PrintStatement(new ValueExpression(new StringValue("yes"))),
            new PrintStatement(new ValueExpression(new StringValue("no"))));

    private static final IStatement example25 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("v2", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("v3", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("cnt",new IntType()),
            new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(2))),
            new CompoundStatement(new NewStatement("v2", new ValueExpression(new IntValue(3))),
            new CompoundStatement(new NewStatement("v3", new ValueExpression(new IntValue(4))),
            new CompoundStatement(new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                    new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new CompoundStatement(new CountDownLatchStatement("cnt"),
                            new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                    new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))), new CompoundStatement(new CountDownLatchStatement("cnt"),
                                            new ForkStatement(new CompoundStatement(new WriteHeapStatement("v3", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))), new CountDownLatchStatement("cnt")))))))), new NoOperationStatement()))))),
            new CompoundStatement(new AwaitLatchStatement("cnt"), new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new CompoundStatement(new CountDownLatchStatement("cnt"), new PrintStatement(new ValueExpression(new IntValue(100)))))))))))))));

    private static final IStatement example26 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
            new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(2))),
            new CompoundStatement(new NewToySemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireToySemaphoreStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseToySemaphoreStatement("cnt"))))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireToySemaphoreStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseToySemaphoreStatement("cnt")))))),
            new CompoundStatement(new AcquireToySemaphoreStatement("cnt"),
            new CompoundStatement(new PrintStatement(new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
            new ReleaseToySemaphoreStatement("cnt")))))))));

    private static final IStatement example27 = new CompoundStatement(new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
            new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
            new CompoundStatement(new NewStatement("v1", new ValueExpression(new IntValue(1))),
            new CompoundStatement(new NewCountSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireCountSemaphoreStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseCountSemaphoreStatement("cnt"))))),
            new CompoundStatement(new ForkStatement(new CompoundStatement(new AcquireCountSemaphoreStatement("cnt"), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))), new CompoundStatement(new WriteHeapStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(2)))), new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))), new ReleaseCountSemaphoreStatement("cnt")))))),
            new CompoundStatement(new AcquireCountSemaphoreStatement("cnt"),
            new CompoundStatement(new PrintStatement(new ArithmeticExpression('-', new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)))),
            new ReleaseCountSemaphoreStatement("cnt")))))))));

    private static final IStatement example28 = new CompoundStatement(new VariableDeclarationWithInitializerStatement("v1", new IntType(), new ValueExpression(new IntValue(2))),
            new CompoundStatement(new VariableDeclarationWithInitializerStatement("v2", new IntType(), new ValueExpression(new IntValue(3))),
            new IfStatement(new VariableExpression("v1"), new PrintStatement(new MULExpression(new VariableExpression("v1"), new VariableExpression("v2"))), new PrintStatement(new VariableExpression("v1")))));

    private static final IStatement example29 = new CompoundStatement(new NewProcedureStatement("sum", Arrays.asList("a", "b"), new CompoundStatement(new VariableDeclarationWithInitializerStatement("v", new IntType(), new ArithmeticExpression('+', new VariableExpression("a"), new VariableExpression("b"))), new PrintStatement(new VariableExpression("v")))),
            new CompoundStatement(new NewProcedureStatement("product", Arrays.asList("a", "b"), new CompoundStatement(new VariableDeclarationWithInitializerStatement("v", new IntType(), new ArithmeticExpression('*', new VariableExpression("a"), new VariableExpression("b"))), new PrintStatement(new VariableExpression("v")))),
            new CompoundStatement(new VariableDeclarationWithInitializerStatement("v", new IntType(), new ValueExpression(new IntValue(2))),
            new CompoundStatement(new VariableDeclarationWithInitializerStatement("w", new IntType(), new ValueExpression(new IntValue(5))),
            new CompoundStatement(new CallProcedureStatement("sum", Arrays.asList(new ArithmeticExpression('*', new VariableExpression("v"), new ValueExpression(new IntValue(10))), new VariableExpression("w"))),
            new CompoundStatement(new PrintStatement(new VariableExpression("v")),
            new CompoundStatement(new ForkStatement(new CallProcedureStatement("product", Arrays.asList(new VariableExpression("v"), new VariableExpression("w")))),
            new ForkStatement(new CallProcedureStatement("sum", Arrays.asList(new VariableExpression("v"), new VariableExpression("w")))))))))));

    public static final List<IStatement> programs = Arrays.asList(example1, example2, example3, example4, example5,
            example6, example7, example8, example9, example10, example11, example12, example13, example14, example15,
            example16, example17, example18, example19, example20, example21, example22, example23, example24,
            example25, example26, example27, example28, example29);

    public static List<IStatement> getProgramsAsStatements() {
        return programs;
    }

    public static List<ProgramState> getProgramsStates()
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
