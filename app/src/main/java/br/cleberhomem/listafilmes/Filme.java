package br.cleberhomem.listafilmes;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Filme implements Serializable
{

    @Exclude
    private String key;
    private String titulo;
    private String genero;

    public Filme(){}
    public Filme(String titulo, String genero)
    {
        this.titulo = titulo;
        this.genero = genero;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getGenero()
    {
        return genero;
    }

    public void setGenero(String position)
    {
        this.genero = genero;
    }
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}