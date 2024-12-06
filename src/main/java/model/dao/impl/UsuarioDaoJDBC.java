package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;

import java.sql.*;
import java.util.List;

public class UsuarioDaoJDBC implements UsuarioDao {
    private Connection conn;

    public UsuarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Usuario obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO usuario (CpfUsuario, Nome, Email, Telefone, DataNasc, Endereco)" +
                    "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getCpf());
            ps.setString(2, obj.getNome());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getTelefone());
            ps.setDate(5, Date.valueOf(obj.getDataNasc()));
            ps.setString(6, obj.getEndereco());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    String cpf = rs.getString(1);
                    System.out.println(cpf);
                    DB.closeResultSet(rs);
                }
            } else {
                throw new DbException("Erro ao inserir usuario");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Usuario obj) {

    }

    @Override
    public void deleteByCpf(String cpf) {

    }

    @Override
    public Usuario findByCpf(String cpf) {
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        return List.of();
    }
}
