package controller;

import DAO.BD;
import DAO.MovimentacaoDAO;
import DAO.SetorDAO;
import DAO.UsuarioDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import org.w3c.dom.html.HTMLBaseElement;
import view.Main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrincipalController {

    private LoginController loginController;
    public UsuarioSingleton singleton;
    public Main main = new Main();
    public Usuario usuario = new Usuario();
    public BD bd = new BD();
    public Usuario usrLogin;
    public String usrLoginName;

    private SetorDAO setorDAO = new SetorDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();

    String usr = usuario.getNome();

    @FXML
    public  Label usuarioLabel, horaLabel;

    @FXML
    public MenuItem menuItemSetor, menuItemUsuario;

    @FXML
    public TableView<Movimentacao> atividadeTableView;

    @FXML
    public TableColumn numeracaoTableColumn;

    @FXML
    public TableColumn<Movimentacao, String> nomeTableColumn, atividadeTableColumn,
            conclusaoTableColumn, observacaoTableColumn, assuntoTableColumn;

    @FXML
    public TableColumn<Movimentacao, Orgao> orgaoTableColumn;

    @FXML
    public TableColumn<Movimentacao, Localidade> localTableColumn;

    @FXML
    public TableColumn<Movimentacao, LocalDate> dtInicioTableColumn, dtFimTableColumn;

    @FXML
    public TableColumn<Movimentacao, Setor> setorTableColumn;

    @FXML
    public Menu cadastrarMenu, editarMenu;

    @FXML
    public Button cadastrarButton, excluirButton;

    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    public SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public Date hora = Calendar.getInstance().getTime();

    public LocalDateTime dataAtual = LocalDateTime.now();
    public Date horaAtual = new Date();

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

       nomeTableColumn.setCellValueFactory(param -> {
           return new SimpleObjectProperty(param.getValue().getUsuario());
       });

        atividadeTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getAtividade());
        });

        assuntoTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getAssunto());
        });

        conclusaoTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getConclusao());
        });

        observacaoTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getObervação());
        });

        orgaoTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getOrgao());
        });

        localTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getLocal());
        });

        dtInicioTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(dateTimeFormatter.format(param.getValue().getDataInicio()));
        });

        dtFimTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(dateTimeFormatter.format(param.getValue().getDataFim()));
        });

        setorTableColumn.setCellValueFactory(param -> {
            return new SimpleObjectProperty(param.getValue().getSetor());
        });

        Image cadastrar = new Image(getClass().getResourceAsStream("/add.png"));
        cadastrarButton.setGraphic(new ImageView(cadastrar));

        Image excluir = new Image(getClass().getResourceAsStream("/excluir.png"));
        excluirButton.setGraphic(new ImageView(excluir));

        horaLabel.setText(dateTimeFormatter.format(dataAtual) + " " + sdf.format(hora));

        doubleClickSap();



    }

    public void refreshTable() {
        atividadeTableView.refresh();
    }
    /*public void attTable(Setor setor) {
        List<Movimentacao> movimentacaos = bd.listarMovimentacao(setor.getId());
        atividadeTableView.getItems().clear();
        atividadeTableView.getItems().addAll(movimentacaos);
    }*/

    public void attTable(Usuario usr) {
        List<Movimentacao> movimentacaos = bd.listarMovimentacaoNome(usr.getNome());
        atividadeTableView.getItems().clear();
        atividadeTableView.getItems().addAll(movimentacaos);
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
            attTable(usrLogin);
            System.out.println("O setor do usuário é: " + usuario.getSetor());
        } else {
            movimentacaoDAO.cadastroMovimentacao(movimentacao);
            attTable(usrLogin);
            System.out.println("O setor do usuário é: " + usuario.getSetor());
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

    @FXML
    public void editarMovimentacao() throws IOException {
        Stage setorStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cadastroMovimentacao.fxml"));
        Parent root = loader.load();

        MovimentacaoController movimentacaoController = loader.getController();
        movimentacaoController.setPrincipalController(this);
        movimentacaoController.setorComboBox.setValue(usrLogin.getSetor());
        movimentacaoController.usrTextField.setText(usrLogin.getNome());
        movimentacaoController.setMovimentacao(atividadeTableView.getSelectionModel().getSelectedItem());

        setorStage.setTitle("Cadastrar Atividade");
        setorStage.setScene(new Scene(root));
        setorStage.setResizable(false);
        setorStage.show();
    }

    public void doubleClickSap() {
        atividadeTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {
                            editarMovimentacao();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

    }

    public void setPrincipalController(LoginController controller) {
        loginController = controller;
    }
}
