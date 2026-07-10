package library.service;

import java.io.*;

/**
 * FileHandler.java
 * ----------------
 * Handles all reading/writing of Java objects to disk using standard
 * Object Serialization (ObjectOutputStream / ObjectInputStream).
 *
 * This is what makes the application's data PERSISTENT: every time the
 * app closes (or after every change), we write our data structures to
 * .dat files inside the /data folder. On the next launch we read them
 * back in, so the library "remembers" its books, issued records and
 * waiting queues even after a restart -- with NO database required.
 */
public class FileHandler {

    /** Serializes (saves) any Serializable object to the given file path. */
    public static void save(Object object, String filePath) {
        // try-with-resources automatically closes the stream, even on error
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(object);
        } catch (IOException e) {
            System.err.println("Error saving file: " + filePath + " -> " + e.getMessage());
        }
    }

    /** Deserializes (loads) an object from the given file path. Returns null if file doesn't exist or is unreadable. */
    public static Object load(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading file: " + filePath + " -> " + e.getMessage());
            return null;
        }
    }

    /** Ensures the /data directory exists before we try to write to it. */
    public static void ensureDataFolderExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
