package controller;

import DAO.BD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MovimentacaoController {
    BD bd = new BD();

    public Usuario usuario;

    private Movimentacao movimentacao;

    public PrincipalController principalController;

    private ObservableList<Orgao> orgaos = FXCollections.observableArrayList();
    private ObservableList<Localidade> localidades = FXCollections.observableArrayList();

    @FXML
    public ComboBox<String> atividadeComboBox, assuntoComboBox,
                    horaInicioComboBox, horaFimComboBox,
                    conclusaoComboBox;

    @FXML
    public ComboBox<Setor> setorComboBox;

    @FXML
    public ComboBox<Orgao> orgaoComboBox;

    @FXML
    public ComboBox<Localidade> localidadeComboBox;

    @FXML
    public Button cadastrarButton, fecharButton;

    @FXML
    public DatePicker dataRefDatePicker, dataInicioDatePicker, dataFimDatePicker;

    @FXML
    public CheckBox processoCheckBox;

    @FXML
    public TextField processoTextField, observacaoTextField, usrTextField;

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
        preMovimentacao();
    }

    public void preMovimentacao() {
        dataRefDatePicker.setValue(movimentacao.getDataRegistro());
        usrTextField.setText(movimentacao.getUsuario());
        atividadeComboBox.setValue(movimentacao.getAtividade());
        assuntoComboBox.setValue(movimentacao.getAssunto());
        orgaoComboBox.setValue(movimentacao.getOrgao());
        processoTextField.setText(movimentacao.getProcesso());
        localidadeComboBox.setValue(movimentacao.getLocal());
        dataInicioDatePicker.setValue(movimentacao.getDataInicio());
        dataFimDatePicker.setValue(movimentacao.getDataFim());
        horaInicioComboBox.setValue(movimentacao.getHoraInicio());
        horaFimComboBox.setValue(movimentacao.getHoraFim());
        conclusaoComboBox.setValue(movimentacao.getConclusao());
        observacaoTextField.setText(movimentacao.getObervação());
    }



    @FXML
    public void initialize() {
        dataRefDatePicker.setValue(LocalDate.now());

        orgaoComboBox.setItems(orgaos);
        orgaos.addAll(bd.getOrgao());

        localidadeComboBox.setItems(localidades);
        localidades.addAll(bd.getLocal());

        horaInicioComboBox.setItems(horario);
        horaFimComboBox.setItems(horario);

        atividadeComboBox.setItems(atividades);
        definirAssunto();

        processoCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                processoTextField.setDisable(false);
            } else {
                processoTextField.setDisable(true);
                processoTextField.setText("");
            }
        });

        Image cadastrar = new Image(getClass().getResourceAsStream("/verifica.png"));
        cadastrarButton.setGraphic(new ImageView(cadastrar));

        Image cancelar = new Image(getClass().getResourceAsStream("/cancelar.png"));
        fecharButton.setGraphic(new ImageView(cancelar));

    }

    public void verifyUser(Usuario usr) {

        if (usrTextField.getText() == usr.getNome()) {
            JOptionPane.showMessageDialog(null, "Você não pode editar uma atividade que pertence a outro usuário");
        } else {
            localidadeComboBox.setVisibleRowCount(0);
        }
    }

    @FXML
    public void fecharJanela() {
        fecharButton.getScene().getWindow().hide();
    }


    /*ObservableList<String> atividades = FXCollections.observableArrayList(
            "DIAD", "DJUR"
    );*/

    ObservableList<String> atividades = FXCollections.observableArrayList(
            "Emitir Certidão Daniel", "Emitir Certidão Nivea"
    );

    ObservableList<String> daniel = FXCollections.observableArrayList(
            "Assunto Daniel"
    );

    ObservableList<String> nivea = FXCollections.observableArrayList(
            "Assunto Nivea"
    );

    ObservableList<String> dipr = FXCollections.observableArrayList(
            "GEPES", "GERIN"
    );

    ObservableList<String> conclusao = FXCollections.observableArrayList(
            "Pronto", "Não finalizado"
    );

    ObservableList<String> conclusao2 = FXCollections.observableArrayList(
            "Pronto 2", "Não finalizado 2"
    );

    ObservableList<String> horario = FXCollections.observableArrayList(
            "08:00", "09:00"
    );

    public void definirAssunto() {

        atividadeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == "Emitir Certidão Daniel") {
                assuntoComboBox.getItems().clear();
                assuntoComboBox.setItems(daniel);
                conclusaoComboBox.setItems(conclusao);
            } else if (newValue == "Emitir Certidão Nivea"){
                assuntoComboBox.getItems().clear();
                assuntoComboBox.setItems(nivea);
                conclusaoComboBox.setItems(conclusao2);
            }
        });
    }

    @FXML
    public void cadastrar() {

        if (!validarDados()) {
            exibirMensagem("Existem campos em branco, por favor, preencher todos os campos!");
            return;
        }

        boolean editando = true;

        if (movimentacao == null){
            movimentacao = new Movimentacao();
            editando = false;
        }

        movimentacao.setSetor(setorComboBox.getValue());
        movimentacao.setDataRegistro(dataRefDatePicker.getValue());
        movimentacao.setAtividade(atividadeComboBox.getValue());
        movimentacao.setAssunto(assuntoComboBox.getValue());
        movimentacao.setProcesso(processoTextField.getText());
        movimentacao.setOrgao(orgaoComboBox.getValue());
        movimentacao.setLocal(localidadeComboBox.getValue());
        movimentacao.setDataInicio(dataInicioDatePicker.getValue());
        movimentacao.setDataFim(dataFimDatePicker.getValue());
        movimentacao.setHoraInicio(horaInicioComboBox.getValue());
        movimentacao.setHoraFim(horaFimComboBox.getValue());
        movimentacao.setConclusao(conclusaoComboBox.getValue());
        movimentacao.setObervação(observacaoTextField.getText());
        movimentacao.setUsuario(usrTextField.getText());

        if (editando == false){
            principalController.adicionarMovimentacao(movimentacao);
            movimentacao = null;
            cadastrarButton.getScene().getWindow().hide();
        } else {

        }

        System.out.println(movimentacao);

    }

    public boolean validarDados() {
        long diferenca = ChronoUnit.DAYS.between(dataFimDatePicker.getValue(), dataInicioDatePicker.getValue());

        System.out.println(diferenca);

        if (diferenca > 0) {
            JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }

        return true;
    }

    public void exibirMensagem(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void setPrincipalController(PrincipalController controller) {
        principalController = controller;
    }

}
