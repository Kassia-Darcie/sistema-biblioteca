package model.dao;

import model.entities.Usuario;

import java.util.List;

public interface UsuarioDao {
    void insert(Usuario obj);
    void update(Usuario obj);
    void deleteByCpf(String cpf);
    Usuario findByCpf(String cpf);
    List<Usuario> findAll();
}
