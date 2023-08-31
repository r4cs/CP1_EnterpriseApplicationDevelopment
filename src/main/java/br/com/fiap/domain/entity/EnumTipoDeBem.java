package br.com.fiap.domain.entity;

public enum EnumTipoDeBem {
    COMPUTADOR(new TipoDeBem(null, "computador")),
    MoVEL(new TipoDeBem(null, "movel")),
    VEiCULO(new TipoDeBem(null, "veiculo")),
    MaQUINA(new TipoDeBem(null, "maquina")),
    EQUIPAMENTO(new TipoDeBem(null, "equipamento")),
    FERRAMENTA(new TipoDeBem(null, "ferramenta")),
    ELETRODOMÉSTICO(new TipoDeBem(null, "eletrodomestico")),
    INSTRUMENTO(new TipoDeBem(null, "instrumento")),
    EDIFiCIO(new TipoDeBem(null, "edificio")),
    TERRA(new TipoDeBem(null, "terra"));

    private TipoDeBem tipoDeBem;

    EnumTipoDeBem(TipoDeBem tipoDeBem) {
        this.tipoDeBem = tipoDeBem;
    }

    public TipoDeBem getTipoDeBem() {
        return tipoDeBem;
    }
}
