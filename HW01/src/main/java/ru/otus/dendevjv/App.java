package ru.otus.dendevjv;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class App {

    private static final int NUM_PARAMETERS = 2;

    /**
     *
     * @param args args[0] path to the directory;
     *             args[1] path to csv file containing mapping oldPrefixes -> newPrefixes,
     *             this file must be UTF-8 without BOM.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != NUM_PARAMETERS) {
            printUsageAndExit();
        }

        String csv = args[1];
        System.out.println("Reading prefixes from " + csv);
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8));
        Map<String,String> mapping = new HashMap<>();
        reader.readAll().forEach(tokens -> mapping.put(tokens[0], tokens[1]));

        FileProcessor processor = new FileProcessor(mapping);
        processor.renameInDirectory(new File(args[0]));
    }

    private static void printUsageAndExit() {
        System.out.println("Usage:");
        System.out.println("    hw01 <directory> <csv_file>" +
                " - renames files in the directory using mapping of prefixes in csv file");
        System.out.println();
        System.exit(0);
    }
}
