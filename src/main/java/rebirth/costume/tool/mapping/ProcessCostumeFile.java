package rebirth.costume.tool.mapping;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rebirth.costume.tool.model.CostumeFile;
import rebirth.costume.tool.model.CostumePart;
import rebirth.costume.tool.model.InfoMapping;

public class ProcessCostumeFile {
  private static final String HEAD_PATTER = "\\{(.*?)CostumePart";
  private static final String PART_PATTERN = "CostumePart \"\"(.*?)\\{(.*?)\\}";
  private List<InfoMapping> componentMappings;

  public String execute(String costumeFilePath) throws IOException {
    CostumeFile cf = null;
    String fileText = new String(Files.readAllBytes(Paths.get(costumeFilePath)));
    /*
     * Create the component mappings
     */
    if (getComponentMappings() == null) {
      initialise();
    }
    /*
     * Parse the file header to work out the BoneSet
     */
    Matcher hMatcher = Pattern.compile(HEAD_PATTER, Pattern.DOTALL).matcher(fileText);
    if (hMatcher.find()) {
      cf = processCostumeFile(hMatcher.group(1));
    }
    /*
     * Start processing each costume component
     * But only for female - body type 1
     */
    Matcher cMatcher = Pattern.compile(PART_PATTERN, Pattern.DOTALL).matcher(fileText);
    while (cMatcher.find()) {
      CostumePart cp = processCostumePart(cMatcher.group(2), isFemale(cf));
      cf.addCostumePart(cp);
    }
    /*
     * If old format, convert to new
     */
    if ("28".equalsIgnoreCase(cf.getNumParts())) {
      cf.setNumParts("33");
      List<CostumePart> newCostumeParts = convert28to33(cf.getCostumeParts());
      cf.setCostumeParts(newCostumeParts);
    }
    return cf.toString();
  }

  public List<InfoMapping> getComponentMappings() {
    return componentMappings;
  }
  public void setComponentMappings(List<InfoMapping> componentMappings) {
    this.componentMappings = componentMappings;
  }
  public void setComponentMappings(ArrayList<InfoMapping> componentMappings) {
    this.componentMappings = componentMappings;
  }

  private CostumePart processCostumePart(String partText, boolean update) {
    CostumePart result = new CostumePart();
    if (partText != null) {
      Scanner scanner = new Scanner(partText);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        String[] words = line.trim().split("\\s+");
        switch (words[0]) {
        case "Fx" :
        case "Geometry" :
        case "Texture1" :
        case "Texture2" :
          set(result, words[0], words[1]);
          break;
        case "DisplayName" :
          set(result, words[0], line.substring(12));
          break;
        case "RegionName" :
          set(result, words[0], line.substring(11));
          break;
        case "BodySetName" :
          set(result, words[0], line.substring(12));
          break;
        case "Color1" :
        case "Color2" :
        case "Color3" :
        case "Color4" :
          set(result, words[0], line.substring(7));
        }
      }
      scanner.close();
    }
    if (update) {
      return updateAttributes(result);
    } else {
      return result;
    }
  }
  private CostumeFile processCostumeFile(String headerText) {
    CostumeFile result = new CostumeFile();
    if (headerText != null) {
      Scanner scanner = new Scanner(headerText);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        String[] words = line.trim().split("\\s+");
        if (words.length>1) {
          set(result, words[0], line.substring(line.indexOf(" ")+1));
        }
      }
      scanner.close();
    } 
    return result;
  }
  private CostumePart updateAttributes(CostumePart old) {
    for (InfoMapping map : this.componentMappings) {
      if (old.getBodySetName() != null) {
        if (old.getDisplayName().equalsIgnoreCase(map.getOldGeoSetDisplayName())) {
          if (old.getBodySetName().equalsIgnoreCase(map.getOldBoneSetName()) &&
              old.getGeometry().equalsIgnoreCase(map.getOldGeo()) &&
              old.getTexture1().equalsIgnoreCase(map.getOldTex1()) &&
              old.getTexture2().equalsIgnoreCase(map.getOldTex2())) {
            old.setGeometry(map.getNewGeo());
            old.setTexture1(map.getNewTex1());
            old.setTexture2(map.getNewTex2());
            old.setBodySetName(map.getNewBoneSetName());
            return old;
          }
        }
      }
    }
    if ("Shirts".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("Asym Shirts");
    } else if ("Tight".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("AsymTight");
    } else if ("Tights/Skin".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("AsymTights/skin");
    } else if ("Jackets".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("AsymJackets");
    } else if ("Pants".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("PantsAsym");
    } else if ("PantsTight".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("PantsTightAsym");
    } else if ("\"Tights/skin asym\"".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("AsymTights/Skin");
    } else if ("Robe".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("Asym Robe");
    } else if ("NewSkirts".equalsIgnoreCase(old.getBodySetName())) {
      old.setBodySetName("NewSkirtsAsym");
    }
    return old;
  }

  private boolean set(Object object, String fieldName, Object fieldValue) {
    Class<?> clazz = object.getClass();
    while (clazz != null) {
      try {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
        return true;
      } catch (NoSuchFieldException e) {
        clazz = clazz.getSuperclass();
      } catch (Exception e) {
        throw new IllegalStateException(e);
      }
    }
    return false;
  }

  private List<CostumePart> convert28to33(List<CostumePart> oldCostumeParts) {
    List<CostumePart> newCostumeParts = new ArrayList<CostumePart>();
    // 1 to 19 same as before
    for (int x=0; x<28; x++) {
      CostumePart newPart = CostumePart.clone(oldCostumeParts.get(x));
      newCostumeParts.add(newPart);
    }
    // Add in the right glove
    CostumePart rightGlove = CostumePart.clone(oldCostumeParts.get(3));
    rightGlove.setDisplayName("\"Right Glove\"");
    rightGlove.setGeometry(rightGlove.getGeometry().replace('*', 'R'));
    newCostumeParts.add(rightGlove);
    // add in the left glove
    CostumePart leftGlove = CostumePart.clone(oldCostumeParts.get(3));
    leftGlove.setGeometry(leftGlove.getGeometry().replace('*', 'L'));
    leftGlove.setDisplayName("\"Left Glove\"");
    newCostumeParts.add(leftGlove);
    // add some extra empty parts for good luck
    for (int x=28; x<oldCostumeParts.size(); x++) {
      CostumePart newPart = CostumePart.clone(oldCostumeParts.get(x));
      if (newPart.getGeometry() == null) {
        newPart.setFx("None");
        newPart.setGeometry("None");
        newPart.setTexture1("None");
        newPart.setTexture2("None");
      }
      newCostumeParts.add(newPart);
    }
    for (int x=0; x<3; x++) {
      CostumePart newPart = new CostumePart();
      newPart.setColor1("0,  0,  0");
      newPart.setColor2("0,  0,  0");
      newPart.setColor3("0,  0,  0");
      newPart.setColor4("0,  0,  0");
      newCostumeParts.add(newPart);
    }
    return newCostumeParts;
  }

  private boolean isFemale(CostumeFile costumeFile) {
    if (costumeFile.getBodyType() != null)
      if ("1".equalsIgnoreCase(costumeFile.getBodyType()))
        return true;
    return false;
  }

  public void initialise() throws IOException {
    /*
     * Deserialised object
     */
    ArrayList<InfoMapping> maps = new ArrayList<>();
    try {
      InputStream fis = getClass().getClassLoader().getResourceAsStream("mappingData");
      ObjectInputStream ois = new ObjectInputStream(fis);
      maps = (ArrayList) ois.readObject();
      ois.close();
      fis.close();
    } catch (IOException ioe) {
        ioe.printStackTrace();
        return;
    } catch (ClassNotFoundException c) {
        System.out.println("Class not found");
        c.printStackTrace();
        return;
    }
    setComponentMappings(maps);
  }
}
