package model.entities;

import model.dao.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
    private CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
    private EditoraDao editoraDao = DaoFactory.createEditoraDao();
    private AutorDao autorDao = DaoFactory.createAutorDao();
    private LivroDao livroDao = DaoFactory.createLivroDao();

    public void registerUsuario() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Cpf: ");
        String cpf = sc.next();
        System.out.print("Nome: ");
        sc.nextLine();
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Telefone: ");
        String telefone = sc.next();
        System.out.print("Data de nascimento: ");
        LocalDate nascimento = LocalDate.parse(sc.next());
        System.out.print("Endereço: ");
        sc.nextLine();
        String endereco = sc.nextLine();
        Usuario usuario = new Usuario(cpf, nome, email, telefone, nascimento, endereco);
        usuarioDao.insert(usuario);
        System.out.println(usuario);
        sc.close();
    }

    public void updateUsuario(String cpf, String nome, String email, String telefone, LocalDate nascimento, String endereco) {
        Usuario usuario = usuarioDao.findByCpf(cpf);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        usuario.setDataNasc(nascimento);
        usuario.setEndereco(endereco);
        usuarioDao.update(usuario);
    }

    public void listAllUser() {
        List<Usuario> usuarios = usuarioDao.findAll();
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
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
}
