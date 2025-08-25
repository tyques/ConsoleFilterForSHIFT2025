package org.example.config;

import org.example.app.AppConfig;
import org.example.exception.ConfigException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {

    public AppConfig parse(String[] args) {
        List<Path> inputFiles = new ArrayList<>();
        Path outputPath = Paths.get("");
        String filePrefix = "";
        boolean appendMode = false;
        StatisticsMode statisticsMode = StatisticsMode.NONE;

        if (args.length == 0) {
            throw new ConfigException("Не указаны входные файлы. Используйте 'java -jar util.jar [options] file1.txt file2.txt...'.");
        }

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = Paths.get(args[++i]);
                    } else {
                        throw new ConfigException("Опция -o требует указания пути.");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        filePrefix = args[++i];
                    } else {
                        throw new ConfigException("Опция -p требует указания префикса.");
                    }
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    statisticsMode = StatisticsMode.SHORT;
                    break;
                case "-f":
                    statisticsMode = StatisticsMode.FULL;
                    break;
                default:
                    if (args[i].startsWith("-")) {
                        System.err.println("Предупреждение: неизвестная опция '" + args[i] + "'. Она будет проигнорирована.");
                    } else {
                        inputFiles.add(Paths.get(args[i]));
                    }
                    break;
            }
        }

        if (inputFiles.isEmpty()) {
            throw new ConfigException("Не указаны входные файлы для обработки.");
        }

        return new AppConfig(inputFiles, outputPath, filePrefix, appendMode, statisticsMode);
    }
}
