package com.tetris;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class HighscoreManager {
    private static final String FILE_NAME = ".tetris/highscores.txt";
    private static final String DELIM = "\\|";
    private static final String WRITE_DELIM = "|";

    private final Path filePath;
    private final List<HighscoreEntry> entries = new ArrayList<>();
    private final Object lock = new Object();

    private static volatile HighscoreManager instance;

    private HighscoreManager() {
        String home = System.getProperty("user.home");
        filePath = Paths.get(home, FILE_NAME);
        loadFromFile();
    }

    public static HighscoreManager getInstance() {
        if (instance == null) {
            synchronized (HighscoreManager.class) {
                if (instance == null) instance = new HighscoreManager();
            }
        }
        return instance;
    }

    private void ensureDir() throws IOException {
        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }

    private void loadFromFile() {
        synchronized (lock) {
            entries.clear();
            if (!Files.exists(filePath)) return;
            try (BufferedReader r = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = r.readLine()) != null) {
                    try {
                        String[] parts = line.split(DELIM, 3);
                        if (parts.length < 2) continue;
                        long ts = Long.parseLong(parts[0]);
                        int score = Integer.parseInt(parts[1]);
                        String name = parts.length >= 3 ? parts[2] : "";
                        entries.add(new HighscoreEntry(ts, score, name));
                    } catch (Exception e) {
                        System.err.println("Fehler beim Parsen der Highscore-Zeile: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Fehler beim Lesen der Highscore-Datei: " + e.getMessage());
            }
        }
    }

    public void addScore(int score, String name) {
        long ts = System.currentTimeMillis();
        String safeName = (name == null) ? "" : name.replace("|", " ");
        HighscoreEntry entry = new HighscoreEntry(ts, score, safeName);

        synchronized (lock) {
            try {
                ensureDir();
                String line = ts + WRITE_DELIM + score + WRITE_DELIM + safeName + System.lineSeparator();
                Files.writeString(filePath, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                entries.add(entry);
            } catch (IOException e) {
                System.err.println("Fehler beim Schreiben des Highscores: " + e.getMessage());
            }
        }
    }

    public Optional<HighscoreEntry> getTopScore() {
        synchronized (lock) {
            return entries.stream().max(Comparator.naturalOrder());
        }
    }

    public boolean isNewHighscore(int score) {
        synchronized (lock) {
            return entries.stream().max(Comparator.naturalOrder()).map(e -> score > e.getScore()).orElse(true);
        }
    }

    public boolean addScoreAndReturnIfTop(int score, String name) {
        long ts = System.currentTimeMillis();
        String safeName = (name == null) ? "" : name.replace("|", " ");
        HighscoreEntry entry = new HighscoreEntry(ts, score, safeName);

        synchronized (lock) {
            boolean isTop = entries.stream().max(Comparator.naturalOrder()).map(e -> score > e.getScore()).orElse(true);
            try {
                ensureDir();
                String line = ts + WRITE_DELIM + score + WRITE_DELIM + safeName + System.lineSeparator();
                Files.writeString(filePath, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                entries.add(entry);
            } catch (IOException e) {
                System.err.println("Fehler beim Schreiben des Highscores: " + e.getMessage());
            }
            return isTop;
        }
    }

    public List<HighscoreEntry> getRecent(int n) {
        synchronized (lock) {
            return entries.stream().sorted(Comparator.comparingLong((HighscoreEntry entry) -> entry.getTime()).reversed()).limit(n).collect(Collectors.toList());
        }
    }

    public List<HighscoreEntry> getTop(int n) {
        synchronized (lock) {
            return entries.stream().sorted().limit(n).collect(Collectors.toList());
        }
    }

    public List<HighscoreEntry> getAll() {
        synchronized (lock) {
            return new ArrayList<>(entries);
        }
    }

    public void reload() {
        loadFromFile();
    }
}