package rebirth.costume.tool;

import java.io.IOException;

import rebirth.costume.tool.mapping.ProcessCostumeFile;

public class ExportMappings {

  public static void main(String[] args) throws IOException {
    ProcessCostumeFile pcf = new ProcessCostumeFile();
    pcf.export();
  }

}
