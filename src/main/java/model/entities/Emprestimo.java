/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Emprestimo {
    private static int prazoDevolucao = 7;
    private Integer id;
    private LocalDate dataEmp = LocalDate.now();
    private LocalDate dataDev;
    private Integer extensoes = 0;
    private Double multa = 0.0;
    private String cpfUsuario;
    private Livro livro;

    public Emprestimo() {
    }
    
    public Emprestimo(String cpfUsuario, Livro livro) {
        this.cpfUsuario = cpfUsuario;
        this.livro = livro;
        this.dataDev = dataEmp.plusDays(prazoDevolucao);
    }

    public int getPrazoDevolucao() {
        return prazoDevolucao;
    }
 
    public void setPrazoDevolucao(int prazoDevolucao) {
        this.prazoDevolucao = prazoDevolucao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataEmp() {
        return dataEmp;
    }

    public void setDataEmp(LocalDate dataEmp) {
        this.dataEmp = dataEmp;
    }

    public LocalDate getDataDev() {
        return dataDev;
    }

    public void setDataDev(LocalDate dataDev) {
        this.dataDev = dataDev;
    }

    public Integer getExtensoes() {
        return extensoes;
    }

    public void setExtensoes(Integer extensoes) {
        this.extensoes = extensoes;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Emprestimo other = (Emprestimo) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Emprestimo{" + "prazoDevolucao=" + prazoDevolucao + ", id=" + id + ", dataEmp=" + dataEmp + ", dataDev=" + dataDev + ", extensoes=" + extensoes + ", multa=" + multa + ", cpfUsuario=" + cpfUsuario + ", livro=" + livro + '}';
    }

     
}
