package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Editora implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int codEditora;
    private String nome;

    public Editora(int codEditora, String nome) {
        this.codEditora = codEditora;
        this.nome = nome;
    }

    public int getCodEditora() {
        return codEditora;
    }

    public void setCodEditora(int codEditora) {
        this.codEditora = codEditora;
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
        Editora editora = (Editora) o;
        return getCodEditora() == editora.getCodEditora();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodEditora());
    }

    @Override
    public String toString() {
        return "Editora{" +
                "codEditora=" + getCodEditora() +
                ", nome='" + getNome() + '\'' +
                '}';
    }
}
