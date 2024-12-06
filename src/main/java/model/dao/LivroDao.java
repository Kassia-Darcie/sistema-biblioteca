package model.dao;

import model.entities.Livro;

import java.util.List;

public interface LivroDao {
    void insert(Livro obj);
    void update(Livro obj);
    void deleteById(int id);
    Livro findById(int id);
    List<Livro> findAll();
}
