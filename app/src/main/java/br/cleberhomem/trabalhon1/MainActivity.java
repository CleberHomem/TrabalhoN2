package br.cleberhomem.trabalhon1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.SmsManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvFilmes;
    private ArrayAdapter adapter;
    private List<Filme> listaDeFilmes;
    private EditText etNumero, etMensagem;
    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumero = findViewById(R.id.etNumero);
        etMensagem = findViewById(R.id.etMensagem);
        enviar = findViewById(R.id.btnEnviar);

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    enviarSMS();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        lvFilmes = findViewById(R.id.lvFilme);

        carregarFilmes();

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
                int idFilme = listaDeFilmes.get(position).getId();
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idFilme", idFilme);
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

    private void enviarSMS() {
        String numero = etNumero.getText().toString().trim();
        String mensagem = etMensagem.getText().toString().trim();

        if (!numero.equals("") && !mensagem.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero, null, mensagem, null, null);
            Toast.makeText(getApplicationContext(), "Recomendação enviada!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Preencha os campos!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            enviarSMS();
        } else {
            Toast.makeText(getApplicationContext(), "Permissão negada!", Toast.LENGTH_SHORT).show();

        }
    }

    private void excluir(int posicao) {
        Filme prod = listaDeFilmes.get(posicao);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir...");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage("Confirma a exclusão do Filme " + prod.getTitulo() + "?");
        alerta.setNeutralButton("Cancelar", null);

        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FilmeDAO.remover(MainActivity.this, prod.getId());
                carregarFilmes();
            }
        });
        alerta.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarFilmes();
    }

    private void carregarFilmes() {

        listaDeFilmes = FilmeDAO.getFilme(this);
        if (listaDeFilmes.size() == 0) {
            Filme fake = new Filme("Lista Vazia...", " ", " ");
            listaDeFilmes.add(fake);
            lvFilmes.setEnabled(false);
        } else {
            lvFilmes.setEnabled(true);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeFilmes);
        lvFilmes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        return true;
    }

	return super.onOptionsItemSelected(item);
}

}