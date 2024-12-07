package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer codCat;
    private String nome;

    public Categoria() {
    }

    public Categoria(Integer codCat, String nome) {
        this.codCat = codCat;
        this.nome = nome;
    }

    public Integer getCodCat() {
        return codCat;
    }

    public void setCodCat(int codCat) {
        this.codCat = codCat;
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
        Categoria categoria = (Categoria) o;
        return getCodCat() == categoria.getCodCat();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodCat());
    }

    @Override
    public String toString() {
        return "Category{" + "codCat=" + getCodCat() +
                ", nome='" + getNome() + '\'' +
                '}';
    }
}
