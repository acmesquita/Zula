package br.edu.ifpi.ads.tep.zula;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;
import br.edu.ifpi.ads.tep.zula.dominio.modelo.Viagem;

public class MainActivity extends AppCompatActivity {

    private final int NOVA_VIAGEM = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Zula");
        toolbar.setNavigationIcon(R.drawable.ico_turismo);
        setSupportActionBar(toolbar);


    }
    public void onOpcaoNovaViagem(View view) {
        /*Intent para tela de cadastro*/
        Toast.makeText(this, "Clicou em Nova Viagem", Toast.LENGTH_SHORT).show();
        Intent novaViagem = new Intent(this, NovaViagemActivity.class);
        startActivityForResult(novaViagem, NOVA_VIAGEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bundle bundle = null;

        if(data != null){
            bundle = data.getExtras();
        }
        /*Resposta De Nova Viagem*/
        if (requestCode == NOVA_VIAGEM){
            switch (resultCode) {
                case RESULT_OK:
                    Viagem viagem = (Viagem) bundle.getSerializable("VIAGEM");
                    DAO.addViagem(viagem);
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "A viagem não foi salva.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    public void onOpcaoListarViagem(View view) {
        /*Intent para tela de Listagem das Viagens*/
        Toast.makeText(this, "Clicou em Listar Viagem", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MinhasViagensActivity.class);
        startActivity(intent);
    }

    public void onOpcaoNovoGasto(View view) {
        /*Intent para tela de Casdastro*/
        Toast.makeText(this, "Clicou em Novo Gasto", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NovoGastoActivity.class);
        startActivity(intent);
    }
}
