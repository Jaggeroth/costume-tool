package rebirth.costume.tool.model;

import java.util.ArrayList;
import java.util.List;

public class CostumePart {
  private static final String OUTPUT_FORMAT = "CostumePart \"\"\r\n" + 
      "{\r\n" +
      "%s \r\n}\r\n\r\n";
  private String Fx;
  private String Geometry;
  private String Texture1;
  private String Texture2;
  private String DisplayName;
  private String RegionName;
  private String BodySetName;
  private String Color1;
  private String Color2;
  private String Color3;
  private String Color4;
  
  public String getFx() {
    return Fx;
  }
  public void setFx(String fx) {
    this.Fx = fx;
  }
  public String getGeometry() {
    return Geometry;
  }
  public void setGeometry(String geometry) {
    Geometry = geometry;
  }
  public String getTexture1() {
    return Texture1;
  }
  public void setTexture1(String texture1) {
    Texture1 = texture1;
  }
  public String getTexture2() {
    return Texture2;
  }
  public void setTexture2(String texture2) {
    Texture2 = texture2;
  }
  public String getDisplayName() {
    return DisplayName;
  }
  public void setDisplayName(String displayName) {
    DisplayName = displayName;
  }
  public String getRegionName() {
    return RegionName;
  }
  public void setRegionName(String regionName) {
    RegionName = regionName;
  }
  public String getBodySetName() {
    return BodySetName;
  }
  public void setBodySetName(String bodySetName) {
    BodySetName = bodySetName;
  }
  public String getColor1() {
    return Color1;
  }
  public void setColor1(String color1) {
    Color1 = color1;
  }
  public String getColor2() {
    return Color2;
  }
  public void setColor2(String color2) {
    Color2 = color2;
  }
  public String getColor3() {
    return Color3;
  }
  public void setColor3(String color3) {
    Color3 = color3;
  }
  public String getColor4() {
    return Color4;
  }
  public void setColor4(String color4) {
    Color4 = color4;
  }
  public String toString() {
    List<String> parts = new ArrayList<String>();
    if (Fx != null) {
      parts.add(String.format("\tFx %s", Fx));
    }
    if (Geometry != null) {
      parts.add(String.format("\tGeometry %s", Geometry));
    }
    if (Texture1 != null) {
      parts.add(String.format("\tTexture1 %s", Texture1));
    }
    if (Texture2 != null) {
      parts.add(String.format("\tTexture2 %s", Texture2));
    }
    if (DisplayName != null) {
      parts.add(String.format("\tDisplayName %s", DisplayName));
    }
    if (RegionName != null) {
      parts.add(String.format("\tRegionName %s", RegionName));
    }
    if (BodySetName != null) {
      parts.add(String.format("\tBodySetName %s", safeFormat(BodySetName)));
    }
    if (Color1 != null) {
      parts.add(String.format("\tColor1 %s", Color1));
    }
    if (Color2 != null) {
      parts.add(String.format("\tColor2 %s", Color2));
    }
    if (Color3 != null) {
      parts.add(String.format("\tColor3 %s", Color3));
    }
    if (Color4 != null) {
      parts.add(String.format("\tColor4 %s", Color4));
    }
    return String.format(OUTPUT_FORMAT, String.join("\n", parts));
  }
  private String safeFormat(String param) {
    if (param.startsWith("\""))
      return param;
    if (param.indexOf(" ")>0) {
      return String.format("\"%s\"", param);
    }
    return param;
  }
  public static CostumePart clone(CostumePart clone) {
    CostumePart newClone = new CostumePart();
    newClone.setFx(clone.getFx());
    newClone.setGeometry(clone.getGeometry());
    newClone.setTexture1(clone.getTexture1());
    newClone.setTexture2(clone.getTexture2());
    newClone.setDisplayName(clone.getDisplayName());
    newClone.setRegionName(clone.getRegionName());
    newClone.setBodySetName(clone.getBodySetName());
    newClone.setColor1(clone.getColor1());
    newClone.setColor2(clone.getColor2());
    newClone.setColor3(clone.getColor3());
    newClone.setColor4(clone.getColor4());
    return newClone;
  }
}
