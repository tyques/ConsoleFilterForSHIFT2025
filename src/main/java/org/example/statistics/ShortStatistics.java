package org.example.statistics;

public class ShortStatistics implements Statistics {
    private final String typeName;
    private long count = 0;

    public ShortStatistics(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public void recordValue(String value) {
        count++;
    }

    @Override
    public String getReport() {
        return String.format("--- Краткая статистика по '%s' ---\nКоличество элементов: %d", typeName, count);
    }

    @Override
    public long getCount() {
        return count;
    }
}
