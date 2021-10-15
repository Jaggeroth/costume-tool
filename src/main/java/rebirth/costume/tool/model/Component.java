package rebirth.costume.tool.model;

public class Component {
  private String boneSetLegacy;
  private String boneSetName;
  private String boneSetDisplayName;
  private String geoSetDisplayName;
  private String geoSetBodyPart;
  private String geoSetType;
  private String infoCOV;
  private String infoDevOnly;
  private String infoDisplayName;
  private String infoGeo;
  private String infoGeoName;
  private String infoTex1;
  private String infoTex2;
  private String infoKey;
  private String infoProduct;
  private String infoIsMask;
  private String maskLegacy;
  private String maskDisplayName;
  private String maskName;
  public String getBoneSetLegacy() {
    return boneSetLegacy;
  }
  public void setBoneSetLegacy(String boneSetLegacy) {
    this.boneSetLegacy = boneSetLegacy;
  }
  public String getBoneSetName() {
    return boneSetName;
  }
  public void setBoneSetName(String boneSetName) {
    this.boneSetName = boneSetName;
  }
  public String getBoneSetDisplayName() {
    return boneSetDisplayName;
  }
  public void setBoneSetDisplayName(String boneSetDisplayName) {
    this.boneSetDisplayName = boneSetDisplayName;
  }
  public String getGeoSetDisplayName() {
    return geoSetDisplayName;
  }
  public void setGeoSetDisplayName(String geoSetDisplayName) {
    this.geoSetDisplayName = geoSetDisplayName;
  }
  public String getGeoSetBodyPart() {
    return geoSetBodyPart;
  }
  public void setGeoSetBodyPart(String geoSetBodyPart) {
    this.geoSetBodyPart = geoSetBodyPart;
  }
  public String getInfoDisplayName() {
    return infoDisplayName;
  }
  public void setInfoDisplayName(String infoDisplayName) {
    this.infoDisplayName = infoDisplayName;
  }
  public String getInfoGeo() {
    return infoGeo;
  }
  public void setInfoGeo(String infoGeo) {
    this.infoGeo = infoGeo;
  }
  public String getInfoTex1() {
    return infoTex1;
  }
  public void setInfoTex1(String infoTex1) {
    this.infoTex1 = infoTex1;
  }
  public String getInfoTex2() {
    return infoTex2;
  }
  public void setInfoTex2(String infoTex2) {
    this.infoTex2 = infoTex2;
  }
  public String getInfoKey() {
    return infoKey;
  }
  public void setInfoKey(String infoKey) {
    this.infoKey = infoKey;
  }
  public String getInfoProduct() {
    return infoProduct;
  }
  public void setInfoProduct(String infoProduct) {
    this.infoProduct = infoProduct;
  }
  public String getMaskLegacy() {
    return maskLegacy;
  }
  public void setMaskLegacy(String maskLegacy) {
    this.maskLegacy = maskLegacy;
  }
  public String getMaskDisplayName() {
    return maskDisplayName;
  }
  public void setMaskDisplayName(String maskDisplayName) {
    this.maskDisplayName = maskDisplayName;
  }
  public String getMaskName() {
    return maskName;
  }
  public void setMaskName(String maskName) {
    this.maskName = maskName;
  }
  public String getInfoGeoName() {
    return infoGeoName;
  }
  public void setInfoGeoName(String infoGeoName) {
    this.infoGeoName = infoGeoName;
  }
  public String getInfoDevOnly() {
    return infoDevOnly;
  }
  public void setInfoDevOnly(String infoDevOnly) {
    this.infoDevOnly = infoDevOnly;
  }
  public String getInfoCOV() {
    return infoCOV;
  }
  public void setInfoCOV(String infoCOV) {
    this.infoCOV = infoCOV;
  }
  public String getInfoIsMask() {
    return infoIsMask;
  }
  public void setInfoIsMask(String infoIsMask) {
    this.infoIsMask = infoIsMask;
  }
  public String getGeoSetType() {
    return geoSetType;
  }
  public void setGeoSetType(String geoSetType) {
    this.geoSetType = geoSetType;
  }
  public Component clone() {
    Component clone = new Component();
    clone.setBoneSetDisplayName(this.boneSetDisplayName);
    clone.setBoneSetLegacy(this.boneSetLegacy);
    clone.setBoneSetName(this.boneSetName);
    clone.setGeoSetBodyPart(this.geoSetBodyPart);
    clone.setGeoSetDisplayName(this.geoSetDisplayName);
    clone.setGeoSetType(this.geoSetType);
    clone.setInfoDisplayName(this.infoDisplayName);
    clone.setInfoGeo(this.infoGeo);
    clone.setInfoKey(this.infoKey);
    clone.setInfoProduct(this.infoProduct);
    clone.setInfoTex1(this.infoTex1);
    clone.setInfoTex2(this.infoTex2);
    clone.setMaskDisplayName(this.maskDisplayName);
    clone.setMaskLegacy(this.maskLegacy);
    clone.setMaskName(this.maskName);
    clone.setInfoDevOnly(this.infoDevOnly);
    clone.setInfoGeoName(this.infoGeoName);
    clone.setInfoCOV(this.infoCOV);
    clone.setInfoIsMask(this.infoIsMask);
    return clone;
  }
  public void clearInfoMask() {
    this.infoDisplayName = null;
    this.infoGeo = null;
    this.infoGeoName = null;
    this.infoKey = null;
    this.infoProduct = null;
    this.infoTex1 = null;
    this.infoTex2 = null;
    this.maskDisplayName = null;
    this.maskLegacy = null;
    this.maskName = null;
    this.infoDevOnly = null;
    this.infoCOV = null;
    this.infoIsMask = null;
  }
  public void clearGeoSet() {
    this.geoSetBodyPart = null;
    this.geoSetDisplayName = null;
    this.geoSetType = null;
  }
  public void clearBoneSet() {
    this.boneSetLegacy = null;
    this.boneSetName = null;
    this.boneSetDisplayName = null;
  }
  public String toString() {
    return String.format("%s %s %s %s %s %s %s %s %s %s %s %s %s",
        this.boneSetLegacy,
        this.boneSetName,
        this.boneSetDisplayName,
        this.geoSetDisplayName,
        this.geoSetBodyPart,
        this.infoDisplayName,
        this.infoGeo,
        this.infoGeoName,
        this.infoTex1,
        this.infoTex2,
        this.maskLegacy,
        this.maskDisplayName,
        this.maskName);
  }
}
