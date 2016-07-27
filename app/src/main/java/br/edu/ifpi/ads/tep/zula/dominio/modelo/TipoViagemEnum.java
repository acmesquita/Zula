package br.edu.ifpi.ads.tep.zula.dominio.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djas on 20/07/16.
 */
public enum TipoViagemEnum {

    LAZER("Lazer"),
    NEGOCIO("Negocio");

    private String descricao;

    TipoViagemEnum(String descricao){
        this.descricao = descricao;
    }

    public static List<String> getTipos() {
        List<String> tipos = new ArrayList<>();
        for(TipoViagemEnum tipo : TipoViagemEnum.values()){
            tipos.add(tipo.getDescricao());
        }
        return tipos;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoViagemEnum getByDescricao(String descricao){
        for (TipoViagemEnum tipo : values()){
            if(tipo.getDescricao().equals(descricao)){
                return tipo;
            }
        }
        return null;
    }
}
