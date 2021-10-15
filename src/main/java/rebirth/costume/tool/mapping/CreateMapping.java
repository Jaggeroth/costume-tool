package rebirth.costume.tool.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rebirth.costume.tool.model.Component;
import rebirth.costume.tool.model.InfoMapping;
import rebirth.costume.tool.model.MaskMapping;

public class CreateMapping {

  public static List<InfoMapping> execute(String oldCtmPath, String newCtmPath) throws IOException {
    List<InfoMapping> ctmMappings = new ArrayList<InfoMapping>();
    return execute(oldCtmPath, newCtmPath, ctmMappings);
  }

  public static List<InfoMapping> execute(String oldCtmPath, String newCtmPath, List<InfoMapping> ctmMappings) throws IOException {
    List<Component> oldShirts = new ArrayList<Component>();
    List<Component> newShirts = new ArrayList<Component>();
    List<MaskMapping> maskMappings = new ArrayList<MaskMapping>();
    oldShirts = ProcessCtmFile.execute(oldCtmPath);
    newShirts = ProcessCtmFile.execute(newCtmPath);
    String oldBoneSet = oldShirts.get(0).getBoneSetName();
    String newBoneSet = newShirts.get(0).getBoneSetName();
    //System.out.println(String.format("%s to %s", oldBoneSet, newBoneSet));
    for (Component oldC : oldShirts) {
      if (oldC.getMaskName() != null) {
        //System.out.println("trying to match mask");
        // Match a Mask entry
        Component match = findMaskComponent(newBoneSet, oldC.getGeoSetDisplayName(), oldC.getMaskDisplayName(), newShirts);
        if (match != null) {
          MaskMapping mm = new MaskMapping();
          mm.setOldBoneSetName(oldC.getBoneSetName());
          mm.setOldGeoSetDisplayName(oldC.getGeoSetDisplayName());
          mm.setInfoDisplayName(oldC.getInfoDisplayName());
          mm.setOldGeo(oldC.getInfoGeo());
          mm.setOldMaskDisplayName(oldC.getMaskDisplayName());
          mm.setOldMaskName(oldC.getMaskName());
          mm.setNewGeoSetDisplayName(oldC.getGeoSetDisplayName());
          mm.setNewBoneSetName(match.getBoneSetName());
          mm.setNewGeo(match.getInfoGeo());
          mm.setNewMaskDisplayName(match.getMaskDisplayName());
          mm.setNewMaskName(match.getMaskName());
          maskMappings.add(mm);
        }
      } else {
        // Match Info entry
        Component match = findInfoComponent(newBoneSet, oldC.getGeoSetDisplayName(), oldC.getInfoDisplayName(), newShirts);
        if (match != null) {
          InfoMapping im = new InfoMapping();
          im.setOldBoneSetName(oldC.getBoneSetName());
          im.setOldGeoSetDisplayName(oldC.getGeoSetDisplayName());
          im.setInfoDisplayName(oldC.getInfoDisplayName());
          im.setOldGeo(oldC.getInfoGeo());
          im.setOldTex1(oldC.getInfoTex1());
          im.setOldTex2(oldC.getInfoTex2());
          //if ("mask".equalsIgnoreCase(oldC.getInfoTex2())) {
          //  im.setOldTex2("None");
          //}
          im.setNewGeoSetDisplayName(match.getGeoSetDisplayName());
          im.setNewBoneSetName(match.getBoneSetName());
          im.setNewGeo(match.getInfoGeo());
          im.setNewTex1(match.getInfoTex1());
          im.setNewTex2(match.getInfoTex2());
          //if ("mask".equalsIgnoreCase(match.getInfoTex2())) {
          //  im.setNewTex2("None");
          //}
          ctmMappings.add(im);
        }
      }
    }
    //for (MaskMapping mm : maskMappings ) {
    //  System.out.println(mm);
    //}
    List<InfoMapping> newMappings = new ArrayList<InfoMapping>();
    for (InfoMapping im : ctmMappings) {
      if ("mask".equalsIgnoreCase(im.getOldTex2())) {
        newMappings.addAll(findMasks(im, maskMappings));
       im.setOldTex2("None");
       if ("mask".equalsIgnoreCase(im.getNewTex2())) {
         im.setNewTex2("None");
       }
      }
    }
    ctmMappings.addAll(newMappings);
    return ctmMappings;
  }
  private static List<InfoMapping> findMasks(InfoMapping im, List<MaskMapping> maskMappings) {
    List<InfoMapping> newMappings = new ArrayList<InfoMapping>();
    for (MaskMapping mm : maskMappings) {
      if (im.getOldGeoSetDisplayName().equalsIgnoreCase(mm.getOldGeoSetDisplayName()) &&
          im.getOldBoneSetName().equalsIgnoreCase(mm.getOldBoneSetName()) &&
          im.getNewBoneSetName().equalsIgnoreCase(mm.getNewBoneSetName())) {
        InfoMapping newInfoMap = InfoMapping.clone(im);
        newInfoMap.setOldTex2(mm.getOldMaskName());
        newInfoMap.setNewTex2(mm.getNewMaskName());
        newMappings.add(newInfoMap);
        //System.out.println(newInfoMap);
      }
    }
    return newMappings;
  }
  private static Component findInfoComponent(String boneSetName, String geoSetDisplayName, String infoDisplayName, List<Component> components) {
    for (Component c : components) {
      if (c.getInfoDisplayName() != null) {
        if (c.getBoneSetName().equalsIgnoreCase(boneSetName) &&
            c.getGeoSetDisplayName().equalsIgnoreCase(geoSetDisplayName) &&
            c.getInfoDisplayName().equalsIgnoreCase(infoDisplayName)) {
          return c;
        }
      }
    }
    return null;
  }
  private static Component findMaskComponent(String boneSetName, String geoSetDisplayName, String maskDisplayName, List<Component> components) {
    for (Component c : components) {
      if (c.getMaskDisplayName() != null) {
        if (c.getBoneSetName().equalsIgnoreCase(boneSetName) &&
            c.getGeoSetDisplayName().equalsIgnoreCase(geoSetDisplayName) &&
            c.getMaskDisplayName().equalsIgnoreCase(maskDisplayName)) {
          return c;
        }
      }
    }
    return null;
  }
  private static boolean equalsGeo(final String geo1, final String geo2) {
    String g1 = geo1;
    String g2 = geo2;
    if (g1.endsWith("__PanelEmblemSplit")) {
      g1 = g1.substring(0, g1.length() - 18);
    }
    if (g2.endsWith("__PanelEmblemSplit")) {
      g2 = g2.substring(0, g2.length() - 18);
    }
    return g1.equalsIgnoreCase(g2);
  }
}
