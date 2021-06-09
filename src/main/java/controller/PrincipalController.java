package controller;

import DAO.MovimentacaoDAO;
import DAO.SetorDAO;
import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Movimentacao;
import model.Setor;
import model.Usuario;
import model.UsuarioSingleton;
import view.Main;

import java.io.IOException;

public class PrincipalController {

    private LoginController loginController;
    public UsuarioSingleton singleton;
    public Main main = new Main();
    public Usuario usuario = new Usuario();

    public Usuario usrLogin;

    private SetorDAO setorDAO = new SetorDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

    String usr = usuario.getNome();

    @FXML
    public  Label usuarioLabel;

    @FXML
    public MenuItem menuItemSetor, menuItemUsuario;

    @FXML
    public TableView<Movimentacao> atividadeTableView;

    @FXML
    public TableColumn numeracaoTableColumn;

    @FXML
    public void initialize() {

        numeracaoTableColumn.setCellFactory(column -> new TableCell<Movimentacao, String>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                String index = empty ? null : getIndex() + 1 + "";
                getStyleClass().addAll("column-header");

                setGraphic(null);
                setText(index);
            }
        });

        numeracaoTableColumn.setMaxWidth(35);
        numeracaoTableColumn.setMinWidth(35);



    }

    public void getUserLogged (Usuario usr) {
        usuario = usr;
        System.out.println("Matrícula:" + usuario.getMatricula());
    }


    @FXML
    public void cadastrarUsuario() throws IOException {
        Stage usuarioStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cadastroUsuario.fxml"));
        Parent root = loader.load();

        UsuarioController usuarioController = loader.getController();
        usuarioController.setPrincipalController(this);
        usuarioStage.setTitle("CADASTRAR USUÁRIO");
        usuarioStage.setScene(new Scene(root));
        usuarioStage.setResizable(false);
        usuarioStage.show();
    }


    @FXML
    public void cadastrarSetor() throws IOException {
        Stage setorStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cadastroSetor.fxml"));
        Parent root = loader.load();

        SetorController setorController = loader.getController();
        setorController.setPrincipalController(this);
        setorStage.setTitle("CADASTRAR CREDOR");
        setorStage.setScene(new Scene(root));
        setorStage.setResizable(false);
        setorStage.show();
    }

    public void adicionarSetor(Setor setor) {
        if (setor.getNome() != null){
            setorDAO.inserir(setor);
        } else {

        }
    }

    public void adicionarUsuario(Usuario usuario) {
        if (usuario.getMatricula() != null) {
            usuarioDAO.cadastroUsuario(usuario);
        } else {
            usuarioDAO.cadastroUsuario(usuario);
        }

    }

    public void adicionarMovimentacao(Movimentacao movimentacao) {
        if (movimentacao.getSetor() != null) {
            movimentacaoDAO.cadastroMovimentacao(movimentacao);
        } else {
            movimentacaoDAO.cadastroMovimentacao(movimentacao);
        }
    }

    @FXML
    public void cadastrarMovimentacao() throws IOException {
        Stage setorStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cadastroMovimentacao.fxml"));
        Parent root = loader.load();

        MovimentacaoController movimentacaoController = loader.getController();
        movimentacaoController.setPrincipalController(this);
        movimentacaoController.setorComboBox.setValue(usrLogin.getSetor());
        movimentacaoController.usrTextField.setText(usrLogin.getNome());

        setorStage.setTitle("Cadastrar Atividade");
        setorStage.setScene(new Scene(root));
        setorStage.setResizable(false);
        setorStage.show();
    }

    public void setPrincipalController(LoginController controller) {
        loginController = controller;
    }
}
