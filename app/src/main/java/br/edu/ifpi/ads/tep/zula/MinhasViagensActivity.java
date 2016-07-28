package br.edu.ifpi.ads.tep.zula;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.List;

import br.edu.ifpi.ads.tep.zula.adapters.ViagemAdapter;
import br.edu.ifpi.ads.tep.zula.dominio.dao.DAO;

public class MinhasViagensActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List viagens;
    private ViagemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_viagens);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Minhas Viagens");
        actionBar.dispatchMenuVisibilityChanged(true);

        viagens =  DAO.getViagens();
        adapter = new ViagemAdapter(this,viagens);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_minhas_viagens, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==  R.id.action_search){
            return true;
        }
        else if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
