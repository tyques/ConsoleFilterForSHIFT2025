package org.example.app;

import org.example.config.CommandLineParser;
import org.example.model.DataType;
import org.example.processor.DataProcessor;
import org.example.processor.ProcessorFactory;
import org.example.service.DataTypeClassifier;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileContentFilter {
    private final CommandLineParser parser = new CommandLineParser();
    private final DataTypeClassifier classifier = new DataTypeClassifier();
    private final ProcessorFactory processorFactory = new ProcessorFactory();

    public void run(String[] args) {
        AppConfig config = parser.parse(args);
        Map<DataType, DataProcessor> processors;
        processors = processorFactory.createProcessors(config);

        try {
            processInputFiles(config, processors);
        } finally {
            closeProcessors(processors);
        }

        printStatistics(processors);
    }

    private void processInputFiles(AppConfig config, Map<DataType, DataProcessor> processors) {
        for (Path inputFile : config.inputFiles()) {
            try (var lines = Files.lines(inputFile)) {
                System.out.println("Обработка файла: " + inputFile);
                lines.forEach(line -> {
                    DataType type = classifier.classify(line);
                    processors.get(type).process(line);
                });
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла '" + inputFile + "': " + e.getMessage() + ". Файл будет пропущен.");
            } catch (UncheckedIOException e) {
                System.err.println("Ошибка записи данных из файла '" + inputFile + "': " + e.getCause().getMessage() + ". Обработка остановлена.");
                throw e;
            }
        }
    }

    private void closeProcessors(Map<DataType, DataProcessor> processors) {
        processors.values().forEach(p -> {
            try {
                p.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла: " + e.getMessage());
            }
        });
    }

    private void printStatistics(Map<DataType, DataProcessor> processors) {
        System.out.println("\nСтатистика обработки:");
        processors.values().stream()
                .flatMap(p -> p.getStatistics().stream())
                .forEach(s -> System.out.println(s.getReport() + "\n"));
    }
}

