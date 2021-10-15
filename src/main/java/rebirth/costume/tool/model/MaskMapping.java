package rebirth.costume.tool.model;

public class MaskMapping {
  private String oldGeoSetDisplayName;
  private String infoDisplayName;
  private String oldBoneSetName;
  private String oldGeo;
  private String oldMaskDisplayName;
  private String oldMaskName;
  private String newGeoSetDisplayName;
  private String newBoneSetName;
  private String newGeo;
  private String newMaskDisplayName;
  private String newMaskName;
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
  public String getOldMaskDisplayName() {
    return oldMaskDisplayName;
  }
  public void setOldMaskDisplayName(String oldMaskDisplayName) {
    this.oldMaskDisplayName = oldMaskDisplayName;
  }
  public String getOldMaskName() {
    return oldMaskName;
  }
  public void setOldMaskName(String oldMaskName) {
    this.oldMaskName = oldMaskName;
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
  public String getNewMaskDisplayName() {
    return newMaskDisplayName;
  }
  public void setNewMaskDisplayName(String newMaskDisplayName) {
    this.newMaskDisplayName = newMaskDisplayName;
  }
  public String getNewMaskName() {
    return newMaskName;
  }
  public void setNewMaskName(String newMaskName) {
    this.newMaskName = newMaskName;
  }
  public String toString() {
    return String.format("%s %s %s %s %s %s %s %s %s %s %s",
        this.oldGeoSetDisplayName,
        this.infoDisplayName,
        this.oldBoneSetName,
        this.oldGeo,
        this.oldMaskDisplayName,
        this.oldMaskName,
        this.newGeoSetDisplayName,
        this.newBoneSetName,
        this.newGeo,
        this.newMaskDisplayName,
        this.newMaskName);
  }
}
