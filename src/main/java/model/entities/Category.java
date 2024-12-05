package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int codCat;
    private String nome;

    public Category(int codCat, String nome) {
        this.codCat = codCat;
        this.nome = nome;
    }

    public int getCodCat() {
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
        Category category = (Category) o;
        return getCodCat() == category.getCodCat();
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
