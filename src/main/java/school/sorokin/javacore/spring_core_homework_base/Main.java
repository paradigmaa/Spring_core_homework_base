package school.sorokin.javacore.spring_core_homework_base;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school.sorokin.javacore.spring_core_homework_base.Controller.OperationsConsoleListener;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("school/sorokin/javacore/spring_core_homework_base");
        OperationsConsoleListener operationsConsoleListener = context.getBean(OperationsConsoleListener.class);
        operationsConsoleListener.run();

    }
}
