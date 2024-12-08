/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.dao.DaoFactory;
import model.dao.EmprestimoDao;
import model.dao.UsuarioDao;
import model.dao.impl.EmprestimoDaoJDBC;

/**
 *
 * @author kassi
 */
public class PedidoEmprestimo {
    private int limiteEmprestimo = 3;
    private final String cpfUsuario;
    private final List<Emprestimo> emprestimos = new ArrayList<>();
    EmprestimoDao emprestimoDao = DaoFactory.createEmprestimoDao();
    UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

    public PedidoEmprestimo(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
        this.limiteEmprestimo = limiteEmprestimo - usuarioDao.getTotalEmprestimos(cpfUsuario);
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
    
    public void addBook(Livro livro) {
        if (emprestimos.size() < limiteEmprestimo) {
            Emprestimo emp = new Emprestimo(cpfUsuario, livro);
            emprestimos.add(emp);
            System.out.println(emp);
        } else {
            JOptionPane diag = new JOptionPane("Limite de empréstimos atingido");
        }
    }
    
    public void removeBook(Livro livro) {
        if (emprestimos.size() > 0) {
            Emprestimo emprestimoToRemove = null;
            for (Emprestimo emp: emprestimos) {
                if (emp.getLivro().getCodLivro().equals(livro.getCodLivro())) {
                    emprestimoToRemove = emp;
                    break;
                }
            }
            emprestimos.remove(emprestimoToRemove);
        }
    }
    
    public void processarPedido() {
        for (Emprestimo emprestimo : emprestimos) {
            emprestimoDao.insert(emprestimo);
        }
    }
}
