package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNasc;
    private String endereco;

    public Usuario() {
    }

    public Usuario(String cpf, String nome, String email, String telefone, String dataNasc, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNasc = LocalDate.parse(dataNasc, fmt);
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate data) {
        this.dataNasc = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getCpf(), usuario.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCpf());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "cpf='" + getCpf() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", dataNasc=" + getDataNasc() +
                ", endereco='" + getEndereco() + '\'' +
                '}';
    }
}
