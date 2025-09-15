package school.sorokin.javacore.spring_core_homework_base.Operations;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}
