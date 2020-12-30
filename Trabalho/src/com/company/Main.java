package com.company;

import javax.swing.*;

public class Main {

    /**
     * Define o padrão do LookAndFeel(Aparência do programa) como sendo igual a do Sistema Operacional
     * Chama o FormMain.
     * As execeções abaixo são necessárias para que o UIManager consiga coletar o LookAndFeel do Sistema Operacional
     * @param args
     * @throws ClassNotFoundException
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    new Form0Main().setVisible(true);
    }
}
