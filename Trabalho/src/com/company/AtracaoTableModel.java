package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


/**
 * Preenche a tabela com uma lista de Atracao, essa lista é coletada usando uma consulta na Base de dados
 */
public class AtracaoTableModel extends AbstractTableModel {
    private String colunas[] = {"ID", "Tipo", "Data", "Horário", "Espaço", "Local"};
    private ArrayList<Atracao> atracoes;

    private final int COLUNA_ID = 0;
    private final int COLUNA_TIPO = 1;
    private final int COLUNA_DATA = 2;
    private final int COLUNA_HORARIO = 3;
    private final int COLUNA_ESPACO = 4;
    private final int COLUNA_LOCAL = 5;

    public AtracaoTableModel(ArrayList<Atracao> atracoes) {
        this.atracoes = atracoes;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return atracoes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Atracao atracao = atracoes.get(i);

        switch (i1) {
            case COLUNA_ID:
                return atracao.getId();

            case COLUNA_TIPO:
                return atracao.getTipo();

            case COLUNA_DATA:
                return atracao.getData();

            case COLUNA_HORARIO:
                return atracao.getHoras();

            case COLUNA_ESPACO:
                return atracao.getEspaco();

            case COLUNA_LOCAL:
                return atracao.getLocal();
        }
        return null;
    }
}
