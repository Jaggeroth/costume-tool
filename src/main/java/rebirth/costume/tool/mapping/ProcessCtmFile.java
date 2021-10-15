package rebirth.costume.tool.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import rebirth.costume.tool.model.Component;

public class ProcessCtmFile {
  public static List<Component> execute(String ctmFilePath) throws IOException {

    String fileText = ProcessIncludes.execute(ctmFilePath);
    Scanner scanner = new Scanner(fileText);
    boolean isBoneSet = false;
    boolean isGeoSet = false;
    boolean isInfo = false;
    boolean isMask = false;
    List<Component> components = new ArrayList<Component>();
    Component nextComponent = new Component();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();
      String[] words = line.trim().split("\\s+");
      //System.out.println(line);
      if (words.length>0) {
        if ("BoneSet".equalsIgnoreCase(words[0])) {
          isBoneSet = true;
        } else if ("GeoSet".equalsIgnoreCase(words[0])) {
          isGeoSet = true;
        } else if ("Info".equalsIgnoreCase(words[0])) {
          isInfo = true;
        } else if ("Mask".equalsIgnoreCase(words[0])) {
          isMask = true;
        } else if ("End".equalsIgnoreCase(words[0])) {
          if (isInfo) {
            isInfo = false;
            components.add(nextComponent);
            nextComponent = nextComponent.clone();
            nextComponent.clearInfoMask();
          } else if (isMask) {
            isMask = false;
            components.add(nextComponent);
            nextComponent = nextComponent.clone();
            nextComponent.clearInfoMask();
          } else if (isGeoSet) {
            isGeoSet = false;
            nextComponent.clearGeoSet();
          } else if (isBoneSet) {
            isBoneSet = false;
            nextComponent.clearBoneSet();
          } else {
            System.out.println("Shouldn't get here. Mismatched END");
          }
        } else if ("DisplayName".equalsIgnoreCase(words[0])) {
          //String lineDetail = line.substring(line.indexOf(" "));
          if (isMask) {
            nextComponent.setMaskDisplayName(cleanParam(line));
          } else if (isInfo) {
            nextComponent.setInfoDisplayName(cleanParam(line));
          } else if (isGeoSet) {
            nextComponent.setGeoSetDisplayName(cleanParam(line));
          } else if (isBoneSet) {
            nextComponent.setBoneSetDisplayName(cleanParam(line));
          } else {
            System.out.println("Shouldn't get here. Mismatched DisplayName");
          }
        } else if ("Legacy".equalsIgnoreCase(words[0]) && isBoneSet) {
          nextComponent.setBoneSetLegacy(cleanParam(line));
        } else if ("Name".equalsIgnoreCase(words[0]) && isBoneSet && !isMask) {
          nextComponent.setBoneSetName(cleanParam(line));
        } else if ("BodyPart".equalsIgnoreCase(words[0]) && isGeoSet) {
          nextComponent.setGeoSetBodyPart(cleanParam(line));
        } else if ("Type".equalsIgnoreCase(words[0]) && isGeoSet) {
          nextComponent.setGeoSetType(cleanParam(line));
        } else if ("DevOnly".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoDevOnly(cleanParam(line));
        } else if ("COV".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoCOV(cleanParam(line));
        } else if ("Geo".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoGeo(cleanParam(line));
        } else if ("GeoName".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoGeoName(cleanParam(line));
        } else if ("Tex1".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoTex1(cleanParam(line));
        } else if ("Tex2".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoTex2(cleanParam(line));
          if ("!none".equalsIgnoreCase(nextComponent.getInfoTex2())) {
            nextComponent.setInfoTex2("None");
          }
        } else if ("Key".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoKey(cleanParam(line));
        } else if ("Product".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoProduct(cleanParam(line));
        } else if ("IsMask".equalsIgnoreCase(words[0]) && isInfo) {
          nextComponent.setInfoIsMask(cleanParam(line));
        } else if ("Legacy".equalsIgnoreCase(words[0]) && isMask) {
          nextComponent.setMaskLegacy(cleanParam(line));
        } else if ("Name".equalsIgnoreCase(words[0]) && isMask) {
          nextComponent.setMaskName(cleanParam(line));
        } else if ("include".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("CoHV".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("COV".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("Key".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("Product".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("DevOnly".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("Fx".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("Flags".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("ColorLink".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if ("NumColor".equalsIgnoreCase(words[0])) {
          // do nothing at the moment
        } else if (line.startsWith("//")) {
          // comment do nothing
        } else if (line.startsWith("#")) {
          // comment do nothing
        } else if ("".equalsIgnoreCase(line)) {
          // comment do nothing
        } else {
          System.out.println("Shouldn't get here. Mismatched parameter");
          System.out.println(line);
        }
      }
    }
    scanner.close();
    //System.out.println("INFO & MASK");
    //for (Component c : components) {
    //  System.out.println(c);
    //}
    return components;
  }

  private static String cleanParam(String line) {
    String result = line;
    if (result.indexOf("//")>0) {
      result = result.substring(0, result.indexOf("//"));
    }
    if (result.indexOf(" ")>0) {
      result = result.substring(result.indexOf(" ")).trim();
    }
    if (result.startsWith("\"") && result.endsWith("\"")) {
      result = result.substring(1, result.length() - 1);
    }
    if (result.endsWith(".tga")) {
      result = result.substring(0, result.length() - 4);
    }
    //if (result.endsWith("__PanelEmblemSplit")) {
    //  result = result.substring(0, result.length() - 18);
    //}
    //if (result.endsWith("_Mask")) {
    //  result = result.substring(0, result.length() - 5);
    //}
    return result;
    
  }
}
