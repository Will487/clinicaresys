package br.com.curso.dao;

import br.com.curso.model.Agendamento;
import br.com.curso.model.Paciente;
import br.com.curso.model.Profsaude;
import br.com.curso.model.Tipo;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgendamentoDAO implements GenericDAO {
    
    private Connection conexao;
    
    public AgendamentoDAO() throws Exception{
        conexao = SingleConnection.getConnection();
    }
    
    @Override
    public Boolean cadastrar(Object objeto) {
        Agendamento oAgendamento = (Agendamento) objeto;
        boolean retorno = false;
        if (oAgendamento.getIdAgendamento()== 0) {
            retorno = inserir(oAgendamento);
        } else {
            retorno = alterar(oAgendamento);
        }
        return retorno;
    }
      

@Override
    public Boolean inserir(Object objeto) {
        
        Agendamento oAgendamento = (Agendamento) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into agendamento (idpaciente, idprofissional, idtipo, dataagendamento, horario,"
                +" descricao, observacao, valor) values (?,?,?,?,?,?,?,?)";
        try {
             
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, oAgendamento.getPaciente().getIdPaciente());
            stmt.setInt(2, oAgendamento.getProfissional().getIdProfsaude());
            stmt.setInt(3, oAgendamento.getPagamento().getIdtipo());
            stmt.setDate(4, new java.sql.Date(oAgendamento.getDataAgendamento().getTime()));
            stmt.setTime(5, oAgendamento.getHorario());
            stmt.setString(6, oAgendamento.getDescricao());
            stmt.setString(7, oAgendamento.getObservacao());
            stmt.setDouble(8, oAgendamento.getValor());
            stmt.execute();
            conexao.commit();
            
            return true;
        } catch (Exception e) {
            try {
                System.out.println("Problemas ao cadastrar Agendamento ! Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback();
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback! " + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Agendamento oAgendamento = (Agendamento) objeto;
        PreparedStatement stmt = null;
        String sql = "update agendamento set dataagendamento=?, horario=?, descricao=?, observacao=? valor=? where idagendamento=?";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setDate(0, new java.sql.Date(oAgendamento.getDataAgendamento().getTime()));
            stmt.setTime(1, oAgendamento.getHorario());
            stmt.setString(2, oAgendamento.getDescricao());
            stmt.setString(3, oAgendamento.getObservacao());
            stmt.setDouble(4, oAgendamento.getValor());
            stmt.setInt(5, oAgendamento.getIdAgendamento());
            stmt.execute();
            conexao.commit();
            return true;
        }catch(Exception e){
            try {
                System.out.println("Problemas ao alterar Agendamento! Erro: " + e.getMessage());
                e.printStackTrace();
                conexao.rollback(); 
            } catch (SQLException ex) {
                System.out.println("Problemas ao executar rollback" + ex.getMessage());
                ex.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public Boolean excluir(int numero) {
        int idAgendamento = numero;
        PreparedStatement stmt= null;
        String sql = "delete from agendamento where idagendamento=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idAgendamento);
            stmt.execute();
            conexao.commit();
            
            return true;
        } catch (Exception ex) {
            System.out.println("Problema ao excluir o Agendamento! Erro: " + ex.getMessage());
            try {
                conexao.rollback();
            } catch (SQLException e) {
                System.out.println("Erro rolback "+e.getMessage());
                e.printStackTrace();
            }
            return false;
        } 
    }

    @Override
    public Object carregar(int numero) {
        int idAgendamento = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Agendamento oAgendamento = null;
        String sql= "select * from agendamento where idagendamento=?";
        
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idAgendamento);
            rs=stmt.executeQuery();
            while (rs.next()) {
                oAgendamento = new Agendamento();
                oAgendamento.setIdAgendamento(rs.getInt("idagendamento"));
                
                try {
                    PacienteDAO oPacienteDAO = new PacienteDAO();
                    oAgendamento.setPaciente((Paciente) oPacienteDAO.carregar(rs.getInt("idpaciente")));

                    ProfsaudeDAO oProfsaudeDAO = new ProfsaudeDAO();
                    oAgendamento.setProfissional((Profsaude) oProfsaudeDAO.carregar(rs.getInt("idprofissional"))); 

                    TipoDAO oTipoDAO = new TipoDAO();
                    oAgendamento.setPagamento((Tipo) oTipoDAO.carregar(rs.getInt("idtipo")));
                    
                } catch (Exception e) {
                    System.out.println("Erro no agendamento! Erro: " + e.getMessage());
                    e.printStackTrace();
                }
                
                oAgendamento.setDataAgendamento(rs.getDate("dataagendamento"));
                oAgendamento.setHorario(rs.getTime("horario"));
                oAgendamento.setDescricao(rs.getString("descricao"));
                oAgendamento.setObservacao(rs.getString("observacao"));
                oAgendamento.setValor(rs.getDouble("valor"));
            }
            return oAgendamento;
        } catch (Exception ex) {
            System.out.println("Problema ao carregar Agendamento! Erro:"+ex.getMessage());
            return false;
        }
    }

@Override
public List<Object> listar() {
    List<Object> resultado = new ArrayList<>();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String sql = "Select * from agendamento order by dataagendamento";

    try {
        stmt = conexao.prepareStatement(sql);
        rs=stmt.executeQuery();
        while (rs.next()){
            Agendamento oAgendamento = new Agendamento();
            PacienteDAO oPacienteDAO = null;
            TipoDAO oTipoDAO = null;
            ProfsaudeDAO oProfsaudeDAO = null;

            try{
                oPacienteDAO = new PacienteDAO();
                oProfsaudeDAO = new ProfsaudeDAO();
                oTipoDAO = new TipoDAO();

            } catch (Exception e){
                System.out.println("Erro na listagem de agendamento! Erro: " + e.getMessage());
                e.printStackTrace();
            }
            oAgendamento.setIdAgendamento(rs.getInt("idagendamento"));
            oAgendamento.setPaciente((Paciente) oPacienteDAO.carregar(rs.getInt("idpaciente")));
            oAgendamento.setProfissional((Profsaude) oProfsaudeDAO.carregar(rs.getInt("idprofissional")));
            oAgendamento.setPagamento((Tipo) oTipoDAO.carregar(rs.getInt("idtipo")));
            oAgendamento.setDataAgendamento(rs.getDate("dataagendamento"));
            oAgendamento.setHorario(rs.getTime("horario"));
            oAgendamento.setDescricao(rs.getString("descricao"));
            oAgendamento.setObservacao(rs.getString("observacao"));
            oAgendamento.setValor(rs.getDouble("valor"));

            resultado.add(oAgendamento);
        }
    } catch (SQLException ex){
        System.out.println("Problemas ao listar Agendamento! Erro:" +ex.getMessage());
    } catch (ParseException ex) {
        Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return resultado; 
}
    
}
