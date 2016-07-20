package br.edu.ifpi.ads.tep.zula.dominio.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by djas on 20/07/16.
 */
public class Viagem {

    private TipoViagemEnum tipoViagem;
    private String destino;
    private Date data;
    private List<Gasto> gastos;

    public Viagem(){
        this.gastos = new ArrayList<>();
    }

    public Viagem(TipoViagemEnum tipoViagem, String destino, Date data, List<Gasto> gastos) {
        this.tipoViagem = tipoViagem;
        this.destino = destino;
        this.data = data;
        this.gastos = gastos;
    }

    public TipoViagemEnum getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(TipoViagemEnum tipoViagem) {
        this.tipoViagem = tipoViagem;
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

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
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
