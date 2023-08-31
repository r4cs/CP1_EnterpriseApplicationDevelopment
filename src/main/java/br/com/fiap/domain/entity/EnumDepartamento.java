package br.com.fiap.domain.entity;

public enum EnumDepartamento {
    DEPARTAMENTO1(new Departamento(null, "Departamento 1")),
    DEPARTAMENTO2(new Departamento(null, "Departamento 2")),
    DEPARTAMENTO3(new Departamento(null, "Departamento 3"));

//    private final Departamento departamento;
    private Departamento departamento;

//    EnumDepartamento(Departamento nomeDepartamento) {
//        this.departamento = nomeDepartamento;
//    }
    EnumDepartamento(Departamento dep) {
        this.departamento = dep;
    }

    public Departamento getDepartamento() {
        return departamento;
    }
}