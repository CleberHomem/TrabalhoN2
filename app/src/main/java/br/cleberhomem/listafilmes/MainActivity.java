package br.cleberhomem.listafilmes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText etTitulo = findViewById(R.id.edit_titulo);
        final EditText etGenero = findViewById(R.id.edit_genero);
        Button btn = findViewById(R.id.btn_enviar);
        Button btn_listar = findViewById(R.id.btn_listar);
        btn_listar.setOnClickListener(v->
        {
            Intent intent =new Intent(MainActivity.this, RVActivity.class);
            startActivity(intent);
        });
        DAOFilme dao = new DAOFilme();
        Filme film_edit = (Filme)getIntent().getSerializableExtra("EDIT");
        if(film_edit !=null)
        {
            btn.setText("UPDATE");
            etTitulo.setText(film_edit.getTitulo());
            etGenero.setText(film_edit.getGenero());
            btn_listar.setVisibility(View.GONE);
        }
        else
        {
            btn.setText("ADICIONAR");
            btn_listar.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v->
        {
            Filme film = new Filme(etTitulo.getText().toString(), etGenero.getText().toString());
            if(film_edit==null)
            {
                dao.add(film).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Filme adicionado", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            else
            {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("titulo", etTitulo.getText().toString());
                hashMap.put("genero", etGenero.getText().toString());
                dao.update(film.getKey(), hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Filme atualizado", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });


    }
}