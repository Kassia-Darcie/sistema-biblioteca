package model.entities;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

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

    public void listAllUser() {
        List<Usuario> usuarios = usuarioDao.findAll();
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }
}
