/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domination.mvc.model;

/**
 *
 * @author giann
 */
public class SalaEnsayo implements Alquilable {
    
    private int idSala;
    private int numSala;
    private double valorHora;
    private int idSede;

    public SalaEnsayo() {
    }

    public SalaEnsayo(int numSala, double valorHora, int idSede) {
        this(0,numSala,valorHora,idSede);//a futuro se puede empezar a modificar el valor hora de la sala.
    }
    
    public SalaEnsayo(int idSala, int numSala, double valorHora, int idSede) {
        this.idSala = idSala;
        this.numSala = numSala;
        this.valorHora= valorHora;
        this.idSede = idSede;
    }

    public int getIdSala() {
        return idSala;
    }

    public int getNumSala() {
        return numSala;
    }

    public double getValorHora() {
        return valorHora;
    }
    
    public int getIdSede() {
        return idSede;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }
    
    @Override
    public void Alquilar() {

        
    }
    
}
