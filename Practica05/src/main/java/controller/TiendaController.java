package controller;

import helpers.Dialogs;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Producto;
import model.ProductoRepository;

/**
 * FXML Controller class
 *
 * @author myslf
 */
public class TiendaController implements Initializable {

    @FXML
    private TableColumn<Producto, Integer> colId;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colDesc;
    @FXML
    private TableColumn<Producto, String> colCat;
    @FXML
    private TableColumn<Producto, Float> colPrecio;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAgregar;
    @FXML
    private TableView<Producto> tblProductos;

    private ObservableList<Producto> listaProductos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaProductos = FXCollections.observableArrayList(ProductoRepository.getProducto());
        this.colId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colDesc.setCellValueFactory(new PropertyValueFactory("desc"));
        this.colCat.setCellValueFactory(new PropertyValueFactory("cat"));
        this.tblProductos.setItems(listaProductos);
    }

    @FXML
    private void handleAgregar(ActionEvent event) throws IOException {
        //VISTA
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/NewTienda.fxml"));
        //
        DialogPane productoDialogPane = fxmlLoader.load();
        //VISTA CONTROLLER       
        NewTiendaController newTiendaController = fxmlLoader.getController();
        //DIÁLOGO MODAL
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(productoDialogPane);
        dialog.setTitle("Nuevo producto");
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        //IF's
        if (clickedButton.get() == ButtonType.OK) {
            Producto producto = newTiendaController.getProducto();
            if (producto != null) {
                if (!listaProductos.contains(producto)) {
                    listaProductos.add(producto);
                    Dialogs.showConfirmationDialog("¡ÉXITO!", null, "El producto se ha agregado exitosamente");
                    tblProductos.getSelectionModel().select(producto);
                } else {
                    Dialogs.showErrorDialog("ERROR", null, "Este libro ya existe");
                }
            } else {
                Dialogs.showErrorDialog("ERROR", null, "Por favor, no deje espacios en blanco");
            }
        }
//        //VISTA
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("/view/NewTienda.fxml"));
//        //VISTA CONTROLLER
//        NewTiendaController newtienda = fxmlLoader.getController();
//        //DIÁLOGO MODAL
//        DialogPane productoDialogPane = fxmlLoader.load();
//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.setDialogPane(productoDialogPane);
//        dialog.setTitle("NUEVO PRODUCTO");
//        Optional<ButtonType> clickedButton = dialog.showAndWait();
//        //IF's
//        if (clickedButton.get() == ButtonType.OK) {
//            Producto producto = newtienda.getProducto();
//            if (producto != null) {
//                if (!listaProductos.contains(producto)) {
//                    listaProductos.add(producto);
//                    Dialogs.showConfirmationDialog("¡ÉXITO!", null, "El producto se ha agregado exitosamente");
//                    tblProductos.getSelectionModel().select(producto);
//                } else {
//                    Dialogs.showErrorDialog("ERROR", null, "Este libro ya existe");
//                }
//            } else {
//                Dialogs.showErrorDialog("ERROR", null, "Por favor, no deje espacios en blanco");
//            }
//        } else {
//            Dialogs.showInformationDialog("¿¿??", null, "¿¿¿???");
//        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            Optional<ButtonType> result = Dialogs.showConfirmationDialog(
                    "Eliminar",
                    null,
                    "¿Está seguro de que desea eliminar el producto seleccionado?"
            );
            if (result.get() == ButtonType.YES) {
                listaProductos.remove(producto);
                tblProductos.getSelectionModel().select(null);
            }
        } else {
            Dialogs.showErrorDialog("ERROR", null, "Debe seleccionar un producto para poder eliminarlo");
        }
    }

}
