package br.edu.ifpi.ads.tep.zula.dominio.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;

/**
 * Created by djas on 20/07/16.
 */
public class DAO {

    private static List<Viagem> viagens = new ArrayList<>();

    public static List<Viagem> getViagens(){
        return viagens;
    }

    public static void addViagem(Viagem v){
        viagens.add(v);
    }

    public static void removerViagem(Viagem v){
        viagens.remove(v);
    }



}
