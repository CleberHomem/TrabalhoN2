package br.cleberhomem.trabalhon1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etTempo;
    private Spinner spGenero;
    private Button btnSalvar;
    private String acao;
    private Filme filme;
    private String tempo;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        etTitulo = findViewById(R.id.etTitulo);
        etTempo = findViewById(R.id.etTempo);
        spGenero =  findViewById(R.id.spGenero);
        btnSalvar = findViewById(R.id.btnSalvar);

        acao = getIntent().getStringExtra("acao");
        if( acao.equals("modificar") ){
            carregarFormulario();
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
            salvar();
            }
        });
    }

    private void carregarFormulario(){
        String id = getIntent().getStringExtra("idFilme");
        String titulo = getIntent().getStringExtra("titulo");
        String genero = getIntent().getStringExtra("genero");
        String tempo = getIntent().getStringExtra("tempo");
        filme = new Filme();
        filme.setId(id);
        filme.setTitulo(titulo);
        filme.setGenero(genero);
        filme.setTempo(tempo);
        etTitulo.setText(filme.getTitulo());
        String[] categorias = getResources().getStringArray(R.array.genero);
        for (int i = 1; i < genero.length();i++){
            if( filme.getGenero().equals(categorias[i] ) ){
                spGenero.setSelection(i);
                break;
            }
        }
    }
    private void salvar(){
        String titulo = etTitulo.getText().toString();
        if( titulo.isEmpty() || spGenero.getSelectedItemPosition()  == 0 ){
            Toast.makeText(this, "Você deve preencher todos os campos!", Toast.LENGTH_LONG ).show();
        }else{
            if( acao.equals("inserir")) {
                reference.child("filmes").push().setValue(filme);
                etTitulo.setText("");
                spGenero.setSelection(0,true);
                Toast.makeText(this,"Filme cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            }else{
                reference.child("filmes").child(filme.getId() ).setValue(filme);
                Toast.makeText(this,"Filme atualizado com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}