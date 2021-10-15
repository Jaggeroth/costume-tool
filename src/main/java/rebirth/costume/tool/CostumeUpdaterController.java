package rebirth.costume.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import rebirth.costume.tool.mapping.ProcessCostumeFile;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CostumeUpdaterController  {
  public class AboutWindow extends Stage {
    public AboutWindow () {
      super();

      Parent root;
      try {
        root = FXMLLoader.load(getClass().getResource("/about.fxml"));
        Scene scene = new Scene(root);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("About");
        this.setScene(scene);
        this.setResizable(false);
      } catch (IOException e) {
        // Do nothing
      }
    }
  }
  AboutWindow popup = new AboutWindow();
  @FXML 
  private VBox vBox;
  @FXML 
  private Button sourceButton;
  @FXML
  private Button targetButton;
  @FXML
  private Button executeButton;
  @FXML
  private Button exitButton;
  @FXML
  private Label sourceLabel;
  @FXML
  private Label targetLabel;
  @FXML
  private ProgressBar progressBar;
  
  private DirectoryChooser directoryChooser;
  private File selectedDirectory;

  public Button getSourceButton() {
    return sourceButton;
  }

  public void setSourceButton(Button sourceButton) {
    this.sourceButton = sourceButton;
  }

  public Button getTargetButton() {
    return targetButton;
  }

  public void setTargetButton(Button targetButton) {
    this.targetButton = targetButton;
  }

  public Button getExecuteButton() {
    return executeButton;
  }

  public void setExecuteButton(Button executeButton) {
    this.executeButton = executeButton;
  }

  public Button getExitButton() {
    return exitButton;
  }

  public void setExitButton(Button exitButton) {
    this.exitButton = exitButton;
  }

  public Label getSourceLabel() {
    return sourceLabel;
  }

  public void setSourceLabel(Label sourceLabel) {
    this.sourceLabel = sourceLabel;
  }

  public Label getTargetLabel() {
    return targetLabel;
  }

  public void setTargetLabel(Label targetLabel) {
    this.targetLabel = targetLabel;
  }

  @FXML
  private void handleMenuAction(ActionEvent event) {
    if (event.getSource() instanceof MenuItem) {
      MenuItem pressedMenu = (MenuItem)event.getSource();
      if ("menuAbout".equalsIgnoreCase(pressedMenu.getId())) {
        popup.show();
      } else if ("menuQuit".equalsIgnoreCase(pressedMenu.getId())) {
        Platform.exit();
      }
    }
  }

  @FXML
  private void handleButtonAction(ActionEvent event) {
    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    if (event.getSource() instanceof Button) {
      Button pressedB = (Button)event.getSource();
      if ("sourceButton".equalsIgnoreCase(pressedB.getId())) {
        if (!"Unknown".equalsIgnoreCase(sourceLabel.getText())) {
          directoryChooser.setInitialDirectory(new File(sourceLabel.getText()));
        }
        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
          sourceLabel.setText(selectedDirectory.getAbsolutePath());
          progressBar.setProgress(0);
        } else {
          sourceLabel.setText("Unknown");
        }
      } else if ("targetButton".equalsIgnoreCase(pressedB.getId())) {
        if (!"Unknown".equalsIgnoreCase(targetLabel.getText())) {
          directoryChooser.setInitialDirectory(new File(targetLabel.getText()));
        }
        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
          targetLabel.setText(selectedDirectory.getAbsolutePath());
          progressBar.setProgress(0);
        } else {
          targetLabel.setText("Unknown");
        }
      } else if ("executeButton".equalsIgnoreCase(pressedB.getId())) {
        executeButton.setDisable(true);
        // Do stuff
        Thread taskThread = new Thread(new Runnable() {
          @Override
          public void run() {
            ProcessCostumeFile pcf = new ProcessCostumeFile();
            List<File> costumeFiles = listFiles(sourceLabel.getText());
            int prg = 0;
            for (File costumeFile : costumeFiles) {
              prg++;
              String inFile = costumeFile.getAbsolutePath();
              String outFile = newFileName(targetLabel.getText(), costumeFile.getName());
              String content;
              try {
                content = pcf.execute(inFile);
                writeFile(outFile, content);
              } catch (IOException e) {
                e.printStackTrace();
              }
              //
              final double reportedProgress = ((double)prg)/(double)costumeFiles.size();
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  progressBar.setProgress(reportedProgress);
                  if (reportedProgress < 1) {
                    executeButton.setDisable(true);
                  } else {
                    executeButton.setDisable(false);
                  }
                }
              }
                  );
              //
            } // end for
          }
        });
        taskThread.start();
      } else if ("exitButton".equalsIgnoreCase(pressedB.getId())) {
        Platform.exit();
      }
      setExecuteButton();
    }
  }
  /*
   * Look for costume files
   */
  private static List<File> listFiles(final String dirLocation) {
    try {
      List<File> files = Files.list(Paths.get(dirLocation))
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith("costume"))
          .map(Path::toFile)
          .collect(Collectors.toList());

      return files;
    } catch (IOException e) {
      // Error while reading the directory
      return null;
    }
  }
  /*
   * basic file write
   */
  private static void writeFile(String path, String content) {
    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
      writer.write(content);
    } catch (IOException ie) {
      System.out.println(String.format("Could not write to %s", path));
    }
  }
  /*
   * create new file name based on old
   */
  private static String newFileName(String dir, String fileName) {
    if (fileName.toLowerCase().endsWith(".costume")) {
      fileName = fileName.replace(".", "_v1.");
    }
    return String.format("%s\\%s", dir, fileName.substring(0, fileName.indexOf("."))+".v2costume");
  }
  private void setExecuteButton() {
    if ("Unknown".equalsIgnoreCase(sourceLabel.getText())) {
      executeButton.setDisable(true);
    } else if ("Unknown".equalsIgnoreCase(targetLabel.getText())) {
      executeButton.setDisable(true);
    } else if (sourceLabel.getText().equalsIgnoreCase(targetLabel.getText())) {
      executeButton.setDisable(true);
    } else {
      executeButton.setDisable(false);
    }
  }
  
  @FXML
  void initialize() {
    vBox.setMaxSize(640, 400);
    vBox.setMinSize(640, 400);
    sourceLabel.setText("Unknown");
    targetLabel.setText("Unknown");
    executeButton.setDisable(true);
    progressBar.setProgress(0.0);
    directoryChooser = new DirectoryChooser();
    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
  }
}
