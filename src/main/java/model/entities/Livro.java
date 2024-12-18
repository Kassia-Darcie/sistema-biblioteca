package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Livro implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer codLivro;
    private String titulo;
    private String isbn;
    private int anoPub;
    private List<Autor> autor = new ArrayList<>();
    private String nomesAutores;
    private Editora editora;
    private Categoria categoria;

    public Livro() {
    }

    public Livro(Integer codLivro, String titulo, String isbn, int anoPub, Editora editora, Categoria categoria) {
        this.codLivro = codLivro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPub = anoPub;
        this.editora = editora;
        this.categoria = categoria;
    }
    
    public Livro(String titulo, String isbn, int anoPub, Editora editora, Categoria categoria) {
        this.codLivro = null;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPub = anoPub;
        this.editora = editora;
        this.categoria = categoria;
    }

    public Integer getCodLivro() {
        return codLivro;
    }

    public void setCodLivro(Integer codLivro) {
        this.codLivro = codLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnoPub() {
        return anoPub;
    }

    public void setAnoPub(int anoPub) {
        this.anoPub = anoPub;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }
    
    public String getNomesAutores() {
        return nomesAutores;
    }

    public void setNomesAutores(String nomesAutores) {
        this.nomesAutores = nomesAutores;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return getCodLivro() == livro.getCodLivro();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCodLivro());
    }

    @Override
    public String toString() {
        return "Livro{" +
                "codLivro=" + getCodLivro() +
                ", titulo='" + getTitulo() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", anoPub=" + getAnoPub() +
                ", autor=" + getAutor() +
                ", editora=" + getEditora() +
                ", categoria=" + getCategoria() +
                '}';
    }

    
}
