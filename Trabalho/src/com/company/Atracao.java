package com.company;

/**
 * Classe atração que vai ser usada para a tabela que se encontra no Form0Main
 */
public class Atracao {
    private String id;
    private String tipo;
    private String data;
    private String horas;
    private String espaco;
    private String local;

    public Atracao(String id, String tipo, String data, String horas, String espaco, String local) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.horas = horas;
        this.espaco = espaco;
        this.local = local;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
