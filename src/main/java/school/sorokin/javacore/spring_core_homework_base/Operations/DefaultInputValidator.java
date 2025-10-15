package school.sorokin.javacore.spring_core_homework_base.Operations;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DefaultInputValidator implements InputValidator {

    @Override
    public Long inputValidLong(String input, String field) {
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " не может быть пустым");
        }
        try {
            Long value = Long.parseLong(input);
            if (value < 0) {
                throw new IllegalArgumentException(value + " может быть отрицательным");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Для " + field + " введите число");
        }
    }

    @Override
    public BigDecimal validateBigDecimalInput(String input, String fieldName) {
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустой");
        }
        try {
            try {
                BigDecimal sum = new BigDecimal(input.trim());
                if (sum.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException(fieldName + "не может быть отрицательной");
                }
                return sum;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Для " + fieldName + " Введите корректную сумму");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Для продолжения введите число");
        }
    }

    @Override
    public String validateStringInput(String input, String fieldName) {
        return "";
    }
}
