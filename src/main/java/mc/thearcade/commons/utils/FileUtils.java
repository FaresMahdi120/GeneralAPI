package mc.thearcade.commons.utils;


import java.io.*;
import java.nio.file.*;

public class FileUtils {
    public static boolean backupFile(final File fn) {
        try {
            final Path source = Paths.get(fn.getAbsolutePath(), new String[0]);
            final Path backup = Paths.get(fn.getAbsolutePath() + ".backup", new String[0]);
            Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }
}

