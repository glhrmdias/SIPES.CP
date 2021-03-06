package controller;

import DAO.BD;
import DAO.MovimentacaoDAO;
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
    MovimentacaoDAO movDAO = new MovimentacaoDAO()
;
    public Usuario usuario;

    private Movimentacao movimentacao;

    public PrincipalController principalController;

    private ObservableList<Orgao> orgaos = FXCollections.observableArrayList();
    private ObservableList<Localidade> localidades = FXCollections.observableArrayList();

    @FXML
    public ComboBox<String> atividadeComboBox, assuntoComboBox,
                    horaInicioComboBox, horaFimComboBox,
                    conclusaoComboBox, tempoComboBox;

    @FXML
    public ComboBox<Setor> setorComboBox;

    @FXML
    public ComboBox<Orgao> orgaoComboBox;

    @FXML
    public ComboBox<Localidade> localidadeComboBox;

    @FXML
    public Button cadastrarButton, fecharButton, orgaoButton, localButton;

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
        //horaInicioComboBox.setValue(movimentacao.getHoraInicio());
        tempoComboBox.setValue(movimentacao.getTempoAtividade());
        conclusaoComboBox.setValue(movimentacao.getConclusao());
        observacaoTextField.setText(movimentacao.getOberva????o());
    }



    @FXML
    public void initialize() {
        dataRefDatePicker.setValue(LocalDate.now());

        orgaoComboBox.setItems(orgaos);
        orgaos.addAll(bd.getOrgao());

        localidadeComboBox.setItems(localidades);
        localidades.addAll(bd.getLocal());

        //horaInicioComboBox.setItems(horario);
        tempoComboBox.setItems(tempo);

        /*definirAssunto();*/

        processoCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                processoTextField.setDisable(false);
            } else {
                processoTextField.setDisable(true);
                processoTextField.setText("");
            }
        });

        Image cadastrar = new Image(getClass().getResourceAsStream("/edit1.png"));
        cadastrarButton.setGraphic(new ImageView(cadastrar));

        Image cancelar = new Image(getClass().getResourceAsStream("/can1.png"));
        fecharButton.setGraphic(new ImageView(cancelar));

        ComboBoxKeyCompleter completer = new ComboBoxKeyCompleter();
        completer.install(atividadeComboBox);
        completer.install(assuntoComboBox);
        completer.install(conclusaoComboBox);
        completer.install(localidadeComboBox);
        completer.install(orgaoComboBox);

    }

    @FXML
    public void listaOrgao() throws IOException {
        Stage usuarioStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("listaOrgaos.fxml"));
        Parent root = loader.load();

        ListOrgaoController listOrgaoController = loader.getController();
        listOrgaoController.setPrincipalController(this);
        usuarioStage.setTitle("Lista Secretarias e ??rg??os");
        usuarioStage.setScene(new Scene(root));
        usuarioStage.setResizable(false);
        usuarioStage.show();
    }

    @FXML
    public void listaLocal() throws IOException {
        Stage usuarioStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("listaLocalidades.fxml"));
        Parent root = loader.load();

        ListLocalidadeController listLocalidadeController = loader.getController();
        listLocalidadeController.setPrincipalController(this);
        usuarioStage.setTitle("Lista Localidades");
        usuarioStage.setScene(new Scene(root));
        usuarioStage.setResizable(false);
        usuarioStage.show();
    }

    public void verifyUser(Usuario usr) {

        if (usrTextField.getText() == usr.getNome()) {
            JOptionPane.showMessageDialog(null, "Voc?? n??o pode editar uma atividade que pertence a outro usu??rio");
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
            "APURA????O DE D??BITOS PREVIDENCI??RIOS(Contrib. Prev)",
            "APURA????O DE D??BITOS PREVIDENCI??RIOS(Benef. Prev)",
            "ATENDIMENTO DEMANDA OUTROS SETORES",
            "AUDITORIA"
    );

    ObservableList<String> assCorafi1 = FXCollections.observableArrayList(
            "Licen??a Sem Remunera????o LSR", "?? Disposi????o",
            "Mandato Eletivo", "Cartor??rio", "Outros"
    );

    ObservableList<String> assCorafi2 = FXCollections.observableArrayList(
            "Res??duos de Aposentadoria", "Res??duos de Pens??o",
            "Pens??es Irregulares", "Outros"
    );

    ObservableList<String> assCorafi3 = FXCollections.observableArrayList(
            "Averigua????o de d??bitos de contribui????o previdenci??ria",
            "Averigua????o de d??bitos de contribui????o previdenci??ria de instituidores de pens??o e pensionistas",
            "Suspender/cancelar/sobrestar cobran??a administrativa de contribui????o previdenci??ria por decis??o judicial",
            "Proceder Busca em Cart??rios por Bens de Notificados",
            "Proceder Busca de endere??os de Notificados",
            "Responder Dilig??ncias do TCE/SC",
            "Outros"
    );

    ObservableList<String> assCorafi4 = FXCollections.observableArrayList(
            "Auditoria em Benef??cios Previdenci??rios(Aposentadoria)",
            "Auditoria em Benef??cios Previdenci??rios(Pens??o)",
            "Auditoria em Contribui????es Previdenci??rias(Servidor)",
            "Auditoria em Contribui????es Previdenci??rias(Patronal)"
    );

    public void corafi() {
        atividadeComboBox.setItems(atvCoraf);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "APURA????O DE D??BITOS PREVIDENCI??RIOS(Contrib. Prev)"){
                assuntoComboBox.setItems(assCorafi1);
            } else if (newValue == "APURA????O DE D??BITOS PREVIDENCI??RIOS(Benef. Prev)") {
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
            "ANALISE DE COMP.(RO) DAS PENS??ES",
            "ANALISE DE COMP. (RI) SENDO O INSS SOLICITANTE",
            "CORRE????O DOS FORMUL??RIOS DE ANALISE(SIST. ANTIGO)",
            "DIGITALIZA????O DE DOC. F??SICOS AOS ANALISTAS E PERITOS DO COMPREV",
            "EMISS??O DE RELAT??RIOS",
            "REGISTRO DE AN??LISE DE DEMANDAS(SIST. RAFAEL)",
            "ANALISE DAS APOSENT. POR INVALIDEZ POR M??DICO PERITO NO SISTEMA COMPREV.",
            "ANALISE DE PENS??O FILHO MAIOR INV??LIDO POR M??DICO PERITO NO SISTEMA COMPREV"
    );

    ObservableList<String> assGecomp1 = FXCollections.observableArrayList(
            "Registro de an??lise e cadastro de demandas(IPREV--->RGPS/INSS)",
            "AConfec????o de documentos(DPT e Certid??es Espec??ficas)(IPREV--->RGPS/INSS)",
            "Diligencia para setorial(IPREV--->RGPS/INSS)",
            "Requerimento no COMPREV e envio das imagens de documentos(IPREV--->RGPS/INSS)",
            "Cadastro de DTC no COMPREV(IPREV--->RGPS/INSS)",
            "Solicita????o de processo ou documento ao setorial(IPREV--->RGPS/INSS)",
            "Encaminhamento de laudo m??dico das aposent. por invalidez aos peritos(IPREV--->RGPS/INSS)"
    );

    ObservableList<String> assGecomp2 = FXCollections.observableArrayList(
            "Registro de an??lise e cadastro de demandas(IPREV--->RPPS)",
            "Confec????o de documentos(DPT e Certid??es Espec??ficas)(IPREV--->RPPS)",
            "Diligencia para setorial(IPREV--->RPPS)",
            "Requerimento no COMPREV e envio das imagens de documentos(IPREV--->RPPS)",
            "Respostas de diligencias ao COMPREV(IPREV--->RPPS)",
            "Cadastro de DTC no COMPREV(IPREV--->RPPS)",
            "Solicita????o de processo ou documento ao setorial(IPREV--->RPPS)",
            "Encaminhamento de laudo m??dico das aposent. por invalidez aos peritos(IPREV--->RPPS)"
    );

    ObservableList<String> assGecomp3 = FXCollections.observableArrayList(
            "Requerimento comp.(RO) pens??o",
            "Envios de documentos comp.(RO) pens??o",
            "Acompanhamento e cadastro de DCB comp(RO) Pens??o",
            "Disponibiliza????o de laudos m??dicos filho maior invalido e dependentes"
    );

    ObservableList<String> assGecomp4 = FXCollections.observableArrayList(
            "Registro no COMPREV(Deferimento) ",
            "Registro no COMPREV(Indeferimento) ",
            "Registro no COMPREV(Exig??ncias)",
            "Registro no COMPREV(Suspens??o de Analise)",
            "Solicita????o de informa????es ao setorial do ex-servidor",
            "Solicita????o de ficha financeira a SEF"
    );

    ObservableList<String> assGecomp5 = FXCollections.observableArrayList(
            "Cont??beis", "Extra????o de ??bitos no SISOB", "Busca de inconsist??ncias no COMPREV",
            "Extra????o de novas exig??ncias", "Levantamento de prioridades(Processos antigos ou sobrestado)",
            "DOE (ESTADUAL/TJSC/ALESC/TCE)"
    );

    ObservableList<String> assGecomp6 = FXCollections.observableArrayList(
            "Sem assunto para esta atividade"
    );

    public void gecomp() {
        atividadeComboBox.setItems(atvGecomp);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "ANALISE DE COMP. (RO) IPREV--->RGPS/INSS"){
                assuntoComboBox.setItems(assGecomp1);
            } else if (newValue == "ANALISE DE COMP. (RO) IPREV SOLICITANTE RGPS/INSS") {
                assuntoComboBox.setItems(assGecomp2);
            } else if (newValue == "ANALISE DE COMP.(RO) DAS PENS??ES") {
                assuntoComboBox.setItems(assGecomp3);
            } else if (newValue == "ANALISE DE COMP. (RI) SENDO O INSS SOLICITANTE") {
                assuntoComboBox.setItems(assGecomp4);
            } else if (newValue == "CORRE????O DOS FORMUL??RIOS DE ANALISE(SIST. ANTIGO)") {
                assuntoComboBox.setItems(assGecomp5);
            } else if (newValue == "DIGITALIZA????O DE DOC. F??SICOS AOS ANALISTAS E PERITOS DO COMPREV") {
                assuntoComboBox.setItems(assGecomp5);
            } else if (newValue == "EMISS??O DE RELAT??RIOS") {
                assuntoComboBox.setItems(assGecomp5);
            } else if (newValue == "REGISTRO DE AN??LISE DE DEMANDAS(SIST. RAFAEL)") {
                assuntoComboBox.setItems(assGecomp6);
            } else if (newValue == "ANALISE DAS APOSENT. POR INVALIDEZ POR M??DICO PERITO NO SISTEMA COMPREV.") {
                assuntoComboBox.setItems(assGecomp6);
            } else if (newValue == "ANALISE DE PENS??O FILHO MAIOR INV??LIDO POR M??DICO PERITO NO SISTEMA COMPREV") {
                assuntoComboBox.setItems(assGecomp6);
            }
        });
    }

    ObservableList<String> atvGfpag = FXCollections.observableArrayList(
            "Confer??ncia final da folha de pagamento dos inativos",
            "Ressarcimento de remunera????o por ??bito de servidor inativo",
            "Afastar da folha de pagamento os servidores inativos que vieram a ??bito",
            "Receber e distribuir os processos com demandas judiciais e administrativas",
            "Elaborar relat??rio pens??o"
    );

    ObservableList<String> assGfpag1 = FXCollections.observableArrayList(
            "Gerar Resumo para  Compara????o entre os arquivos da Folha no SIGRH",
            "Gerar relat??rio de ??bitos", "Gerar relat??rio de aposentadoria do per??odo",
            "Conferir a folha do m??s anterior com a folha que est?? em desenvolvimento no m??s, gerando relat??rio espec??fico para cada rubrica",
            "Encaminhamento dos arquivos gerados da folha de pagamento, via SGPE, ao Banco do Brasil",
            "Verifica????o dos valores rejeitados por motivos de mudan??a de cadastro do servidor ou ??bito",
            "Acompanhar arquivos de folha suplementar se necess??rio",
            "Acompanhamento do cadastro e do recadastramento de servidores inativos",
            "Acompanhamento e levantamento de valores mensalmente bloqueados por falta de recadastramento",
            "Gerar o arquivo com relat??rio do banco mensal",
            "Gerar arquivos de pagamento por recadastramento, via arquivos banc??rios",
            "Busca de inativos que n??o se recadastraram no per??odo certo, para evitar o bloqueio e cancelamento do cadastro"
    );

    ObservableList<String> assGfpag2 = FXCollections.observableArrayList(
            "Analisar processos relativos ao aux??lio funeral",
            "Identificar os servidores falecidos que receberam valores indevidos",
            "Fazer o c??lculo dos valores pagos indevidamente em raz??o de ??bito de servidor inativo",
            "Encaminhar of??cio ao Banco do Brasil solicitando estorno de pagamento",
            "Solicitar c??pia da certid??o de ??bito junto aos cart??rios",
            "Providenciar abertura de processo"
    );

    ObservableList<String> assGfpag3 = FXCollections.observableArrayList(
            "Identificar e selecionar os servidores inativos, que vieram a ??bito, nos relat??rios enviados pelos cart??rios",
            "Acompanhar os pedidos de pens??o para identificar servidores aposentados que faleceram",
            "Encerrar (Afastar), no SIGRH, os servidores inativos que faleceram (Cart??rios e SISOB)"
    );

    ObservableList<String> assGfpag4 = FXCollections.observableArrayList(
            "Providenciar a solu????o para as demandas judiciais",
            "Providenciar a solu????o para as demandas administrativas",
            "Calcular o redutor da aposentadoria nos casos de ac??mulo com pens??o",
            "Lan??ar o redutor da aposentadoria no SIGRH",
            "Comunicar o servidor acerca do redutor de sua aposentadoria",
            "Comunicar a ger??ncia de compensa????o previdenci??ria as aposentadorias que incidiu o redutor"
    );


    ObservableList<String> assGfpag5 = FXCollections.observableArrayList(
            "Calcular os valores de contribui????o previdenci??ria e/ou de imposto de renda a serem restitu??dos a(o) pensionista",
            "Lan??ar no SIGRH os valores da contribui????o previdenci??ria e/ou do imposto de renda a serem restitu??dos a(o) pensionista"
    );

    ObservableList<String> assGfpag6 = FXCollections.observableArrayList(
            "Calcular o valor da pens??o relativa ao IPALESC",
            "Elaborar o relat??rio com o resumo da folha da pens??o paga pelos Poderes",
            "Gerar o Relat??rio 41",
            "Encaminhar email ?? Fazenda, ?? Controladoria e, ?? Diretoria de Previd??ncia, ?? Presid??ncia, ?? GEAFC disponibilizando os relat??rios"
    );



    public void gfpag() {
        atividadeComboBox.setItems(atvGfpag);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "Confer??ncia final da folha de pagamento dos inativos"){
                assuntoComboBox.setItems(assGfpag1);
            } else if (newValue == "Ressarcimento de remunera????o por ??bito de servidor inativo") {
                assuntoComboBox.setItems(assGfpag2);
            } else if (newValue == "Afastar da folha de pagamento os servidores inativos que vieram a ??bito") {
                assuntoComboBox.setItems(assGfpag3);
            } else if (newValue == "Receber e distribuir os processos com demandas judiciais e administrativas") {
                assuntoComboBox.setItems(assGfpag4);
            } else if (newValue == "Restituir valores de contribui????o previdenci??ria e de IRRF aprovados pela Per??cia pensionistas") {
                assuntoComboBox.setItems(assGfpag5);
            } else if (newValue == "Elaborar relat??rio pens??o") {
                assuntoComboBox.setItems(assGfpag6);
            }
        });


    }

    ObservableList<String> atvGetig = FXCollections.observableArrayList(
            "Suporte em software", "Suporte em hardware",
            "Cadastramento e manuten????o de usu??rios", "Desenvolvimento", "Servi??os administrativos",
            "Outros"
    );

    ObservableList<String> assGetig1 = FXCollections.observableArrayList(
            "Sistema operacional", "Pacote Office", "Sistema legado", "SGPE",
            "SIGRH", "SICOP", "SAP", "SOS - Chamados", "Navegadores", "Certificado Digital",
            "Planilhas", "Power BI", "Boa vista", "Arquivos acervo"
    );

    ObservableList<String> assGetig2 = FXCollections.observableArrayList(
            "Computadores", "Monitores", "Teclados", "Mouse", "Estabilizadores",
            "Cabeamento", "Switch", "Roteadores", "Telefones IP", "Projetores", "Som",
            "TV", "Cameras", "Catraca", "Ponto de rede", "Rede el??trica"
    );

    ObservableList<String> assGetig3 = FXCollections.observableArrayList(
            "E-Mail", "Rede Iprev", "Senhas de rede", "Pastas de rede", "Catraca",
            "CIASC ETERNAL", "CIASC(PROD)", "Ramais", "Outro sistema"
    );

    ObservableList<String> assGetig4 = FXCollections.observableArrayList(
            "Sistemas", "Bancos de dados", "Scripts", "API'S", "Ferramentas anal??ticas",
            "Query Boa Vista", "Processos de ETL", "Dashboards", "Migra????o entre sistemas",
            "Planilhas e Formul??rios"
    );

    ObservableList<String> assGetig5 = FXCollections.observableArrayList(
            "Certifica????o de notas fiscais", "Coordenar Dev. e aquisi????o de sistemas",
            "Levantamento de or??amento", "Elabora????o de contratos", "Participa????o em reuni??es"
    );

    ObservableList<String> assGetig6 = FXCollections.observableArrayList(
            "Recupera????o de arquivos Acervo"
    );

    public void getig() {
        atividadeComboBox.setItems(atvGetig);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "Suporte em software"){
                assuntoComboBox.setItems(assGetig1);
            } else if (newValue == "Suporte em hardware") {
                assuntoComboBox.setItems(assGetig2);
            } else if (newValue == "Cadastramento e manuten????o de usu??rios") {
                assuntoComboBox.setItems(assGetig3);
            } else if (newValue == "Desenvolvimento") {
                assuntoComboBox.setItems(assGetig4);
            } else if (newValue == "Servi??os administrativos") {
                assuntoComboBox.setItems(assGetig5);
            } else if (newValue == "Outros") {
                assuntoComboBox.setItems(assGetig6);
            }
        });
    }

    ObservableList<String> atvPres = FXCollections.observableArrayList(
            "An??lise de processo"
    );

    ObservableList<String> assPres = FXCollections.observableArrayList(
            "Pens??o", "Averba????o", "Retifica????o", "Aposentadoria", "Habitacional",
            "CTC", "Pareceres", "Portaria", "Levantamento de d??bito", "Valores pendentes",
            "Restitui????o de Contribui????o Previdenci??ria / Devolu????o de valores", "Encaminhamentos"
    );

    public void presidencia() {
        atividadeComboBox.setItems(atvPres);
        conclusaoComboBox.setItems(conclusao);

        atividadeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == "An??lise de processo"){
                assuntoComboBox.setItems(assPres);
            }
        });
    }

    ObservableList<String> tempo = FXCollections.observableArrayList(
            "10 minutos", "30 minutos", "1 hora", "3 horas", "5 horas", "7 horas", "Indefinido"
    );

    ObservableList<String> conclusao = FXCollections.observableArrayList(
            "Deferido", "Suspenso", "Diligenciado", "Parecer jur??dico", "Aguardando an??lise",
            "Aguardando compensa????o", "Compensado", "Criado", "Em an??lise", "Indeferido", "Inclu??do",
            "Alterado", "Bloqueado", "Encaminhado", "Emitido"
    );

    @FXML
    public void cadastrar() {

        if (!validarDados()) {
            exibirMensagem("Existem campos em branco, ou com dados inv??lidos, revise seus dados!");
            return;
        }

        if (!validadeDatas()){
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
        //movimentacao.setHoraInicio(horaInicioComboBox.getValue());
        movimentacao.setTempoAtividade(tempoComboBox.getValue());
        movimentacao.setConclusao(conclusaoComboBox.getValue());
        movimentacao.setOberva????o(observacaoTextField.getText());
        movimentacao.setUsuario(usrTextField.getText());

        if (editando == false){
            principalController.adicionarMovimentacao(movimentacao);
            movimentacao = null;
            cadastrarButton.getScene().getWindow().hide();
        } else if (editando == true){
            principalController.attAtividadesTable();
            movDAO.attMovimentacao(movimentacao);
            cadastrarButton.getScene().getWindow().hide();
        }

        System.out.println(movimentacao);

    }

    public boolean validadeDatas() {
        /*long diferenca = ChronoUnit.DAYS.between(dataInicioDatePicker.getValue(), dataFimDatePicker.getValue());*/

        /*System.out.println(diferenca);*/

        if (dataInicioDatePicker.getValue() != null && dataFimDatePicker.getValue() != null ) {

            long diferenca = ChronoUnit.DAYS.between(dataInicioDatePicker.getValue(), dataFimDatePicker.getValue());

            if (diferenca < 0){
                exibirMensagem("A data fim n??o pode ser menor que a data inicio, revise seus dados!");
                return false;
            }


        } else if (dataFimDatePicker.getValue() == null){
            return true;
        }

        return true;
    }

    public boolean validarDados() {

        if (atividadeComboBox.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }

        if (orgaoComboBox.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }

        if (localidadeComboBox.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }

        if (dataInicioDatePicker.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }

        if (dataInicioDatePicker.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }

        /*if (dataFimDatePicker.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }*/

        /*if (horaInicioComboBox.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }*/

        /*if (horaFimComboBox.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
            return false;
        }*/

        if (conclusaoComboBox.getValue() == null){
            //JOptionPane.showMessageDialog(null, "Data menor que o validado");
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
