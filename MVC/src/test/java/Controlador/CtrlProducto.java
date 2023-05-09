
package Controlador;

import Modelo.ConsultasProducto;
import Modelo.Producto;
import Vista.frmProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlProducto implements ActionListener{
    private Producto mod;
    private ConsultasProducto modC;
    private frmProducto frm;

    public CtrlProducto(Producto mod, ConsultasProducto modC, frmProducto frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        this.frm.btnCreate.addActionListener(this);
        this.frm.btnUpdate.addActionListener(this);
        this.frm.btnDelete.addActionListener(this);
        this.frm.btnSearch.addActionListener(this);
        this.frm.btnClear.addActionListener(this);
    }
    
    public void iniciar(){
        frm.setTitle("Productos");
        frm.setLocationRelativeTo(null);
        frm.txtID.setVisible(false);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == frm.btnCreate){
            mod.setCodigo(frm.txtCodigo.getText());
            mod.setNombre(frm.txtNombre.getText());
            mod.setPrecio(Double.parseDouble(frm.txtCodigo.getText()));
            mod.setCantidad(Integer.parseInt(frm.txtCantidad.getText()));
            
            if (modC.registrar(mod)){
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }
        }
        
        if(e.getSource() == frm.btnUpdate){
            mod.setId(Integer.parseInt(frm.txtID.getText()));
            mod.setCodigo(frm.txtCodigo.getText());
            mod.setNombre(frm.txtNombre.getText());
            mod.setPrecio(Double.parseDouble(frm.txtCodigo.getText()));
            mod.setCantidad(Integer.parseInt(frm.txtCantidad.getText()));
            
            if (modC.modificar(mod)){
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
            }
        }
        
        if(e.getSource() == frm.btnDelete){
            mod.setId(Integer.parseInt(frm.txtID.getText()));
                       
            if (modC.eliminar(mod)){
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
            }
        }
        
        if(e.getSource() == frm.btnSearch){
            mod.setCodigo(frm.txtCodigo.getText());
                       
            if (modC.buscar(mod)){
                frm.txtID.setText(String.valueOf(mod.getId()));
                frm.txtNombre.setText(mod.getNombre());
                frm.txtCantidad.setText(String.valueOf(mod.getCantidad()));
                frm.txtPrecio.setText(String.valueOf(mod.getPrecio()));                
            } else {
                JOptionPane.showMessageDialog(null, "Error al Buscar");
                limpiar();
            }
        }
        
        if(e.getSource() == frm.btnClear){
            limpiar();
        }
    }
    
    public void limpiar(){
        frm.txtID.setText(null);
        frm.txtCodigo.setText(null);
        frm.txtNombre.setText(null);
        frm.txtPrecio.setText(null);        
        frm.txtCantidad.setText(null);
    }
    
}
