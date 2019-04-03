package br.csi.dao;

import br.csi.model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {
    
    public Funcionario read(int id)
    {
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "select * from funcionario where funcionario.id_funcionario = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            
            ResultSet rs = pre.executeQuery();
            
            while(rs.next())
            {
                Funcionario f = new Funcionario();
                f.setId_funcionario(id);
                f.setNome(rs.getString("nome"));
                f.setSenha(rs.getString("senha"));
                return f;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean update(Funcionario funcionario)
    {
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "UPDATE funcionario SET nome = ?, senha = ? WHERE funcionario.id_funcionario = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, funcionario.getNome());
            pre.setString(2, funcionario.getSenha());
            
            if(pre.executeUpdate() > 0)
            {
                return true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(int id)
    {
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            if(pre.executeUpdate() > 0)
            {
                return true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
