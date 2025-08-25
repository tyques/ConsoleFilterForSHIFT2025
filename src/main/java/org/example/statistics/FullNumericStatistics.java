package org.example.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FullNumericStatistics implements Statistics {
    private final String typeName;
    private long count = 0;
    private BigDecimal min = null;
    private BigDecimal max = null;
    private BigDecimal sum = BigDecimal.ZERO;

    public FullNumericStatistics(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void recordValue(String value) {
        BigDecimal number = new BigDecimal(value);
        if (min == null || number.compareTo(min) < 0) {
            min = number;
        }
        if (max == null || number.compareTo(max) > 0) {
            max = number;
        }
        sum = sum.add(number);
        count++;
    }

    @Override
    public String getReport() {
        if (count == 0) {
            return String.format("--- Полная статистика по '%s' ---\nЭлементы не найдены.", typeName);
        }
        BigDecimal average = sum.divide(BigDecimal.valueOf(count), 10, RoundingMode.HALF_UP);
        return String.format(
                """
                        --- Полная статистика по '%s' ---
                        Количество элементов: %d
                        Минимальное значение: %s
                        Максимальное значение: %s
                        Сумма: %s
                        Среднее: %s""",
                typeName, count, min.toPlainString(), max.toPlainString(), sum.toPlainString(), average.toPlainString()
        );
    }

    @Override
    public long getCount() {
        return count;
    }
}
