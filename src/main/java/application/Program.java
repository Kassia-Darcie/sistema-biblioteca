package application;

import db.DB;
import model.entities.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Livro livro = new Livro(null, "O Senhor dos Anéis: A Sociedade do Anel", "9788595086333", 1, 2019, Arrays.asList(new Autor(null, "J.R.R. Tolkien")),
                new Editora(null, "HARLEQUIN"),
                new Categoria(null, "Fantasia"));
        biblioteca.registerLivro(livro);
    }

    static void cadastrarUsuario(String cpf, String nome, String email, String telefone, LocalDate dataNasc, String endereco) {

    }
}
