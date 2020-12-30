package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form2AddExterna extends JFrame{
    /**
     * Elementos da UI
     */
    private JPanel panelAddExterna;
    private JTextField txtNome;
    private JButton btnCancelar;
    private JButton btnProximo;

    /**
     * Cria a variavel que vai realizar a conexão com a Base de Dados
     */
    private BdConnection bd;

    /**
     * ID da atração que foi obtido anteriormente, na consulta feita no Form0Main.java
     */
    private int id;

    public Form2AddExterna(int id) throws HeadlessException {
        this.id = id;

        /**
         * Configurações iniciais dos elementos de UI
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTitle("e-Schecule: Externa");
                setContentPane(panelAddExterna);
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
                if (!bd.insertQuery("INSERT INTO EXTERNA VALUES(" + id + ", " + "'" + txtNome.getText() + "')"))
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
