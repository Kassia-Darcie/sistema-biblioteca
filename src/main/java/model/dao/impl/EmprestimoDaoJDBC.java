/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.dao.EmprestimoDao;
import model.entities.Emprestimo;
import model.entities.Livro;

/**
 *
 * @author kassi
 */
public class EmprestimoDaoJDBC implements EmprestimoDao {
    private final Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public EmprestimoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Emprestimo obj) {
        try {
            String sql = "INSERT INTO `biblioteca`.`emprestimo` " +
            "(`DataEmp`, " +
            "`Extensoes`, " +
            "`Multa`, " +
            "`CodLivro`, " +
            "`CpfUsuario`) " +
            "VALUES " +
            "(?, ?, ?, ?, ?)";
            
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, Date.valueOf(obj.getDataEmp()));
            ps.setInt(2, obj.getExtensoes());
            ps.setDouble(3, obj.getMulta());
            ps.setInt(4, obj.getLivro().getCodLivro());
            ps.setString(5, obj.getCpfUsuario());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idLivro = rs.getInt(1);
                    obj.setId(idLivro);
                    System.out.println(obj);
                    DB.closeResultSet(rs);
                }
            }
             
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void extender(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Emprestimo> listarEmprestimos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deletarEmprestimo(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public static Emprestimo instantiateEmprestimo(ResultSet rs, Livro livro) throws SQLException {
        Emprestimo emp = new Emprestimo();
        emp.setId(rs.getInt("CodEmp"));
        emp.setDataDev(rs.getDate("DataDev").toLocalDate());
        emp.setDataEmp(rs.getDate("DataEmp").toLocalDate());
        emp.setExtensoes(rs.getInt("Extensoes"));
        emp.setMulta(rs.getDouble("Multa"));
        emp.setLivro(livro);
        emp.setCpfUsuario(rs.getString("CpfUsuario"));
        return emp;
    }
    
}
