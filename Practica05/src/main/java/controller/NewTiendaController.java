package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.CategoriesRepository;
import model.Producto;

/**
 * FXML Controller class
 *
 * @author mysfl
*/
public class NewTiendaController implements Initializable {

private ObservableList<String> listaCategories = null;
private Producto producto = null;
    @FXML
    private TextField tflId;
    @FXML
    private TextField tflNombre;
    @FXML
    private TextField tflDesc;
    @FXML
    private TextField tflPrecio;
    @FXML
    private ComboBox<String> cbCategory;

@Override
    public void initialize(URL url, ResourceBundle rb) {
        listaCategories = FXCollections.observableArrayList(CategoriesRepository.getCategories());
        cbCategory.setItems(listaCategories);
    }

    public Producto getProducto(){
        try {
            producto = new Producto(
                Integer.parseInt(tflId.getText()), 
                Float.parseFloat(tflPrecio.getText()), 
                tflNombre.getText(),
                cbCategory.getValue(),
                tflDesc.getText()
            );
        } catch (Exception e) {
        }
    return producto;
    }
}
