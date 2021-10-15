package rebirth.costume.tool.mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ProcessIncludes {
  private static final String TAB = "\t";
  public static String execute(String ctmFilePath) throws IOException {
    String expandedFileContent = "";
    String fileText = new String(Files.readAllBytes(Paths.get(ctmFilePath)));
    Scanner scanner = new Scanner(fileText);

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      if (line.contains(TAB)) {
        line = line.replaceAll(TAB, " ");
      }
      String[] words = line.trim().split("\\s+");

      if ("include".equalsIgnoreCase(words[0])) {
        expandedFileContent = expandedFileContent + processInclude(line.substring(8));
      } else {
        expandedFileContent = expandedFileContent + line + "\r\n";
      }

    }
    scanner.close();
    return expandedFileContent;
  }
  private static String processInclude(String includeString) {
    System.out.println(includeString);
    String includeContent = "";
      try {
        String cp = ProcessIncludes.class.getResource(includeString).getPath();
        System.out.println(cp);
        String fileText = new String(Files.readAllBytes(Paths.get(cp)));
        Scanner scanner = new Scanner(fileText);
        while (scanner.hasNextLine()) {
          String line = scanner.nextLine().trim();
          if (line.contains(TAB)) {
            line = line.replaceAll(TAB, " ");
          }
          includeContent = includeContent + line + "\r\n";
        }
        scanner.close();
      } catch (IOException e) {
        // Expect missing files - do nothing
      }
    return includeContent;
  }
}
