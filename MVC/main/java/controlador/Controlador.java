/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.Modelo;
import vista.Vista;

/**
 *
 * @author Gadym-01
 */
public class Controlador implements ActionListener{
    private Vista view;
    private Modelo model;
    
    public Controlador (Vista view, Modelo model){
        this.view = view;
        this.model = model;
        this.view.btnMult.addActionListener(this);  
    }
    
    public void iniciar(){
        view.setTitle("MVC Multiplicar");
        view.setLocationRelativeTo(null);
    }
    
    public void actionPerformed(ActionEvent e){
        model.setNumeroUno(Integer.parseInt(view.txtNum1.getText()));
        model.setNumeroDos(Integer.parseInt(view.txtNum2.getText()));
        model.multiplicar();
        view.txtRes.setText(String.valueOf(model.getResultado()));
    }
}
