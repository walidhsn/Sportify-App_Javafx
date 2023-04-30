/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import entities.Academy;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.AcademyCRUD;

/**
 *
 * @author ramib
 */
public class AcademyList_1 implements Initializable {

    private Label label;
    private ObservableList<Academy> academyList;
    private AcademyCRUD academyCRUD = new AcademyCRUD();
    @FXML
    private GridPane gridAcademy;
    private int numColumns = 3;
    private List<Academy> academy_list_search;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView btnClear;
    @FXML
    private Pagination academyPagination;
    // Define the number of academies to be displayed per page
    private final int ACADEMIES_PER_PAGE = 6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridAcademy.setVgap(20);
        gridAcademy.setHgap(80);
        academyList = FXCollections.observableArrayList(academyCRUD.display());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            academy_list_search = academyList.stream()
                    .filter(academy -> 
                        academy.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                        academy.getCategory().toLowerCase().contains(newValue.toLowerCase()))
                    .collect(Collectors.toList());
            academyPagination.setPageCount((academy_list_search.size() + ACADEMIES_PER_PAGE - 1) / ACADEMIES_PER_PAGE);
            academyPagination.setCurrentPageIndex(0);
            academyPagination.setPageFactory(this::createPage);
        });

        academy_list_search = academyList;
        academyPagination.setPageCount((academy_list_search.size() + ACADEMIES_PER_PAGE - 1) / ACADEMIES_PER_PAGE);
        academyPagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
    GridPane pageGrid = new GridPane();
    pageGrid.setVgap(20);
    pageGrid.setHgap(80);
    int startIndex = pageIndex * ACADEMIES_PER_PAGE;
    int endIndex = Math.min(startIndex + ACADEMIES_PER_PAGE, academyList.size());
    int rowIndex = 0;
    int colIndex = 0;
    for (int i = startIndex; i < endIndex; i++) {
        Academy academy = academyList.get(i);
        ImageView imageView = new ImageView(new Image("file:C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/" + academy.getImageName()));
        imageView.setFitWidth(170);
        imageView.setFitHeight(150);
        Label nameLabel = new Label(academy.getName() + " (" + academy.getCategory() + ")");
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(nameLabel, Priority.ALWAYS);
        GridPane.setVgrow(nameLabel, Priority.NEVER);

        // Add event listener to navigate to academy details
        imageView.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/AcademyDetails_1.fxml"));
                Parent root = loader.load();
                AcademyDetails_1 controller = loader.getController();
                controller.setAcademy(academy);
                Stage stage = (Stage) gridAcademy.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        nameLabel.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Gui/AcademyDetails_1.fxml"));
                Parent root = loader.load();
                AcademyDetails_1 controller = loader.getController();
                controller.setAcademy(academy);
                Stage stage = (Stage) gridAcademy.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pageGrid.add(imageView, colIndex, rowIndex);
        pageGrid.add(nameLabel, colIndex, rowIndex + 1);
        colIndex++;
        if (colIndex >= numColumns) {
            colIndex = 0;
            rowIndex += 2;
        }
    }
    return pageGrid;
}



    @FXML
    private void handleClearButtonClick(MouseEvent event) throws IOException {
        searchField.clear();
        academyList = FXCollections.observableArrayList(academyCRUD.display());
        academyPagination.setPageCount((academyList.size() + ACADEMIES_PER_PAGE - 1) / ACADEMIES_PER_PAGE);
        academyPagination.setCurrentPageIndex(0);
        academyPagination.setPageFactory(this::createPage);
    }


    @FXML
    private void Auth(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Gui/Auth.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
