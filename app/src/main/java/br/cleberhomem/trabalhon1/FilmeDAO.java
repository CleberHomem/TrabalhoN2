package br.cleberhomem.trabalhon1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public static void adicionar(Context context, Filme filme){
        ContentValues values = new ContentValues();
        values.put( "titulo" , filme.getTitulo() );
        values.put( "genero" , filme.getGenero() );
        values.put( "tempo" , filme.getTempo() );

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.insert("filme", null , values);
    }

    public static void editar(Context context, Filme filme){
        ContentValues values = new ContentValues();
        values.put( "titulo" , filme.getTitulo() );
        values.put( "genero" , filme.getGenero() );
        values.put( "tempo" , filme.getTempo() );

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.update("Filme", values, " id = " + filme.getId(), null);
    }

    public static void remover(Context context, int idFilme){

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.delete("filme", " id = " + idFilme, null);
    }

    public static List<Filme> getFilme(Context context){
        List<Filme> lista = new ArrayList<>();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM filme ORDER BY titulo ", null);

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                Filme prod = new Filme();
                prod.setId(  cursor.getInt( 0 ) );
                prod.setTitulo( cursor.getString( 1 ));
                prod.setGenero( cursor.getString( 2 ));
                prod.setTempo( cursor.getString( 3 ));
                lista.add( prod );

            }while ( cursor.moveToNext() );
        }
        return lista;
    }

    public static Filme getFilmeById(Context context, int idFilme){

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM filme WHERE id =  " + idFilme, null);

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            Filme prod = new Filme();
            prod.setId(  cursor.getInt( 0 ) );
            prod.setTitulo( cursor.getString( 1 ));
            prod.setGenero( cursor.getString( 2 ));
            prod.setTempo( cursor.getString( 3 ));
            return prod;

        }else {
            return null;
        }
    }

}
