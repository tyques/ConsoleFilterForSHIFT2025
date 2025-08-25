package org.example.statistics;

public interface Statistics {
    void recordValue(String value);

    String getReport();

    long getCount();
}
