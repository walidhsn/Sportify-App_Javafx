/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package louaypi.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import louaypi.entities.Categorie;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import louaypi.services.ProduitCRUD;
import louaypi.services.CategorieCrud;
/**
 * FXML Controller class
 *
 * @author louay
 */
public class StatController implements Initializable {
    CategorieCrud cd = new CategorieCrud();
    ProduitCRUD pc= new ProduitCRUD();
    @FXML
    private BorderPane border;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       List<Categorie> l =cd.lister_categories();
        ObservableList lc=FXCollections.observableArrayList(l);
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Categorie element : l) 
    // Utiliser l'Ã©lÃ©ment
 {
    
    
    pieChartData.add(new PieChart.Data(element.getNom_cat(), pc.prodparcat(element.getId())));
}
      PieChart pieChart = new PieChart(pieChartData);

        pieChart.setTitle("Nombre des produits par categorie"); //Setting the title of the Pie chart
        pieChart.setClockwise(true); //setting the direction to arrange the data 
        pieChart.setLabelLineLength(50); //Setting the length of the label line 
        pieChart.setLabelsVisible(true); //Setting the labels of the pie chart visible
        pieChart.setLegendVisible(true);
        pieChart.setStartAngle(180);// TODO
        for (final PieChart.Data data : pieChart.getData()) {
    Node sliceNode = data.getNode();
    Tooltip tooltip = new Tooltip(); // Create a new Tooltip
    
    // Set the tooltip text to display the number of products
    tooltip.setText(String.valueOf(data.getPieValue()));
    
    // Set the tooltip to show when the mouse hovers over the slice node
    Tooltip.install(sliceNode, tooltip);
}
        border.setCenter(pieChart);
        
    }        
    
}
