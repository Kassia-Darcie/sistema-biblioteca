/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model.dao;

import java.util.List;
import model.entities.Emprestimo;


public interface EmprestimoDao {
    void insert(Emprestimo obj);
    void extender(int id);
    List<Emprestimo> listarEmprestimos();
    void deletarEmprestimo(int id);
}
