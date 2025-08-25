package org.example.app;

import org.example.config.StatisticsMode;

import java.nio.file.Path;
import java.util.List;

public record AppConfig(
        List<Path> inputFiles,
        Path outputPath,
        String filePrefix,
        boolean appendMode,
        StatisticsMode statisticsMode
) {
}
