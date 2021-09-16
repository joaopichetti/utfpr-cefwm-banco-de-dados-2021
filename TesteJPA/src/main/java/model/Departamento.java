package model;

import javax.persistence.*;

@Entity // Define a classe como uma entidade
@Table(name = "departamento")
public class Departamento {

    @Id // Define essa propriedade como atributo chave da tabela
    @Column(name = "cd_dep")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamento_sequence")
    @SequenceGenerator(name = "departamento_sequence", sequenceName = "departamento_seq", allocationSize = 1)
    private Integer codigo;
    @Column(name = "nm_dep")
    private String nome;
    @Column(name = "ds_local")
    private String local;
    @Column(name = "num_func")
    private Integer numeroFuncionarios;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(Integer numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }
}
