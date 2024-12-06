package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDaoJDBC implements UsuarioDao {
    private final Connection conn;

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
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void update(Usuario obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE usuario " +
                    "SET Nome = ?, Email = ?, Telefone = ?, DataNasc = ?, Endereco = ? " +
                    "WHERE CpfUsuario = ?");

            ps.setString(1, obj.getNome());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getTelefone());
            ps.setDate(4, Date.valueOf(obj.getDataNasc()));
            ps.setString(5, obj.getEndereco());
            ps.setString(6, obj.getCpf());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void deleteByCpf(String cpf) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM usuario WHERE CpfUsuario = ?");
            ps.setString(1, cpf);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public Usuario findByCpf(String cpf) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM usuario WHERE usuario.CpfUsuario = ?");
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiateUsuario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    private static Usuario instantiateUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setCpf(rs.getString("CpfUsuario"));
        usuario.setNome(rs.getString("Nome"));
        usuario.setEmail(rs.getString("Email"));
        usuario.setTelefone(rs.getString("Telefone"));
        usuario.setDataNasc(rs.getDate("DataNasc").toLocalDate());
        usuario.setEndereco(rs.getString("Endereco"));
        return usuario;
    }

    @Override
    public List<Usuario> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM usuario ORDER BY Nome");
            rs = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(instantiateUsuario(rs));
            }
            return usuarios;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }
}
