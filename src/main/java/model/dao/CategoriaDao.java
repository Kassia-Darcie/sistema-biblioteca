package model.dao;

import model.entities.Categoria;

import java.util.List;

public interface CategoriaDao {
    void insert(Categoria obj);
    void update(Categoria obj);
    void deleteById(int id);
    Categoria findById(int id);
    List<Categoria> findAll();
}
