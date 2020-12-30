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
import java.util.ArrayList;

public class Form3Busca extends JFrame{
    /**
     * Elementos de UI
     */
    private JButton btnVoltar;
    private JTable tableResult;
    private JButton btnBuscar;
    private JPanel panelForm3Busca;
    private JFormattedTextField formattedTextField1;

    /**
     * Cria a variavel que vai realizar a conexão com a Base de Dados
     * Cria a variável que vai receber o resultado da consulta a Base de Dados
     */
    private BdConnection bd;
    private ResultSet rs;

    public Form3Busca() throws HeadlessException {
        /**
         * Configurações iniciais dos elementos de UI
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("e-Schecule: Busca");
                setContentPane(panelForm3Busca);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                pack();
                setVisible(true);
            }
        });

        /**
         * Configura o JFormattedTextField para receber somente data do formato dd/MM/yyyy
         */
        DateFormat data = new SimpleDateFormat("dd/MM/yyyy");
        DateFormatter displayData = new DateFormatter(data);
        formattedTextField1.setFormatterFactory(new DefaultFormatterFactory(displayData));

        /**
         * Inicia a Conexão com a Base de Dados
         */
        bd = new BdConnection();



        /**
         * Ao clicar no botão voltar o usuário é redirecionado ao Form0Main, a base de dados é então desconectada.
         */
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bd.disconnectBD();
                new Form0Main().setVisible(true);
                dispose();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /**
                 * Configura e coleta os dados para a tabelaAtracao que contem os dados das atracoes
                 */
                tableResult.setModel(new BuscaTableModel(tableContent()));
            }
        });
    }

    /**
     * Realiza uma consulta no banco, essa consulta então é colocada em um vetor de busca e retornada
     *
     * @return - Uma lista de busca que será usada para preencher a tabela Busca, retorna null se não houver
     *           dados na Busca.
     */
    public ArrayList<Busca> tableContent() {
        try {
            ArrayList<Busca> table = new ArrayList<>();
            Busca busca;

            rs = bd.query("SELECT DISTINCT A.TIPO, H.HORAS, H.ESPACO, H.LOCAL " +
                    "FROM AGENDA S JOIN ACONTECE H " +
                    "ON((S.DATA = '" + formattedTextField1.getText() + "') AND (S.DATA = H.DATA)) " +
                    "JOIN ATRACAO A " +
                    "ON(H.ID = A.ID) " +
                    "ORDER BY H.HORAS");

            while (rs.next()) {
                busca = new Busca(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                table.add(busca);
            }
            return table;
        } catch (Exception e) {
            System.out.println("tableContent: " + e);
            return null;
        }
    }
}
