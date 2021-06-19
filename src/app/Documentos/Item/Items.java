package app.Documentos.Item;

import modelos.enums.Role;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Items extends JPanel {
    private JTable tableItems;
    private JPanel pnlMain;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JPanel pnlActions;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton eliminarButton;
    private JTextField textFieldCodigo;
    private JTextField textFieldTitulo;
    private JComboBox comboBoxTipo;
    private JComboBox comboBoxRubro;
    private JComboBox comboBoxUnidad;
    private JTextField textFieldInicio;
    private JTextField textFieldFin;
    private JLabel lblCodigo;
    private JLabel lblTitulo;
    private JLabel lblTipo;
    private JLabel lblRubro;
    private JLabel lblUnidad;
    private JLabel lblFin;
    private JLabel lblInicio;
    private JPanel pnlFecha;

    public Items(){

        this.add(this.pnlMain);
        this.pnlFecha.setVisible(false);
        populateComboRubro();
        populateComboTipo();
        populateComboUnidad();
        actionSelectedTipo();
    }

    void populateComboTipo(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement(TipoItem.PRODUCTO);
        comboBoxModel.addElement(TipoItem.SERVICIO);

        this.comboBoxTipo.setModel(comboBoxModel);
    }

    void populateComboUnidad(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement(Unidad.UNIDAD);
        comboBoxModel.addElement(Unidad.HORA);
        comboBoxModel.addElement(Unidad.PESO);

        this.comboBoxUnidad.setModel(comboBoxModel);
    }

    void populateComboRubro(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        for (Rubro rubro : Rubro.values()) {
            comboBoxModel.addElement(rubro);
        }

        this.comboBoxRubro.setModel(comboBoxModel);
    }

    void actionSelectedTipo(){

        Items self = this;

        this.comboBoxTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = self.comboBoxTipo.getSelectedItem().toString();

                if(value.equals(TipoItem.SERVICIO.name())){
                    self.pnlFecha.setVisible(true);
                } else {
                    self.pnlFecha.setVisible(false);
                }

            }
        });
    }
}

