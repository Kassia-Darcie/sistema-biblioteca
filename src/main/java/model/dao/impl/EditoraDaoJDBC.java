package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.EditoraDao;
import model.entities.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditoraDaoJDBC implements EditoraDao {
    private final Connection conn;

    public EditoraDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Editora obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO editora (Nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getNome());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setCodEditora(id);
                    DB.closeResultSet(rs);
                }
            } else {
                throw new DbException("Erro ao inserir categoria");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void update(Editora obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE editora SET Nome = ? WHERE CodEditora = ?");
            ps.setString(1, obj.getNome());
            ps.setInt(2, obj.getCodEditora());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM editora WHERE CodEditora = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public Editora findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM editora WHERE CodEditora = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiateEditora(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Editora findByNome(String nome) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM editora WHERE Nome = ?");
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiateEditora(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Editora> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM editora ORDER BY Nome");
            rs = ps.executeQuery();
            List<Editora> editoras = new ArrayList<>();
            while (rs.next()) {
                editoras.add(instantiateEditora(rs));
            }
            return editoras;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    private static Editora instantiateEditora(ResultSet rs) throws SQLException {
        return new Editora(rs.getInt("CodEditora"), rs.getString("Nome"));
    }
}
