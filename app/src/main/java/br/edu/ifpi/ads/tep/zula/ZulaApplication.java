package br.edu.ifpi.ads.tep.zula;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by catharina on 30/08/16.
 */
public class ZulaApplication extends Application {

    private static ZulaApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name("zula.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }
    public static ZulaApplication getInstance() {
        return context;
    }

}
