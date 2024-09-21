package br.com.curso.model;

import br.com.curso.utils.Conversao;
import java.util.Date;

public class Tipo {
    private int idtipo;
    private String descricao;
    private double valordesconto;
    private double valortaxa;

    public Tipo() {
        
        this.idtipo = 0;
        this.descricao = "";
        this.valordesconto = 0.0;
        this.valortaxa = 0.0;
    }

    public Tipo(int idtipo, String descricao, double valordesconto, double valortaxa) {
        this.idtipo = idtipo;
        this.descricao = descricao;
        this.valordesconto = valordesconto;
        this.valortaxa = valortaxa;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValordesconto() {
        return valordesconto;
    }

    public void setValordesconto(double valordesconto) {
        this.valordesconto = valordesconto;
    }

    public double getValortaxa() {
        return valortaxa;
    }

    public void setValortaxa(double valortaxa) {
        this.valortaxa = valortaxa;
    }
    
    
}
