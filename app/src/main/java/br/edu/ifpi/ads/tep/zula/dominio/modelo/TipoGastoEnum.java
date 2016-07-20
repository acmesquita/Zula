package br.edu.ifpi.ads.tep.zula.dominio.modelo;

/**
 * Created by djas on 20/07/16.
 */
public enum TipoGastoEnum {

    ALIMENTACAO("Alimentação"),
    TRANSPORTE("Transporte"),
    OUTROS("Outros");

    private String descricao;

    TipoGastoEnum(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
