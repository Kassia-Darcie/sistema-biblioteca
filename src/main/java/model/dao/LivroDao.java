package model.dao;

import model.entities.Livro;

import java.util.List;
import model.entities.Emprestimo;

public interface LivroDao {
    void insert(Livro obj);
    void update(Livro obj);
    void deleteById(int id);
    Livro findById(int id);
    List<Livro> findAll();
    List<Livro> searchByIsbnOrTitulo(String txt);
    List<Emprestimo> obterEmprestimoAssociado(int id); 
}
