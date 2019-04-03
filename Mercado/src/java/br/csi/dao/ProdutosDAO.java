package br.csi.dao;

import br.csi.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutosDAO {

    public static void main(String args[])
    {
        Produtos p = new ProdutosDAO().read(2);
        
        System.out.println("Nome: "+p.getNome());
        System.out.println("Preco: "+p.getPreco());
        System.out.println("Unidade: "+p.getUnidade());
    }
    
    public int create (String nome, float preco, int unidade)
    {
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "INSERT INTO produtos (nome, preco, unidade) values (?, ?, ?)";
            PreparedStatement pre = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pre.setString(1, nome);
            pre.setFloat(2, preco);
            pre.setInt(3, unidade);
            pre.execute();
            ResultSet rs = pre.getGeneratedKeys();
            rs.next();
            if(rs.getInt(1) > 0)
            {
                return rs.getInt(1);
            }
            return 0;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean create(Produtos produto)
    {
        //conexao com o banco.
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "insert into produtos (nome, preco, unidade) values (?, ?, ?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, produto.getNome());
            pre.setFloat(2, produto.getPreco());
            pre.setInt(3, produto.getUnidade());
  
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
    public Produtos read(int id)
    {
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "select * from produtos where produtos.id_produtos = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            
            ResultSet rs = pre.executeQuery();
            
            while(rs.next())
            {
                Produtos p = new Produtos();
                p.setId_produto(id);
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getFloat("preco"));
                p.setUnidade(rs.getInt("unidade"));
                return p;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean update(Produtos produto)
    {
        try(Connection conn = new ConectaDB_Postgres().getConexao())
        {
            String sql = "UPDATE produtos SET nome = ?, preco = ?, unidade = ? WHERE produtos.id_produtos = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, produto.getNome());
            pre.setFloat(2, produto.getPreco());
            pre.setInt(3, produto.getUnidade());
            
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
            String sql = "DELETE FROM produtos WHERE id_produtos = ?";
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
