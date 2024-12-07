package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Autor implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer codAutor;
    private String nome;

    public Autor() {
    }

    public Autor(Integer codAutor, String nome) {
        this.codAutor = codAutor;
        this.nome = nome;
    }

    public Integer getCodAutor() {
        return codAutor;
    }

    public void setCodAutor(int codAutor) {
        this.codAutor = codAutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return getCodAutor() == autor.getCodAutor();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodAutor());
    }

    @Override
    public String toString() {
        return "Autor{" +
                "codAutor=" + getCodAutor() +
                ", nome='" + getNome() + '\'' +
                '}';
    }
}
