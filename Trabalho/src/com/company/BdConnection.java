package com.company;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.sql.*;

/**
 * Responsável pela conexão com a base de dados e a manipulação do mesmo
 */
public class BdConnection {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    /**
     * Cria a conexão e realiza essa conexão com a base de dados do nosso trabalho
     */
    public BdConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl", "B9435846", "31051995"
            );

            stmt = con.createStatement();
        } catch (SQLRecoverableException e) {
            JOptionPane.showMessageDialog(null, "Verifique sua conexão com a internet");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Realiza uma query com o banco de dados
     * @param query - String que contém a query
     * @return - Retorna o ResultSet(Resultado do query) ou null se não houver dados
     */
    public ResultSet query(String query) {
        System.out.println("query: " + query);
        try {
            return stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("query ERROR: " + e);
            return null;
        }
    }

    /**
     * Realiza uma query de inserção no banco de dados
     * @param query - String que contém a query
     * @return - Retorna true se deu certo a inserção e false se o contrário
     */
    public boolean insertQuery(String query) {
        System.out.println("insertQuery: " + query);
        try {
            stmt.executeQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("insertQuery ERROR: " + e);
            trataErroUsuario(e.toString());
            return false;
        }
    }

    /**
     * Fecha a conexão com a base de dados
     */
    public void disconnectBD() {
        try {
            con.close();
            System.out.println("BD Disconnected");
        } catch (Exception e) {
            System.out.println("disconnectBD: " + e);
        }
    }

    /**
     * Pega a mensagem de erro e trata para que o usuário possa entender melhor
     * @param message - Mensagem que foi coletada com o erro
     */
    void trataErroUsuario(String message) {
        String userMessage;
        String tmp = message.substring(message.indexOf('(') + 10, message.indexOf(')'));

        if (message.contains("restrição exclusiva")) {
            String[] campos = tmp.split("_");
            userMessage = "Verifique os campos abaixo, a combinação desses campos não pode se repetir, ou seja, é necessário mudar algum destes:\n";
            for (int i = 2; i < campos.length; i++) {
                userMessage = userMessage + campos[i] + "\n";
            }

            JOptionPane.showMessageDialog(null, userMessage);
        } else if (message.contains("NULL")) {
            String[] campos = tmp.split("[.]");

            userMessage = "Os seguintes campos não podem estar em branco:\n";
            for (int i = 2; i < campos.length; i++) {
                userMessage = userMessage + campos[i] + "\n";
            }
            JOptionPane.showMessageDialog(null, userMessage);
        } else if (message.contains("restrição de integridade")) {
            String[] campos = tmp.split("_");

            userMessage = "Verifique os campos abaixo, alguns deles podem não estar cadastrados:\n";
            for (int i = 2; i < campos.length; i++) {
                userMessage = userMessage + campos[i] + "\n";
            }
            JOptionPane.showMessageDialog(null, userMessage);
        }
    }
}
