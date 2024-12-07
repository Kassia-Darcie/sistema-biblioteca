package model.dao;

import model.entities.Editora;

import java.util.List;

public interface EditoraDao {
    void insert(Editora obj);
    void update(Editora obj);
    void deleteById(int id);
    Editora findById(int id);
    Editora findByNome(String nome);
    List<Editora> findAll();
}
