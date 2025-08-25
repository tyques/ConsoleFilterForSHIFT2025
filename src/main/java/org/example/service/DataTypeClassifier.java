package org.example.service;

import org.example.model.DataType;

public class DataTypeClassifier {

    public DataType classify(String line) {
        if (line == null || line.isBlank()) {
            return DataType.STRING;
        }
        try {
            Long.parseLong(line);
            return DataType.INTEGER;
        } catch (NumberFormatException ignored) {
        }

        try {
            Double.parseDouble(line);
            // Этот метод корректно обработает форматы "1.23", "1.5e-10", а также "Infinity".
            return DataType.FLOAT;
        } catch (NumberFormatException e2) {
            // Если не удалось преобразовать ни в Long, ни в Double, то это точно строка.
            return DataType.STRING;
        }
    }
}