package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form2AddPartida extends JFrame {
    /**
     * Elementos da UI
     */
    private JPanel panelAddPartida;
    private JTextField txtCategoria;
    private JTextField txtNomeJogo;
    private JTextField txtEtapa;
    private JTextField txtEquipe1;
    private JTextField txtEquipe2;
    private JButton btnCancelar;
    private JButton btnProximo;

    /**
     * ID da atração que foi obtido anteriormente, na consulta feita no Form0Main.java
     */
    private int id;

    /**
     * Cria a variavel que vai realizar a conexão com a Base de Dados
     */
    private BdConnection bd;

    public Form2AddPartida(int id) throws HeadlessException {
        this.id = id;

        /**
         * Configurações iniciais dos elementos de UI
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("e-Schecule: Partida");
                setContentPane(panelAddPartida);
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
         * Adiciona os dados que estão contidos nos JTextField para a Base de Dados na tabela referente ao Form
         * Desconecta da Base de dados e vai para o Form0Main
         */
        btnProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s = "INSERT INTO PARTIDA VALUES(" + id +
                        ", " + "'" + txtCategoria.getText() + "'" +
                        ", " + "'" + txtNomeJogo.getText() + "'" +
                        ", " + "'" + txtEtapa.getText() + "'" +
                        ", " + "'" + txtEquipe1.getText() + "'" +
                        ", " + "'" + txtEquipe2.getText() + "')";

                if (!bd.insertQuery(s))
                    return;
                bd.disconnectBD();
                new Form0Main().setVisible(true);
                dispose();
            }
        });

        /**
         * Ao clicar no botão cancelar o usuário é redirecionado ao Form0Main, a base de dados é então desconectada.
         */
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bd.query("DELETE FROM ATRACAO WHERE ID = " + id);
                bd.disconnectBD();
                new Form0Main().setVisible(true);
                dispose();
            }
        });
    }
}
