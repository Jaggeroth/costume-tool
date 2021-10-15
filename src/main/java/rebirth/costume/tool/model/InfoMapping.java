package rebirth.costume.tool.model;

import java.io.Serializable;

public class InfoMapping implements Serializable {
  private String oldGeoSetDisplayName;
  private String infoDisplayName;
  private String oldBoneSetName;
  private String oldGeo;
  private String oldTex1;
  private String oldTex2;
  private String newGeoSetDisplayName;
  private String newBoneSetName;
  private String newGeo;
  private String newTex1;
  private String newTex2;
  public String getOldGeoSetDisplayName() {
    return oldGeoSetDisplayName;
  }
  public void setOldGeoSetDisplayName(String oldGeoSetDisplayName) {
    this.oldGeoSetDisplayName = oldGeoSetDisplayName;
  }
  public String getInfoDisplayName() {
    return infoDisplayName;
  }
  public void setInfoDisplayName(String infoDisplayName) {
    this.infoDisplayName = infoDisplayName;
  }
  public String getOldBoneSetName() {
    return oldBoneSetName;
  }
  public void setOldBoneSetName(String oldBoneSetName) {
    this.oldBoneSetName = oldBoneSetName;
  }
  public String getOldGeo() {
    return oldGeo;
  }
  public void setOldGeo(String oldGeo) {
    this.oldGeo = oldGeo;
  }
  public String getOldTex1() {
    return oldTex1;
  }
  public void setOldTex1(String oldTex1) {
    this.oldTex1 = oldTex1;
  }
  public String getOldTex2() {
    return oldTex2;
  }
  public void setOldTex2(String oldTex2) {
    this.oldTex2 = oldTex2;
  }
  public String getNewGeoSetDisplayName() {
    return newGeoSetDisplayName;
  }
  public void setNewGeoSetDisplayName(String newGeoSetDisplayName) {
    this.newGeoSetDisplayName = newGeoSetDisplayName;
  }
  public String getNewBoneSetName() {
    return newBoneSetName;
  }
  public void setNewBoneSetName(String newBoneSetName) {
    this.newBoneSetName = newBoneSetName;
  }
  public String getNewGeo() {
    return newGeo;
  }
  public void setNewGeo(String newGeo) {
    this.newGeo = newGeo;
  }
  public String getNewTex1() {
    return newTex1;
  }
  public void setNewTex1(String newTex1) {
    this.newTex1 = newTex1;
  }
  public String getNewTex2() {
    return newTex2;
  }
  public void setNewTex2(String newTex2) {
    this.newTex2 = newTex2;
  }
  public String toString() {
    return String.format("\"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\" \"%s\"",
        this.oldGeoSetDisplayName, 
        this.infoDisplayName, 
        this.oldBoneSetName,
        this.oldGeo,
        this.oldTex1,
        this.oldTex2,
        this.newGeoSetDisplayName, 
        this.newBoneSetName,
        this.newGeo,
        this.newTex1,
        this.newTex2);
  }
  public static InfoMapping clone(InfoMapping clone) {
    InfoMapping newInfoMap = new InfoMapping();
    newInfoMap.setOldGeoSetDisplayName(clone.getOldGeoSetDisplayName());
    newInfoMap.setInfoDisplayName(clone.getInfoDisplayName());
    newInfoMap.setOldBoneSetName(clone.getOldBoneSetName());
    newInfoMap.setOldGeo(clone.getOldGeo());
    newInfoMap.setOldTex1(clone.getOldTex1());
    newInfoMap.setOldTex2(clone.getOldTex2());
    newInfoMap.setNewGeoSetDisplayName(clone.getNewGeoSetDisplayName());
    newInfoMap.setNewBoneSetName(clone.getNewBoneSetName());
    newInfoMap.setNewGeo(clone.getNewGeo());
    newInfoMap.setNewTex1(clone.getNewTex1());
    newInfoMap.setNewTex2(clone.getNewTex2());
    return newInfoMap;
  }
}
