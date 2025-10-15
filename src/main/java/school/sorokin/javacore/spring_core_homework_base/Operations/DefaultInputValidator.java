package school.sorokin.javacore.spring_core_homework_base.Operations;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DefaultInputValidator implements InputValidator {

    @Override
    public Long inputValidLong(String input, String field) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " не может быть пустым");
        }
        try {
            Long value = Long.parseLong(input);
            if (value < 0) {
                throw new IllegalArgumentException(value + " не может быть отрицательным");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Для " + field + " введите число");
        }
    }

    @Override
    public BigDecimal validateBigDecimalInput(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустой");
        }
        try {
            BigDecimal sum = new BigDecimal(input.trim());
            if (sum.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(fieldName + "не может быть отрицательной");
            }
            return sum;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Для " + fieldName + " Введите корректную сумму");
        }
    }

    @Override
    public String validateStringInput(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        return input.trim();
    }

    @Override
    public int validatePageSize(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Размер страницы не может быть пустым");
        }
        try {
            int size = Integer.parseInt(input.trim());
            if (size <= 0) {
                throw new IllegalArgumentException("Размер страницы должен быть положительным");
            }
            return size;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите корректный размер страницы");
        }
    }
}
