package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.AutorDao;
import model.dao.EditoraDao;
import model.entities.Autor;
import model.entities.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDaoJDBC implements AutorDao {
    private final Connection conn;

    public AutorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Autor obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO autor (Nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getNome());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setCodAutor(id);
                    DB.closeResultSet(rs);
                }
            } else {
                throw new DbException("Erro ao inserir autor");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public void update(Autor obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE autor SET Nome = ? WHERE CodAutor = ?");
            ps.setString(1, obj.getNome());
            ps.setInt(2, obj.getCodAutor());
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
            ps = conn.prepareStatement("DELETE FROM autor WHERE CodAutor = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public Autor findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM autor WHERE CodAutor = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiateAutor(rs);
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
    public List<Autor> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM autor ORDER BY Nome");
            rs = ps.executeQuery();
            List<Autor> autors = new ArrayList<>();
            while (rs.next()) {
                autors.add(instantiateAutor(rs));
            }
            return autors;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    private static Autor instantiateAutor(ResultSet rs) throws SQLException {
        return new Autor(rs.getInt("CodAutor"), rs.getString("Nome"));
    }
}
