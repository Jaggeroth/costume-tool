package rebirth.costume.tool;

import java.net.URL;

public class Test {

  public static void main(String[] args) {
    URL url1 =  Test.class.getClassLoader().getResource("tool.fxml");
    URL url2 =  Test.class.getClassLoader().getResource("female-old-hips/menu_armored.ctm");
    System.out.println(url2.getPath());
  }

}
