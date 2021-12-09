package br.cleberhomem.trabalhon1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvFilmes;
    private ArrayAdapter adapter;
    private List<Filme> listaDeFilmes;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvFilmes = findViewById(R.id.lvFilme);

        listaDeFilmes = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeFilmes);

        lvFilmes.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("acao", "adicionar");
                startActivity(intent);
            }
        });

        lvFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filme filmeSelecionado = listaDeFilmes.get(position);
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idFilme" , filmeSelecionado.getId());
                intent.putExtra("Titulo" , filmeSelecionado.getTitulo());
                intent.putExtra("Genero" , filmeSelecionado.getGenero());
                intent.putExtra("Duração" , filmeSelecionado.getTempo());
                startActivity(intent);
            }
        });

        lvFilmes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excluir(position);
                return true;
            }
        });
    }

    private void excluir(int posicao){
        Filme filme = listaDeFilmes.get( posicao );
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir...");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage("Confirma a exclusão do Filme " + filme.getTitulo() +"?");
        alerta.setNeutralButton("Cancelar", null);

        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reference.child("Filmes").child(filme.getId()).removeValue();
            }
        });
        alerta.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarFilmes();
    }

    private void carregarFilmes(){
        listaDeFilmes.clear();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        query = reference.child("Filmes").orderByChild("titulo");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Filme filme = new Filme();
                filme.setId(snapshot.getKey());
                filme.setTitulo(snapshot.child("titulo").getValue(String.class));
                filme.setGenero(snapshot.child("genero").getValue(String.class));
                filme.setTempo(snapshot.child("tempo").getValue(String.class));
                listaDeFilmes.add(filme);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String idFilme = snapshot.getKey();
                for (Filme filme:listaDeFilmes) {
                    if (filme.getId().equals(idFilme)){
                        filme.setTitulo(snapshot.child("titulo").getValue(String.class));
                        filme.setGenero(snapshot.child("genero").getValue(String.class));
                        filme.setTempo(snapshot.child("tempo").getValue(String.class));
                        adapter.notifyDataSetChanged();
                        break;
                    }

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String idFilme = snapshot.getKey();
                for (Filme filme:listaDeFilmes) {
                    if (filme.getId().equals(idFilme)){
                        listaDeFilmes.remove(filme);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                    
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addChildEventListener(childEventListener);

        /*if(listaDeFilmes.size() == 0 ) {
            Filme fake = new Filme("Lista Vazia...", " ", " ");
            listaDeFilmes.add(fake);
            lvFilmes.setEnabled(false);
        }else{
            lvFilmes.setEnabled(true);
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(childEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}