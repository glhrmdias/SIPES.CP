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
import util.ComboBoxKeyCompleter;

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

        /*definirAssunto();*/

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

        ComboBoxKeyCompleter completer = new ComboBoxKeyCompleter();
        completer.install(atividadeComboBox);
        completer.install(assuntoComboBox);

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

    // Atividades e Assuntos CORAFI
    ObservableList<String> atvCoraf = FXCollections.observableArrayList(
            "APURAÇÃO DE DÉBITOS PREVIDENCIÁRIOS(Contrib. Prev)",
            "APURAÇÃO DE DÉBITOS PREVIDENCIÁRIOS(Benef. Prev)",
            "ATENDIMENTO DEMANDA OUTROS SETORES",
            "AUDITORIA"
    );

    ObservableList<String> assCorafi1 = FXCollections.observableArrayList(
            "Licença Sem Remuneração LSR", "À Disposição",
            "Mandato Eletivo", "Cartorário", "Outros"
    );

    ObservableList<String> assCorafi2 = FXCollections.observableArrayList(
            "Resíduos de Aposentadoria", "Resíduos de Pensão",
            "Pensões Irregulares", "Outros"
    );

    ObservableList<String> assCorafi3 = FXCollections.observableArrayList(
            "Averiguação de débitos de contribuição previdenciária",
            "Averiguação de débitos de contribuição previdenciária de instituidores de pensão e pensionistas",
            "Suspender/cancelar/sobrestar cobrança administrativa de contribuição previdenciária por decisão judicial",
            "Proceder Busca em Cartórios por Bens de Notificados",
            "Proceder Busca de endereços de Notificados",
            "Responder Diligências do TCE/SC",
            "Outros"
    );

    ObservableList<String> assCorafi4 = FXCollections.observableArrayList(
            "Auditoria em Benefícios Previdenciários(Aposentadoria)",
            "Auditoria em Benefícios Previdenciários(Pensão)",
            "Auditoria em Contribuições Previdenciárias(Servidor)",
            "Auditoria em Contribuições Previdenciárias(Patronal)"
    );

    public void corafi() {
        atividadeComboBox.setItems(atvCoraf);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "APURAÇÃO DE DÉBITOS PREVIDENCIÁRIOS(Contrib. Prev)"){
                assuntoComboBox.setItems(assCorafi1);
            } else if (newValue == "APURAÇÃO DE DÉBITOS PREVIDENCIÁRIOS(Benef. Prev)") {
                assuntoComboBox.setItems(assCorafi2);
            } else if (newValue == "ATENDIMENTO DEMANDA OUTROS SETORES") {
                assuntoComboBox.setItems(assCorafi3);
            } else if (newValue == "AUDITORIA") {
                assuntoComboBox.setItems(assCorafi4);
            }
        });
    }


    // Atividades e assuntos GECOMP
    ObservableList<String> atvGecomp = FXCollections.observableArrayList(
            "ANALISE DE COMP. (RO) IPREV--->RGPS/INSS",
            "ANALISE DE COMP. (RO) IPREV SOLICITANTE RGPS/INSS",
            "ANALISE DE COMP.(RO) DAS PENSÕES",
            "ANALISE DE COMP. (RI) SENDO O INSS SOLICITANTE",
            "CORREÇÃO DOS FORMULÁRIOS DE ANALISE(SIST. ANTIGO)",
            "DIGITALIZAÇÃO DE DOC. FÍSICOS AOS ANALISTAS E PERITOS DO COMPREV",
            "EMISSÃO DE RELATÓRIOS",
            "REGISTRO DE ANÁLISE DE DEMANDAS(SIST. RAFAEL)",
            "ANALISE DAS APOSENT. POR INVALIDEZ POR MÉDICO PERITO NO SISTEMA COMPREV.",
            "ANALISE DE PENSÃO FILHO MAIOR INVÁLIDO POR MÉDICO PERITO NO SISTEMA COMPREV"
    );

    ObservableList<String> assGecomp1 = FXCollections.observableArrayList(
            "Registro de análise e cadastro de demandas(IPREV--->RGPS/INSS)",
            "AConfecção de documentos(DPT e Certidões Específicas)(IPREV--->RGPS/INSS)",
            "Diligencia para setorial(IPREV--->RGPS/INSS)",
            "Requerimento no COMPREV e envio das imagens de documentos(IPREV--->RGPS/INSS)",
            "Cadastro de DTC no COMPREV(IPREV--->RGPS/INSS)",
            "Solicitação de processo ou documento ao setorial(IPREV--->RGPS/INSS)",
            "Encaminhamento de laudo médico das aposent. por invalidez aos peritos(IPREV--->RGPS/INSS)"
    );

    ObservableList<String> assGecomp2 = FXCollections.observableArrayList(
            "Registro de análise e cadastro de demandas(IPREV--->RPPS)",
            "Confecção de documentos(DPT e Certidões Específicas)(IPREV--->RPPS)",
            "Diligencia para setorial(IPREV--->RPPS)",
            "Requerimento no COMPREV e envio das imagens de documentos(IPREV--->RPPS)",
            "Respostas de diligencias ao COMPREV(IPREV--->RPPS)",
            "Cadastro de DTC no COMPREV(IPREV--->RPPS)",
            "Solicitação de processo ou documento ao setorial(IPREV--->RPPS)",
            "Encaminhamento de laudo médico das aposent. por invalidez aos peritos(IPREV--->RPPS)"
    );

    public void gecomp() {
        atividadeComboBox.setItems(atvGecomp);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "ANALISE DE COMP. (RO) IPREV--->RGPS/INSS"){
                assuntoComboBox.setItems(assGecomp1);
            } else if (newValue == "ANALISE DE COMP. (RO) IPREV SOLICITANTE RGPS/INSS") {
                assuntoComboBox.setItems(assGecomp2);
            } else if (newValue == "ANALISE DE COMP.(RO) DAS PENSÕES") {

            } else if (newValue == "ANALISE DE COMP. (RI) SENDO O INSS SOLICITANTE") {

            } else if (newValue == "CORREÇÃO DOS FORMULÁRIOS DE ANALISE(SIST. ANTIGO)") {

            } else if (newValue == "DIGITALIZAÇÃO DE DOC. FÍSICOS AOS ANALISTAS E PERITOS DO COMPREV") {

            } else if (newValue == "EMISSÃO DE RELATÓRIOS") {

            } else if (newValue == "REGISTRO DE ANÁLISE DE DEMANDAS(SIST. RAFAEL)") {

            } else if (newValue == "ANALISE DAS APOSENT. POR INVALIDEZ POR MÉDICO PERITO NO SISTEMA COMPREV.") {

            } else if (newValue == "ANALISE DE PENSÃO FILHO MAIOR INVÁLIDO POR MÉDICO PERITO NO SISTEMA COMPREV") {

            }
        });
    }


    ObservableList<String> atvGetig = FXCollections.observableArrayList(
            "Arrumar computador",
            "Arrumar monitor"
    );

    ObservableList<String> assGetig = FXCollections.observableArrayList(
            "Software",
            "Hardware"
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

    ObservableList<String> conclusao2 = FXCollections.observableArrayList(
            "Pronto 2", "Não finalizado 2"
    );

    ObservableList<String> horario = FXCollections.observableArrayList(
            "08:00", "09:00"
    );

    ObservableList<String> conclusao = FXCollections.observableArrayList(
            "Deferido", "Suspenso", "Diligenciado", "Parecer jurídico", "Aguardando análise",
            "Aguardando compensação", "Compensado", "Criado", "Em análise", "Indeferido", "Incluído",
            "Alterado", "Bloqueado", "Encaminhado", "Emitido"
    );

    public void getig() {
        atividadeComboBox.setItems(atvGetig);
        assuntoComboBox.setItems(assGetig);
    }

    public void definirAssunto(Usuario usuario) {
         // o sistema vai pegar o usuario que está logado e vai ver qual o setor ele pertence
         // conforme o setor que o usuário pertencer, vai definir as atividades e os assuntos

        /*atividadeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == "Emitir Certidão Daniel") {
                assuntoComboBox.getItems().clear();
                assuntoComboBox.setItems(daniel);
                conclusaoComboBox.setItems(conclusao);
            } else if (newValue == "Emitir Certidão Nivea"){
                assuntoComboBox.getItems().clear();
                assuntoComboBox.setItems(nivea);
                conclusaoComboBox.setItems(conclusao2);
            }
        });*/
    }

    @FXML
    public void cadastrar() {

        if (!validarDados()) {
            //exibirMensagem("Existem campos em branco, por favor, preencher todos os campos!");
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

        if (setorComboBox.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Setor em branco!");
            return false;
        }

        if (assuntoComboBox.getValue() == null) {
            JOptionPane.showMessageDialog(null, "Setor em branco!");
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
