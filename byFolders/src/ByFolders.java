/*
*
*  Author: Roman V. Fedorin
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ByFolders {
    public static void main(String[] args) {
        File root = new File("." + File.separator);
        ZoneId zoneId = ZoneId.systemDefault();
        StringBuilder fileNamesMapping = new StringBuilder("\n=========\n");
        String author = "\n========= Created by Roman V. Fedorin =========\n";

        File[] allFilesInDir = root.listFiles();
        DateTimeFormatter formatterDir = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterFile = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String toDay = formatterDir.format(Instant.now().atZone(zoneId));
        String logFileName = toDay + "-mapping.txt";

        if (!Files.exists(Paths.get(logFileName))) {
            try (FileWriter fw = new FileWriter(logFileName, true)) {
                fw.write(author);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (allFilesInDir != null) {

            for (File entity : allFilesInDir) {
                System.out.println(entity.getName());
                if (entity.isDirectory()
                        || entity.getName().endsWith("jar")
                        || entity == root
                        || entity.getName().endsWith("mapping.txt"))
                {
                    continue;
                }

                Instant lastModified = Instant.ofEpochMilli(entity.lastModified());

                String folderName = File.separator + formatterDir.format(lastModified.atZone(zoneId));
                String newFileName = formatterFile.format(lastModified.atZone(zoneId));

                fileNamesMapping.append(entity.getName()).append(" : .")
                        .append(folderName).append(File.separator).append(newFileName).append("\n");

                String[] byFileExtension = entity.getName().split("\\.");
                newFileName += byFileExtension.length > 1 ? "." + byFileExtension[1] : "";

                File newFolder = new File(root + folderName);
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                move(entity, newFolder, newFileName);
            }

            try (FileWriter fw = new FileWriter(logFileName, true)) {
                fw.write(fileNamesMapping.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {// if it has files
            System.out.println("[Error] Result of reading current dir is NULL. " + root);
        }

    } // ** main()

    private static void move(File source, File to, String newFileName) {
        try {
            Files.move(source.toPath(), to.toPath().resolve(newFileName), REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
} // class
