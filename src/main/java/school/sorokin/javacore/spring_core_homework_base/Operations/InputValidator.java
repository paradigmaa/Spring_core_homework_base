package school.sorokin.javacore.spring_core_homework_base.Operations;

import java.math.BigDecimal;

public interface InputValidator {
Long inputValidLong(String input, String field);
    BigDecimal validateBigDecimalInput(String input, String fieldName);
    String validateStringInput(String input, String fieldName);
    int validatePageSize(String input);
}
