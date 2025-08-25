package org.example.processor;

import org.example.app.AppConfig;
import org.example.model.DataType;
import org.example.statistics.FullNumericStatistics;
import org.example.statistics.FullStringStatistics;
import org.example.statistics.ShortStatistics;
import org.example.statistics.Statistics;

import java.util.EnumMap;
import java.util.Map;

public class ProcessorFactory {

    public Map<DataType, DataProcessor> createProcessors(AppConfig config) {
        Map<DataType, DataProcessor> processors = new EnumMap<>(DataType.class);

        processors.put(DataType.INTEGER, createProcessor(config, "integers.txt", "Целые числа", DataType.INTEGER));
        processors.put(DataType.FLOAT, createProcessor(config, "floats.txt", "Вещественные числа", DataType.FLOAT));
        processors.put(DataType.STRING, createProcessor(config, "strings.txt", "Строки", DataType.STRING));

        return processors;
    }

    private DataProcessor createProcessor(AppConfig config, String fileName, String statsTypeName, DataType type) {
        Statistics stats = switch (config.statisticsMode()) {
            case SHORT -> new ShortStatistics(statsTypeName);
            case FULL -> switch (type) {
                case INTEGER, FLOAT -> new FullNumericStatistics(statsTypeName);
                case STRING -> new FullStringStatistics(statsTypeName);
            };
            default -> null;
        };

        return new GenericDataProcessor(config, stats, fileName);
    }
}