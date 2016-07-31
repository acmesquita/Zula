package br.edu.ifpi.ads.tep.zula.dominio.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoGastoEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoViagemEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;

/**
 * Created by djas on 20/07/16.
 */
public class DAO {

    private static List<Viagem> viagens = new ArrayList<>();


    static{
        Viagem v = new Viagem();
        v.setId(0);
        v.setTipoViagem(TipoViagemEnum.LAZER);
        v.setDestino("Zica");
        v.setData(new Date());
        Gasto g =  new Gasto();
        g.setId(0);
        g.setData(new Date());
        g.setValor(BigDecimal.TEN);
        g.setTipoDeGasto(TipoGastoEnum.ALIMENTACAO);
        g.setViagem(v);
        v.getGastos().add(g);
        v.setId(viagens.size());
        viagens.add(v);
    }

    public static List<Viagem> getViagens(){
        return viagens;
    }

    public static void addViagem(Viagem v){
        v.setId(viagens.size());
        viagens.add(v);
    }

    public static void removerViagem(Viagem v){
        viagens.remove(v);
    }

    public  static Viagem getViagemById( int id){
        return viagens.get(id);
    }

    public static int getIdByDestino(String destino){
        for(Viagem viagem: viagens){
            if(viagem.getDestino().equals(destino)){
                return viagem.getId();
            }
        }

        return -1;
    }


}
