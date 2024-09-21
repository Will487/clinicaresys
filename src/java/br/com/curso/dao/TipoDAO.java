package br.com.curso.dao;

import br.com.curso.model.Tipo;
import br.com.curso.utils.SingleConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoDAO implements GenericDAO {
    
      private Connection conexao;

    public TipoDAO() throws Exception {
        conexao = SingleConnection.getConnection();

    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Tipo oTipo = (Tipo) objeto;
        boolean retorno = false;
        if (oTipo.getIdtipo()== 0) {
            retorno = inserir(oTipo);
        } else {
            retorno = alterar(oTipo);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        
        Tipo oTipo = (Tipo) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into tipodepagamento (descricao, valordesconto, valortaxa) values (?,?,?)";

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oTipo.getDescricao());
            stmt.setDouble(2, oTipo.getValordesconto());
            stmt.setDouble(3, oTipo.getValortaxa());
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception e) {
            try {
                System.out.println("Problemas ao cadastrar Pagamento ! Erro: " + e.getMessage());
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
        Tipo oTipo = (Tipo) objeto;
        PreparedStatement stmt = null;
        String sql = "update tipodepagamento set descricao=?, valordesconto=?, valortaxa=? where idtipo=?";

        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oTipo.getDescricao());
            stmt.setDouble(2, oTipo.getValordesconto());
            stmt.setDouble(3, oTipo.getValortaxa()); 
            stmt.setInt(4, oTipo.getIdtipo());
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception e) {
            try {
                System.out.println("Problemas ao alterar Pagamento ! Erro: " + e.getMessage());
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
    public Boolean excluir(int numero) {
        
        int idtipo = numero;
        PreparedStatement stmt = null;
        String sql = "delete from tipodepagamento where idtipo=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idtipo);
            stmt.execute();
            conexao.commit();
            return true;
        } catch (Exception e) {
            try {
                System.out.println("Problemas ao excluir Pagamento! Erro: " + e.getMessage());
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
    public Object carregar(int numero) {
        int idtipo = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Tipo oTipo = null;
        String sql = "select * from tipodepagamento where idtipo=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idtipo);
            rs = stmt.executeQuery();
            while (rs.next()) {
                oTipo = new Tipo();
                oTipo.setIdtipo(rs.getInt("idtipo"));
                oTipo.setDescricao(rs.getString("descricao"));
                oTipo.setValordesconto(rs.getDouble("valordesconto"));
                oTipo.setValortaxa(rs.getDouble("valortaxa"));
            }
        } catch (Exception e) {
            System.out.println("Problemas ao carregar Tipo! Erro " + e.getMessage());
            e.printStackTrace();
        }
        return oTipo;
    }

    @Override
    public List<Object> listar() {
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Object> resultado = new ArrayList<>();
        Tipo oTipo = null;
        String sql = "select * from tipodepagamento";
        try {
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                oTipo = new Tipo();
                oTipo.setDescricao(rs.getString("descricao"));
                oTipo.setIdtipo(rs.getInt("idtipo"));
                oTipo.setValordesconto(rs.getDouble("valordesconto"));
                oTipo.setValortaxa(rs.getDouble("valortaxa"));
                resultado.add(oTipo);
            }
        } catch (Exception e) {
            System.out.println("Problemas ao Listar Pagamento! Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
    
}
