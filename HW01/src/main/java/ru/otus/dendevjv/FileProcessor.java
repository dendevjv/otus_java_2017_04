package ru.otus.dendevjv;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class FileProcessor {
    private Map<String,String> mapping;
    private Set<String> oldPrefixes;

    FileProcessor(Map<String,String> prefixMapping) {
        mapping = prefixMapping;
        oldPrefixes = mapping.keySet();
    }

    void renameInDirectory(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                String name = file.getName();
                boolean matches = false;
                String newPrefix = null;
                String oldPrefix = null;
                for (String prefix : oldPrefixes) {
                    if (name.startsWith(prefix)) {
                        newPrefix = mapping.get(prefix);
                        oldPrefix = prefix;
                        matches = true;
                        break;
                    }
                }
                if (matches) {
                    String newName = name.replace(oldPrefix, newPrefix);
                    File dest = new File(directory, newName);
                    file.renameTo(dest);
                    System.out.println("Old name: " + file.getAbsolutePath());
                    System.out.println("New name: " + dest.getAbsolutePath());
                }
            }
        }
    }
}
