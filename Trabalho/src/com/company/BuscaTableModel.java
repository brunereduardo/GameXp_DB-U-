package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Preenche a tabela com uma lista de Busca, essa lista é coletada usando uma consulta na Base de dados
 */
public class BuscaTableModel extends AbstractTableModel {
    private String colunas[] = {"Tipo", "Horário", "Espaço", "Local"};
    private ArrayList<Busca> busca;

    private final int COLUNA_TIPO = 0;
    private final int COLUNA_HORARIO = 1;
    private final int COLUNA_ESPACO = 2;
    private final int COLUNA_LOCAL = 3;

    public BuscaTableModel(ArrayList<Busca> busca) {
        this.busca = busca;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return busca.size();
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
        Busca b = busca.get(i);

        switch (i1) {
            case COLUNA_TIPO:
                return b.getTipo();

            case COLUNA_HORARIO:
                return b.getHoras();

            case COLUNA_ESPACO:
                return b.getEspaco();

            case COLUNA_LOCAL:
                return b.getLocal();
        }
        return null;
    }
}
