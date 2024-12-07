package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.LivroDao;
import model.dao.UsuarioDao;
import model.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDaoJDBC implements LivroDao {
    private final Connection conn;

    public LivroDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Livro obj) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO livro (Titulo, Isbn, AnoPub, CodCat, CodEditora)" +
                    "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obj.getTitulo());
            ps.setString(2, obj.getIsbn());
            ps.setInt(3, obj.getAnoPub());
            ps.setInt(4, obj.getCategoria().getCodCat());
            ps.setInt(5, obj.getEditora().getCodEditora());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setCodLivro(id);
                    DB.closeResultSet(rs);
                }
                for (Autor autor: obj.getAutor()) {
                    ps = conn.prepareStatement("INSERT INTO escrito (CodLivro, CodAutor) VALUES (?, ?)");
                    ps.setInt(1, obj.getCodLivro());
                    ps.setInt(2, autor.getCodAutor());
                    ps.executeUpdate();
                }
            } else {
                throw new DbException("Erro ao inserir livro");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);

        }
    }

    @Override
    public void update(Livro obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE livro " +
                    "SET Titulo = ?, Isbn = ?, AnoPub = ?, CodCat = ?, CodEditora = ? " +
                    "WHERE CodLivro = ?");

            ps.setString(1, obj.getTitulo());
            ps.setString(2, obj.getIsbn());
            ps.setInt(3, obj.getAnoPub());
            ps.setInt(4, obj.getCategoria().getCodCat());
            ps.setInt(5, obj.getEditora().getCodEditora());
            ps.setInt(6, obj.getCodLivro());

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
            ps = conn.prepareStatement("DELETE FROM livro WHERE CodLivro = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
        }
    }

    @Override
    public Livro findById(int id) {
        /*PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT livro.*, FROM usuario WHERE usuario.CpfUsuario = ?");
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
        }*/
        return null;
    }

    @Override
    public List<Livro> findAll() {
        return List.of();
    }

    private static Livro instantiateLivro(ResultSet rs, Categoria cat, Editora editora) throws SQLException {
        Livro livro = new Livro();
        livro.setCodLivro(rs.getInt("CodLivro"));
        livro.setTitulo(rs.getString("Titulo"));
        livro.setIsbn(rs.getString("Isbn"));
        livro.setAnoPub(rs.getInt("AnoPub"));
        livro.setCategoria(cat);
        livro.setEditora(editora);
        return livro;
    }
}
