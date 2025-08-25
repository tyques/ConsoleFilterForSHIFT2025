package org.example.processor;

import org.example.app.AppConfig;
import org.example.statistics.Statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class GenericDataProcessor implements DataProcessor {
    private final AppConfig config;
    private final Statistics statistics;
    private final String outputFileName;
    private BufferedWriter writer;

    public GenericDataProcessor(AppConfig config, Statistics statistics, String outputFileName) {
        this.config = config;
        this.statistics = statistics;
        this.outputFileName = outputFileName;
    }

    @Override
    public void process(String line) {
        try {
            // Ленивая инициализация writer'а при первой записи
            ensureWriterInitialized();
            writer.write(line);
            writer.newLine();
            if (statistics != null) {
                statistics.recordValue(line);
            }
        } catch (IOException e) {
            // Превращаем проверяемое исключение в непроверяемое, чтобы прервать stream
            throw new UncheckedIOException("Ошибка записи в файл '" + outputFileName + "'", e);
        }
    }

    private void ensureWriterInitialized() throws IOException {
        if (writer == null) {
            Path outputPath = config.outputPath().resolve(config.filePrefix() + outputFileName);
            Path parentDir = outputPath.getParent();

            // Создаем директории, только если они указаны в пути и существуют
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            writer = new BufferedWriter(new FileWriter(outputPath.toFile(), config.appendMode()));
        }
    }

    @Override
    public Optional<Statistics> getStatistics() {
        return Optional.ofNullable(statistics).filter(s -> s.getCount() > 0);
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}