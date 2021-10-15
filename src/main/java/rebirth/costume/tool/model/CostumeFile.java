package rebirth.costume.tool.model;

import java.util.ArrayList;
import java.util.List;

public class CostumeFile {
  private static final String OUTPUT_FORMAT = "\r\n{\r\n" +
      "%s" +
      "\r\n}";
  private String CostumeFilePrefix;
  private String Scale;
  private String BoneScale;
  private String ShoulderScale;
  private String ChestScale;
  private String WaistScale;
  private String HipScale;
  private String LegScale;
  private String HeadScales;
  private String BrowScales;
  private String CheekScales;
  private String ChinScales ;
  private String CraniumScales;
  private String JawScales;
  private String NoseScales;
  private String SkinColor;
  private String NumParts;
  private String BodyType;
  private List<CostumePart> costumeParts;
  public String getCostumeFilePrefix() {
    return CostumeFilePrefix;
  }
  public void setCostumeFilePrefix(String costumeFilePrefix) {
    CostumeFilePrefix = costumeFilePrefix;
  }
  public String getScale() {
    return Scale;
  }
  public void setScale(String scale) {
    Scale = scale;
  }
  public String getBoneScale() {
    return BoneScale;
  }
  public void setBoneScale(String boneScale) {
    BoneScale = boneScale;
  }
  public String getShoulderScale() {
    return ShoulderScale;
  }
  public void setShoulderScale(String shoulderScale) {
    ShoulderScale = shoulderScale;
  }
  public String getChestScale() {
    return ChestScale;
  }
  public void setChestScale(String chestScale) {
    ChestScale = chestScale;
  }
  public String getWaistScale() {
    return WaistScale;
  }
  public void setWaistScale(String waistScale) {
    WaistScale = waistScale;
  }
  public String getHipScale() {
    return HipScale;
  }
  public void setHipScale(String hipScale) {
    HipScale = hipScale;
  }
  public String getLegScale() {
    return LegScale;
  }
  public void setLegScale(String legScale) {
    LegScale = legScale;
  }
  public String getHeadScales() {
    return HeadScales;
  }
  public void setHeadScales(String headScales) {
    HeadScales = headScales;
  }
  public String getBrowScales() {
    return BrowScales;
  }
  public void setBrowScales(String browScales) {
    BrowScales = browScales;
  }
  public String getCheekScales() {
    return CheekScales;
  }
  public void setCheekScales(String cheekScales) {
    CheekScales = cheekScales;
  }
  public String getChinScales() {
    return ChinScales;
  }
  public void setChinScales(String chinScales) {
    ChinScales = chinScales;
  }
  public String getCraniumScales() {
    return CraniumScales;
  }
  public void setCraniumScales(String craniumScales) {
    CraniumScales = craniumScales;
  }
  public String getJawScales() {
    return JawScales;
  }
  public void setJawScales(String jawScales) {
    JawScales = jawScales;
  }
  public String getNoseScales() {
    return NoseScales;
  }
  public void setNoseScales(String noseScales) {
    NoseScales = noseScales;
  }
  public String getSkinColor() {
    return SkinColor;
  }
  public void setSkinColor(String skinColor) {
    SkinColor = skinColor;
  }
  public String getNumParts() {
    return NumParts;
  }
  public void setNumParts(String numParts) {
    NumParts = numParts;
  }
  public String getBodyType() {
    return BodyType;
  }
  public void setBodyType(String bodyType) {
    BodyType = bodyType;
  }
  public List<CostumePart> getCostumeParts() {
    return costumeParts;
  }
  public void setCostumeParts(List<CostumePart> costumeParts) {
    this.costumeParts = costumeParts;
  }
  public String toString() {
    List<String> parts = new ArrayList<String>();
    if (CostumeFilePrefix != null) {
      parts.add(String.format("CostumeFilePrefix %s", CostumeFilePrefix));
    }
    if (Scale != null) {
      parts.add(String.format("Scale %s", Scale));
    }
    if (BoneScale != null) {
      parts.add(String.format("BoneScale %s", BoneScale));
    }
    if (ShoulderScale != null) {
      parts.add(String.format("ShoulderScale %s", ShoulderScale));
    }
    if (ChestScale != null) {
      parts.add(String.format("ChestScale %s", ChestScale));
    }
    if (WaistScale != null) {
      parts.add(String.format("WaistScale %s", WaistScale));
    }
    if (HipScale != null) {
      parts.add(String.format("HipScale %s", HipScale));
    }
    if (LegScale != null) {
      parts.add(String.format("LegScale %s", LegScale));
    }
    if (HeadScales != null) {
      parts.add(String.format("HeadScales %s", HeadScales));
    }
    if (BrowScales != null) {
      parts.add(String.format("BrowScales %s", BrowScales));
    }
    if (CheekScales != null) {
      parts.add(String.format("CheekScales %s", CheekScales));
    }
    if (ChinScales != null) {
      parts.add(String.format("ChinScales %s", ChinScales));
    }
    if (CraniumScales != null) {
      parts.add(String.format("CraniumScales %s", CraniumScales));
    }
    if (JawScales != null) {
      parts.add(String.format("JawScales %s", JawScales));
    }
    if (NoseScales != null) {
      parts.add(String.format("NoseScales %s", NoseScales));
    }
    if (SkinColor != null) {
      parts.add(String.format("SkinColor %s", SkinColor));
    }
    if (NumParts != null) {
      parts.add(String.format("NumParts %s", NumParts));
    }
    if (BodyType != null) {
      parts.add(String.format("BodyType %s", BodyType));
    }
    if (getCostumeParts() != null) {
      for (CostumePart p : getCostumeParts()) {
        parts.add(p.toString());
      }
    }
    return String.format(OUTPUT_FORMAT, String.join("\r\n", parts));
  }
  public void addCostumePart(CostumePart part) {
    if (costumeParts == null) {
      costumeParts = new ArrayList<CostumePart>();
    }
    if (part != null) {
      costumeParts.add(part);
    }
  }
}
