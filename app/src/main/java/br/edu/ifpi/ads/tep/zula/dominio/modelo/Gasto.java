package br.edu.ifpi.ads.tep.zula.dominio.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by catharina on 20/07/16.
 *
 * Change by catharina on 30/08/16
 */
public class Gasto extends RealmObject implements Serializable{

    @PrimaryKey
    private String id;
    private String nome;
    private Viagem viagem;
    private String tipoDeGasto;
    private Date data;
    private double valor;

    public Gasto(){
        id = UUID.randomUUID().toString();
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public TipoGastoEnum getTipoDeGasto() {
        return TipoGastoEnum.getTipoByDesc(tipoDeGasto);
    }

    public void setTipoDeGasto(TipoGastoEnum tipoDeGasto) {
        this.tipoDeGasto = tipoDeGasto.getDescricao();
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor.doubleValue();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
