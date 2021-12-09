package br.cleberhomem.trabalhon1;

public class Filme {

    private String id;

    public String titulo;
    public String genero;
    public String tempo;

    public Filme() {

    }

    public Filme(String titulo, String genero, String tempo) {
        this.titulo = titulo;
        this.genero = genero;
        this.tempo = tempo;
    }

    @Override
    public String toString() {return titulo + "  |  " + genero + " | " + tempo; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }

    public void setGenero(String genero) { this.genero = genero; }

    public String getTempo() { return tempo; }

    public void setTempo(String tempo) { this.tempo = tempo; }
}