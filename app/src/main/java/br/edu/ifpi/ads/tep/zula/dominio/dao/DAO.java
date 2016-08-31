package br.edu.ifpi.ads.tep.zula.dominio.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.dominio.modelo.Gasto;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoGastoEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.TipoViagemEnum;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by djas on 20/07/16.
 *
 * Change by Catharina on 30/08/16
 */
public class DAO {

   /* private static List<Viagem> viagens = new ArrayList<>();


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
    }*/

    private Realm realm;
    private static DAO INSTANCE;

    private DAO(Realm realm) {
        this.realm = realm;
    }

    public static DAO getInstance(Realm realm) {
        if (INSTANCE == null) {
            INSTANCE = new DAO(realm);
        }
        return INSTANCE;
    }


    public void saveVigem(final Viagem viagem){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(viagem);
            }
        });
    }

    public void saveGasto(final Gasto gasto){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(gasto);
            }
        });
    }

    public Viagem getViagemById(String id){
        Viagem viagem = realm.where(Viagem.class).equalTo("id", id).findFirst();
        return viagem;
    }

    public Gasto getGastoById(String id){
        Gasto gasto= realm.where(Gasto.class).equalTo("id", id).findFirst();
        return gasto;
    }

    public RealmResults<Viagem> getViagemByDestino(String nome){
        return realm.where(Viagem.class).contains("destino", nome, Case.INSENSITIVE).findAll();
    }

    public RealmResults<Gasto> getGastoByNome(String nome){
        return realm.where(Gasto.class).contains("nome", nome, Case.INSENSITIVE).findAll();
    }

    public RealmResults<Viagem> getViagemAll(){
        return realm.where(Viagem.class).findAll();
    }

    public RealmResults<Gasto> getGastoAll(){
        return realm.where(Gasto.class).findAll();
    }

    public void removeViagemById(String id){
        final Viagem viagem = this.getViagemById(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(viagem != null){
                    viagem.getGastos().deleteAllFromRealm();
                    viagem.deleteFromRealm();
                }
            }
        });
    }

    public void removeGastoById(String id){
        final Gasto gasto = this.getGastoById(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(gasto != null){
                    gasto.deleteFromRealm();
                }
            }
        });
    }


}
