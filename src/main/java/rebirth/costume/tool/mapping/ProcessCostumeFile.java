package rebirth.costume.tool.mapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private Map<String, String> boneSetMapping;

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
  public Map<String, String> getBoneSetMapping() {
    return boneSetMapping;
  }

  public void setBoneSetMapping(Map<String, String> boneSetMapping) {
    this.boneSetMapping = boneSetMapping;
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
    /**
     * Search through the mappings table to find the costume element.
     * Then transfer the new elements into the costume part. 
     */
    for (InfoMapping map : this.componentMappings) {
      if (old.getBodySetName() != null) {
        if (isMatch(old.getDisplayName(), map.getOldGeoSetDisplayName())) {
          if (isMatch(old.getBodySetName(), map.getOldBoneSetName()) &&
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
    /*
     * We need to map old BoneSets to new BoneSets as not
     * all costume elements are covered by the mappings.
     */
    if (old.getBodySetName() != null) {
      String oldBs = cleanParam(old.getBodySetName().toLowerCase());
      if (boneSetMapping.containsKey(oldBs)) {
        old.setBodySetName(boneSetMapping.get(oldBs));
      }
    }
    return old;
  }
  /*
   * Match strings but don't worry about " or case. 
   */
  private boolean isMatch(String part1, String part2) {
    if (part1 == null || part2 == null)
      return false;
    if (part1.equalsIgnoreCase(part2))
      return true;
    if (cleanParam(part1).equalsIgnoreCase(cleanParam(part2)))
      return true;
    return false;
  }

  /*
   * if string is encapsulated by quotes, remove them. 
   */
  private static String cleanParam(String line) {
    String result = line;
    if (result.startsWith("\"") && result.endsWith("\"")) {
      result = result.substring(1, result.length() - 1);
    }
    return result;
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

  /*
   * Simple attempt at converting old v1 costumes to new v2.
   */
  private List<CostumePart> convert28to33(List<CostumePart> oldCostumeParts) {
    if (oldCostumeParts.size()>27) {
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
    } else {
      System.out.println("UNKNOWN FORMAT");
      return oldCostumeParts;
    }
  }

  private boolean isFemale(CostumeFile costumeFile) {
    if (costumeFile.getBodyType() != null)
      if ("1".equalsIgnoreCase(costumeFile.getBodyType()))
        return true;
    return false;
  }

  public void initialise() throws IOException {
    /*
     * Deserialised Mappings
     */
    ArrayList<InfoMapping> maps = new ArrayList<>();
    try {
      InputStream fis = getClass().getClassLoader().getResourceAsStream("mappingData");
      ObjectInputStream ois = new ObjectInputStream(fis);
      maps = (ArrayList<InfoMapping>) ois.readObject();
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
    /**
     * Set BoneSetMappings
     */
    boneSetMapping = new HashMap<String, String>();
    for (InfoMapping im : getComponentMappings()) {
      String oldBs = im.getOldBoneSetName().toLowerCase();
      if (!boneSetMapping.containsKey(oldBs)) {
        boneSetMapping.put(oldBs, im.getNewBoneSetName());
      }
    }
  }
  /**
   * Process the CTM files to produce a mapping of costume parts.
   * The serialise the data.
   * @throws IOException
   */
  public void export() throws IOException {
    // CHEST
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_armored.ctm", "female-asym-chests\\menu_armored_asym.ctm"));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_baggy.ctm", "female-asym-chests\\menu_baggy_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_jackets.ctm", "female-asym-chests\\menu_jackets_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_jackets_sleeveless.ctm", "female-asym-chests\\menu_jackets_sleeveless_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\Menu_Magic_Coat.ctm", "female-asym-chests\\menu_magic_coat_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_robes.ctm", "female-asym-chests\\menu_robes_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_robes_sleeveless.ctm", "female-asym-chests\\menu_robes_sleeveless_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_roboticarm1.ctm", "female-asym-chests\\menu_roboticarm1_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_roboticarm2.ctm", "female-asym-chests\\menu_roboticarm2_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_roboticarm3.ctm", "female-asym-chests\\menu_roboticarm3_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_shirts.ctm", "female-asym-chests\\menu_shirts_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_tight.ctm", "female-asym-chests\\menu_tight_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_tights_skin.ctm", "female-asym-chests\\menu_tights_skin_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_trench_coat.ctm", "female-asym-chests\\menu_trench_coat_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-chests\\menu_unique.ctm", "female-asym-chests\\menu_unique_asym.ctm", getComponentMappings()));
    // HIPS
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_armored.ctm", "female-asym-hips\\menu_armored_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_monstrous.ctm", "female-asym-hips\\menu_monstrous_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_pantstight.ctm", "female-asym-hips\\menu_pantstight_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_pants_all.ctm", "female-asym-hips\\menu_pants_all_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_skirts01.ctm", "female-asym-hips\\menu_skirts01_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_tight.ctm", "female-asym-hips\\menu_tight_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_tights_skin.ctm", "female-asym-hips\\menu_tights_skin_asym.ctm", getComponentMappings()));
    setComponentMappings(CreateMapping.execute("female-old-hips\\menu_tuckedin.ctm", "female-asym-hips\\menu_tuckedin_asym.ctm", getComponentMappings()));
    // Manual mappings
    setComponentMappings(CreateMapping.execute("manual-mappings\\old-parts.ctm", "manual-mappings\\new-parts.ctm", getComponentMappings()));
    // Serialise.
    try {
      FileOutputStream fos = new FileOutputStream("mappingData");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(getComponentMappings());
      oos.close();
      fos.close();
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }
  }
}
