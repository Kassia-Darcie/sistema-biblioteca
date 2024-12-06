package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.CategoriaDao;
import model.entities.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoJDBC implements CategoriaDao {
    private final Connection conn;

    public CategoriaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Categoria obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO categoria (Nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getNome());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setCodCat(id);
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
    public void update(Categoria obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE categoria SET Nome = ? WHERE codCat = ?");
            ps.setString(1, obj.getNome());
            ps.setInt(2, obj.getCodCat());
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
            ps = conn.prepareStatement("DELETE FROM categoria WHERE codCat = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public Categoria findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM categoria WHERE codCat = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiateCategoria(rs);
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
    public List<Categoria> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM categoria ORDER BY Nome");
            rs = ps.executeQuery();
            List<Categoria> categorias = new ArrayList<>();
            while (rs.next()) {
                categorias.add(instantiateCategoria(rs));
            }
            return categorias;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    private static Categoria instantiateCategoria(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("codCat"), rs.getString("Nome"));
    }
}
