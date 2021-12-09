package br.cleberhomem.trabalhon1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "ListaFilmes";
    private static final int VERSAO = 2;

    public Banco(Context context){
        super(context, NOME_BANCO,null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS filme ( id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , titulo TEXT NOT NULL , genero TEXT,  tempo TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
