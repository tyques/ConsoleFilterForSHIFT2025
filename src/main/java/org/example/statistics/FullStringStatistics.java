package org.example.statistics;

public class FullStringStatistics implements Statistics {
    private final String typeName;
    private long count = 0;
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = 0;

    public FullStringStatistics(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void recordValue(String value) {
        int len = value.length();
        if (len < minLength) {
            minLength = len;
        }
        if (len > maxLength) {
            maxLength = len;
        }
        count++;
    }

    @Override
    public String getReport() {
        if (count == 0) {
            return String.format("--- Полная статистика по '%s' ---\nЭлементы не найдены.", typeName);
        }
        return String.format(
                """
                        --- Полная статистика по '%s' ---
                        Количество элементов: %d
                        Самая короткая строка (длина): %d
                        Самая длинная строка (длина): %d""",
                typeName, count, minLength, maxLength
        );
    }

    @Override
    public long getCount() {
        return count;
    }
}