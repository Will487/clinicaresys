package br.com.curso.model;

import br.com.curso.utils.Conversao;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.Date;

public class Agendamento {
    
    private int idAgendamento;
    private Paciente paciente;
    private Profsaude profissional;
    private Tipo pagamento;
    private Date dataAgendamento;
    private Time horario;
    private String descricao;
    private String observacao;
    private double valor;

    public Agendamento() throws ParseException {
        this.idAgendamento = 0;
        this.dataAgendamento = Conversao.dataAtual();
        this.horario = Conversao.horaAtual(LocalTime.now());
        this.descricao = "";
        this.observacao = "";
        this.valor = 0;
        this.pagamento = new Tipo();
        this.paciente = Paciente.pacienteVazio();
        this.profissional = Profsaude.profsaudeVazio();
    }

    public Agendamento(int idAgendamento, Paciente paciente, Profsaude profissional, Tipo pagamento, Date dataAgendamento, Time horario, String descricao, String observacao, double valor) {
        this.idAgendamento = idAgendamento;
        this.paciente = paciente;
        this.profissional = profissional;
        this.pagamento = pagamento;
        this.dataAgendamento = dataAgendamento;
        this.horario = horario;
        this.descricao = descricao;
        this.observacao = observacao;
        this.valor = valor;
    }
    
    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Profsaude getProfissional() {
        return profissional;
    }

    public void setProfissional(Profsaude profissional) {
        this.profissional = profissional;
    }

    public Tipo getPagamento() {
        return pagamento;
    }

    public void setPagamento(Tipo pagamento) {
        this.pagamento = pagamento;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    } 
}
