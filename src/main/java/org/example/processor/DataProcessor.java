package org.example.processor;

import org.example.statistics.Statistics;

import java.io.IOException;
import java.util.Optional;

public interface DataProcessor extends AutoCloseable {
    void process(String line);

    Optional<Statistics> getStatistics();

    @Override
    void close() throws IOException;
}
