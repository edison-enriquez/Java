/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Gadym-01
 */
public class Modelo {
    
    private int numeroUno, numeroDos, resultado;

    public int getNumeroUno() {
        return numeroUno;
    }

    public int getNumeroDos() {
        return numeroDos;
    }

    public int getResultado() {
        return resultado;
    }

    public void setNumeroUno(int numeroUno) {
        this.numeroUno = numeroUno;
    }

    public void setNumeroDos(int numeroDos) {
        this.numeroDos = numeroDos;
    }

    public void setResultado(int Resultado) {
        this.resultado = Resultado;
    }
    
    public int multiplicar(){
        this.resultado = this.numeroUno * this.numeroDos;
        return this.resultado;
    }
}
