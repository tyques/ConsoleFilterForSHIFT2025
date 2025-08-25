package org.example;

import org.example.app.FileContentFilter;

public class Main {
    public static void main(String[] args) {
        try {
            new FileContentFilter().run(args);
        } catch (Exception e) {
            System.err.println("Произошла критическая ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}