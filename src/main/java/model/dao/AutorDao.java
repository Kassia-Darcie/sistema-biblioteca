package model.dao;

import model.entities.Autor;

import java.util.List;

public interface AutorDao {
    void insert(Autor obj);
    void update(Autor obj);
    void deleteById(int id);
    Autor findById(int id);
    Autor findByNome(String nome);
    List<Autor> findAll();
}
