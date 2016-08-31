package br.edu.ifpi.ads.tep.zula.dominio.modelo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

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

    public static List<String> getTipos() {
        List<String> tipos = new ArrayList<>();
        for(TipoGastoEnum tipo : TipoGastoEnum.values()){
            tipos.add(tipo.getDescricao());
        }
        return tipos;
    }

    public static TipoGastoEnum getTipoByDesc(String descricao) {
        for(TipoGastoEnum tipo : TipoGastoEnum.values()){
            if(tipo.getDescricao().equals(descricao)){
                return  tipo;
            }
        }
        return null;
    }

}
