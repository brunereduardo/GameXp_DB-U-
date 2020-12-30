package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Form0Main extends JFrame {
    /**
     * Elementos da UI
     */
    private JPanel panelMain;
    private JButton btnBuscar;
    private JTable tableAtracao;
    private JButton btnAddAtracao;

    /**
     * Cria a variavel que vai realizar a conexão com a Base de Dados
     * Cria a variável que vai receber o resultado da consulta a Base de Dados
     */
    private BdConnection bd;
    private ResultSet rs;

    /**
     * lastId = Refere-se ao último ID encontrado na consulta
     */
    private int lastId = 0;

    public Form0Main() throws HeadlessException {
        /**
         * Configurações iniciais dos elementos de UI
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("e-Schecule");
                setContentPane(panelMain);
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
         * Configura e coleta os dados para a tabelaAtracao que contem os dados das atracoes
         */
        tableAtracao.setModel(new AtracaoTableModel(tableContent()));

        /**
         * Quando o botão Adicionar Atração é clicado, a Base de Dados é desconectada e o Form1AddAtracao é chamado,
         * é passada para o Form1AddAtracao o ID que é para ser usado ao adicionar a atração
         */
        btnAddAtracao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bd.disconnectBD();
                new Form1AddAtracao(lastId + 1).setVisible(true);
                //new Form3AddFuncionarios().setVisible(true);
                dispose();
            }
        });

        /**
         * Ao clicar o botão Buscar é redirecionado ao Form3Busca, a Base de dados é desconectada
         */
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bd.disconnectBD();
                new Form3Busca().setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Realiza uma consulta no banco, essa consulta então é colocada em um vetor de atracoes e retornada
     *
     * @return - Uma lista de atracoes que será usada para preencher a tabela Atracao, retorna null se não houver
     *           dados de Atrações.
     */
    public ArrayList<Atracao> tableContent() {
        try {
            ArrayList<Atracao> table = new ArrayList<>();
            Atracao atracao;

            rs = bd.query("SELECT ATRACAO.ID, ATRACAO.TIPO, ACONTECE.DATA, ACONTECE.HORAS, ACONTECE.ESPACO, ACONTECE.LOCAL FROM ATRACAO " +
                          "LEFT JOIN ACONTECE ON ATRACAO.ID = ACONTECE.ID " +
                          "ORDER BY ID");

            while (rs.next()) {
                lastId = rs.getInt(1);
                atracao = new Atracao(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                table.add(atracao);
            }
            return table;
        } catch (Exception e) {
            System.out.println("tableContent: " + e);
            return null;
        }
    }
}
