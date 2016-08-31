package br.edu.ifpi.ads.tep.zula.dominio.modelo;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by djas on 20/07/16.
 *
 * Change by Catharina 30/08/16
 */
public class Viagem extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;
    private String tipoViagem;
    private String destino;
    private Date data;
    private RealmList<Gasto> gastos;

    public Viagem(){
        id = UUID.randomUUID().toString();
        this.gastos = new RealmList<>();
    }

    public Viagem(TipoViagemEnum tipoViagem, String destino, Date data, RealmList<Gasto> gastos) {
        this.tipoViagem = tipoViagem.getDescricao();
        this.destino = destino;
        this.data = data;
        this.gastos = gastos;
    }

    public TipoViagemEnum getTipoViagem() {
        return TipoViagemEnum.getByDescricao(tipoViagem);
    }

    public void setTipoViagem(TipoViagemEnum tipoViagem) {
        this.tipoViagem = tipoViagem.getDescricao();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public RealmList<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(RealmList<Gasto> gastos) {
        this.gastos = gastos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Viagem viagem = (Viagem) o;

        if (getTipoViagem() != viagem.getTipoViagem()) return false;
        if (!getDestino().equals(viagem.getDestino())) return false;
        return getData().equals(viagem.getData());

    }

    @Override
    public int hashCode() {
        int result = getTipoViagem().hashCode();
        result = 31 * result + getDestino().hashCode();
        result = 31 * result + getData().hashCode();
        return result;
    }
}
