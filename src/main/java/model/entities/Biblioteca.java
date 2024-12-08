package model.entities;

import model.dao.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Biblioteca {
    private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
    private CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
    private EditoraDao editoraDao = DaoFactory.createEditoraDao();
    private AutorDao autorDao = DaoFactory.createAutorDao();
    private LivroDao livroDao = DaoFactory.createLivroDao();
    
    private List<Usuario> usuarios;

    public Biblioteca() {
        usuarios = usuarioDao.findAll();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    

    public void registerUsuario(Usuario usuario) {
        usuarioDao.insert(usuario);
    }

    public void updateUsuario(Usuario usuario) {
        Usuario u = usuarioDao.findByCpf(usuario.getCpf());
        u.setNome(usuario.getNome());
        u.setEmail(usuario.getEmail());
        u.setTelefone(usuario.getTelefone());
        u.setDataNasc(usuario.getDataNasc());
        u.setEndereco(usuario.getEndereco());
        usuarioDao.update(u);
    }

    public List<Usuario> listAllUser() {
        return usuarioDao.findAll();
    }

    public void registerLivro (Livro livro) {
        Categoria categoria = categoriaDao.findByNome(livro.getCategoria().getNome());
        Editora editora = editoraDao.findByNome(livro.getEditora().getNome());


        if (categoria == null) {
            categoriaDao.insert(livro.getCategoria());
        } else {
            livro.setCategoria(categoria);
        }
        if (editora == null) {
            editoraDao.insert(livro.getEditora());
        } else {
            livro.setEditora(editora);
        }
        for (Autor a: livro.getAutor()) {
            Autor autor = autorDao.findByNome(a.getNome());
            if (autor == null) {
                autorDao.insert(a);
            } else {
                a.setCodAutor(autor.getCodAutor());
            }
        }
        livroDao.insert(livro);
    }
    
    public Usuario searchUserByCpf(String cpf) {
        if (cpf.length() == 11) {
            return usuarioDao.findByCpf(cpf);
            
        } else {
            JOptionPane diag = new JOptionPane("Cpf invalido");
            diag.setVisible(true);
        }
        return  null;
    }
    
    public List<Usuario> searchUser(String text) {
        return usuarioDao.searchByCpfOrNome(text);
    }
    
    public List<Livro> searchBook(String txt) {
        return livroDao.searchByIsbnOrTitulo(txt);
    }
    
    public Livro findBookById(int id) {
        return livroDao.findById(id);
    }
    
    public void deleteUser(String cpf) {
        usuarioDao.deleteByCpf(cpf);
    }
    
    public List<Emprestimo> obterEmprestimosAssociadosAUmLivro(int idLivro) {
        return livroDao.obterEmprestimoAssociado(idLivro);
    }
    
    public int getTotalEmprestimosUsuario(String cpf) {
        return usuarioDao.getTotalEmprestimos(cpf);
    }
}
