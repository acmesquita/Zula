package br.edu.ifpi.ads.tep.zula.dominio.modelo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by catharina on 20/07/16.
 */
public class Gasto {

    private String nome;
    private Viagem viagem;
    private TipoGastoEnum tipoDeGasto;
    private Date data;
    private BigDecimal valor;

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public TipoGastoEnum getTipoDeGasto() {
        return tipoDeGasto;
    }

    public void setTipoDeGasto(TipoGastoEnum tipoDeGasto) {
        this.tipoDeGasto = tipoDeGasto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gasto gasto = (Gasto) o;

        if (!getNome().equals(gasto.getNome())) return false;
        if (!getViagem().equals(gasto.getViagem())) return false;
        if (getTipoDeGasto() != gasto.getTipoDeGasto()) return false;
        if (!getData().equals(gasto.getData())) return false;
        return getValor().equals(gasto.getValor());

    }

    @Override
    public int hashCode() {
        int result = getNome().hashCode();
        result = 31 * result + getViagem().hashCode();
        result = 31 * result + getTipoDeGasto().hashCode();
        result = 31 * result + getData().hashCode();
        result = 31 * result + getValor().hashCode();
        return result;
    }
}
