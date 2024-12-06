package application;

import db.DB;
import model.entities.Biblioteca;
import model.entities.Categoria;

import java.sql.Connection;
import java.time.LocalDate;

public class Program {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.listAllUser();
    }

    static void cadastrarUsuario(String cpf, String nome, String email, String telefone, LocalDate dataNasc, String endereco) {

    }
}
