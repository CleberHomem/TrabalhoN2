package br.cleberhomem.trabalhon1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    private EditText etTitulo;
    private EditText etTempo;
    private Spinner spGenero;
    private Button btnSalvar;
    private String acao;
    private Filme filme;
    private String tempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

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
            public void onClick(View v) {
                salvar();
            }
        });
    }

    private void carregarFormulario(){
        int id = getIntent().getIntExtra("idFilme", 0);
        filme = FilmeDAO.getFilmeById(this, id);
        etTitulo.setText( filme.getTitulo() );
        String[] genero = getResources().getStringArray(R.array.genero);
        etTempo.setText(filme.getTempo() );
        for (int i = 1; i < genero.length ;i++){
            if( filme.getGenero().equals( genero[i] ) ){
                spGenero.setSelection(i);
                break;
            }
        }
    }
    private void salvar(){
        String titulo = etTitulo.getText().toString();
        String tempo = etTempo.getText().toString();
        if( titulo.isEmpty() || spGenero.getSelectedItemPosition()  == 0){
            Toast.makeText(this, "Há campos não preenchidos!", Toast.LENGTH_LONG ).show();
        }else{
            if( acao.equals("adicionar")) {
                filme = new Filme();
            }
            filme.setTitulo( titulo );
            filme.setGenero( spGenero.getSelectedItem().toString() );
            filme.setTempo( tempo );
            if( acao.equals("adicionar")) {
                FilmeDAO.adicionar(this, filme);
                etTitulo.setText("");
                spGenero.setSelection(0, true);
            }else{
                FilmeDAO.editar(this, filme);
                finish();
            }
        }
    }
}
