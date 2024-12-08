package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.LivroDao;
import model.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.dao.AutorDao;
import model.dao.CategoriaDao;
import model.dao.EditoraDao;

public class LivroDaoJDBC implements LivroDao {
    private final Connection conn;
    private AutorDao autorDao = new AutorDaoJDBC(DB.getConnection());
    private CategoriaDao categoriaDao = new CategoriaDaoJDBC(DB.getConnection());
    private EditoraDao editoraDao = new EditoraDaoJDBC(DB.getConnection());

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
                    ps = conn.prepareStatement("INSERT INTO escrito_por (CodLivro, CodAutor) VALUES (?, ?)");
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM listalivro WHERE listalivro.CodLivro = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return instantiateLivro(rs);
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
    public List<Livro> findAll() {
        List<Livro> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM listalivro";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(instantiateLivro(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    private Livro instantiateLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setCodLivro(rs.getInt("CodLivro"));
        livro.setTitulo(rs.getString("Titulo"));
        livro.setIsbn(rs.getString("Isbn"));
        livro.setAnoPub(rs.getInt("AnoPub"));
        livro.setNomesAutores(rs.getString("autores"));
        Categoria categoria = categoriaDao.findByNome(rs.getString("Categoria"));
        Editora editora = editoraDao.findByNome(rs.getString("Editora"));
        String[] autores = rs.getString("autores").split(", ");
        List<Autor> listAutor = new ArrayList<>();
        for (String nomeAutor : autores) {
            Autor autor = autorDao.findByNome(nomeAutor);
            listAutor.add(autor);
        }
        livro.setCategoria(categoria);
        livro.setEditora(editora);
        livro.setAutor(listAutor);
        return livro;
    }

    @Override
    public List<Livro> searchByIsbnOrTitulo(String txt) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = String.format("SELECT * FROM listalivro WHERE REGEXP_LIKE(listalivro.CodLivro, '^%s') = 1 OR REGEXP_LIKE(listalivro.Titulo, '^%s') = 1", txt, txt);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Livro> livros = new ArrayList<>();
            while (rs.next()) {
                livros.add(instantiateLivro(rs));
            }
            return livros;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Emprestimo> obterEmprestimoAssociado(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM emprestimo WHERE emprestimo.CodLivro = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            List<Emprestimo> emps = new ArrayList<>();
            while (rs.next()) {
                Livro livro = findById(id);
                emps.add(EmprestimoDaoJDBC.instantiateEmprestimo(rs, livro));
            }
            return emps;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(ps);
            DB.closeResultSet(rs);
        }
    }
}
