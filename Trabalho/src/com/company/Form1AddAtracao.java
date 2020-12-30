package com.company;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Form1AddAtracao extends JFrame{
    /**
     * Constantes que são usadas no programa
     */
    private static final String opAtracao[] = {"Partida", "Concurso Cosplay", "Palestra", "Show", "ArtStreet", "Externa"};
    private static final int OPCAO_PARTIDA = 0;
    private static final int OPCAO_COSPLAY = 1;
    private static final int OPCAO_PALESTRA = 2;
    private static final int OPCAO_SHOW = 3;
    private static final int OPCAO_ARTSTREET = 4;
    private static final int OPCAO_EXTERNA = 5;

    /**
     * Elementos da UI
     */
    private JPanel panelAddAtracao;
    private JComboBox cbLocal;
    private JComboBox cbTipo;
    private JButton btnProximo;
    private JButton btnCancelar;
    private JFormattedTextField ftxtData;
    private JFormattedTextField ftxtHorario;

    private ArrayList<String> locais = new ArrayList<>();

    /**
     * Cria a variavel que vai realizar a conexão com a Base de Dados
     */
    private BdConnection bd;

    /**
     * ID da atração que foi obtido anteriormente, na consulta feita no Form0Main.java
     */
    private int id;

    public Form1AddAtracao(int id) throws HeadlessException {
        this.id = id;


        /**
         * Configurações iniciais dos elementos de UI
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("e-Schecule: Adicionar Atração");
                setContentPane(panelAddAtracao);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                pack();
                setVisible(true);
            }
        });

        /**
         * Inicia a Conexão com a Base de Dados
         */
        bd = new BdConnection();

        /**
         * Demais configurações da UI
         */
        setDateFormat();
        comboBoxConfig();

        /**
         * Ao clicar no Botão Próximo, o próximo Form vai ser o referente ao que está selecionado
         * no comboBox cbTipo que refere-se ao Tipo de Atração.
         * É inserido a atração na base de dados, e se a inserção ocorreu corretamente o próximo Form é chamado
         */
        btnProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int opcao = cbTipo.getSelectedIndex();

                switch (opcao) {
                    case OPCAO_PARTIDA:
                        bd.query("INSERT INTO ATRACAO VALUES (" + id + ", 'PARTIDA')");
                        if(!addToAconteceTable(cbLocal.getSelectedIndex()))
                            return;
                        bd.disconnectBD();
                        new Form2AddPartida(id).setVisible(true);
                        dispose();
                        break;

                    case OPCAO_COSPLAY:
                        bd.query("INSERT INTO ATRACAO VALUES (" + id + ", 'CONCURSOCOSPLAY')");
                        if(!addToAconteceTable(cbLocal.getSelectedIndex()))
                            return;
                        bd.disconnectBD();
                        new Form2AddCosplay(id).setVisible(true);
                        dispose();
                        break;

                    case OPCAO_PALESTRA:
                        bd.query("INSERT INTO ATRACAO VALUES (" + id + ", 'PALESTRA')");
                        if(!addToAconteceTable(cbLocal.getSelectedIndex()))
                            return;
                        bd.disconnectBD();
                        new Form2AddPalestra(id).setVisible(true);
                        dispose();
                        break;

                    case OPCAO_SHOW:
                        bd.query("INSERT INTO ATRACAO VALUES (" + id + ", 'SHOW')");
                        if(!addToAconteceTable(cbLocal.getSelectedIndex()))
                            return;
                        bd.disconnectBD();
                        new Form2AddShow(id).setVisible(true);
                        dispose();
                        break;

                    case OPCAO_ARTSTREET:
                        bd.query("INSERT INTO ATRACAO VALUES (" + id + ", 'ARTSTREET')");
                        if(!addToAconteceTable(cbLocal.getSelectedIndex()))
                            return;
                        bd.disconnectBD();
                        new Form2AddArt(id).setVisible(true);
                        dispose();
                        break;

                    case OPCAO_EXTERNA:
                        bd.query("INSERT INTO ATRACAO VALUES (" + id + ", 'EXTERNA')");
                        if(!addToAconteceTable(cbLocal.getSelectedIndex()))
                            return;
                        bd.disconnectBD();
                        new Form2AddExterna(id).setVisible(true);
                        dispose();
                        break;
                }
            }
        });

        /**
         * Ao clicar no botão cancelar o usuário é redirecionado ao Form0Main, a base de dados é então desconectada.
         */
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bd.disconnectBD();
                new Form0Main().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Adiciona os itens que vão estar disponíveis nos dois comboboxes
     *
     * cbTipo - contém os tipos de atração que estão disponiveis no evento, usa os valores da constante opAtração
     *
     * cbLocal - contém todos os locais que estão cadastrados na base de dados, é feito uma consulta ao banco e
     *           a resposta da consulta é adicionada como item no combobox
     */
    public void comboBoxConfig() {
        try {
            ResultSet rs = bd.query("SELECT * FROM LOCAL");

            //Pega os dados do Result Set e adiciona-os a uma lista
            while (rs.next()) {
                String tmp = rs.getString(1) + ", " + rs.getString(2);
                locais.add(tmp);
                cbLocal.addItem(tmp);
            }

            for (int i = 0; i < opAtracao.length; i++) {
                cbTipo.addItem(opAtracao[i]);
            }
        } catch (Exception e) {
            System.out.println("comboBoxConfig: " + e);
        }
    }

    /**
     * Configura os dois JFormattedTextField para aceitar somente data e horário
     */
    public void setDateFormat () {
        DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter displayData = new DateFormatter(data);

        DateFormat hora = new SimpleDateFormat("HH:mm");
        DateFormatter displayHora = new DateFormatter(hora);

        ftxtData.setFormatterFactory(new DefaultFormatterFactory(displayData));
        ftxtHorario.setFormatterFactory(new DefaultFormatterFactory(displayHora));
    }

    /**
     * Adiciona a atração nas tabelas Agenda e Acontece
     * @param index
     * @return
     */
    public boolean addToAconteceTable(int index) {
        String[] tmp = locais.get(index).split(",");

        bd.query("INSERT INTO AGENDA VALUES ('" + ftxtData.getText() + "', '" + ftxtHorario.getText() + "')");

        if(!bd.insertQuery("INSERT INTO ACONTECE VALUES (" + id +
                ", '" + ftxtData.getText() +
                "', '" + ftxtHorario.getText() +
                "', '" + tmp[0].trim() +
                "', '" + tmp[1].trim() + "')"))
            return false;

        return true;
    }
}
