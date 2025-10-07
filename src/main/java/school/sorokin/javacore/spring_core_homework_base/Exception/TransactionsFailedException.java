package school.sorokin.javacore.spring_core_homework_base.Exception;

public class TransactionsFailedException extends RuntimeException{
    public TransactionsFailedException(String message) {
        super(message);
    }
}
