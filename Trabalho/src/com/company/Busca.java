package com.company;

/**
 * Classe Busca que vai ser usada para a tabela que se encontra no Form3Busca
 */
public class Busca {
    private String tipo;
    private String horas;
    private String espaco;
    private String local;

    public Busca(String tipo, String horas, String espaco, String local) {
        this.tipo = tipo;
        this.horas = horas;
        this.espaco = espaco;
        this.local = local;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getEspaco() {
        return espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
