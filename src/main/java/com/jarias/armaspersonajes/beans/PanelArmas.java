package com.jarias.armaspersonajes.beans;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jarias.armaspersonajes.mvc.Model;
import com.jarias.armaspersonajes.base.Arma;

public class PanelArmas extends JPanel implements ActionListener, MouseListener {
	public JTextField tfDurabilidad;
	public JTextField tfAtaque;
	public JTextField tfNombre;
	public BotonesCrud botonesCrud;
	public Busqueda busqArma;
	public JList<Arma> lArmas;
	public JLabel lblNombre;
	public JLabel lblAtaque;
	public JLabel lblDurabilidad;
	public DefaultListModel<Arma> mArmas;
	public boolean editando;
	public JButton btnEliminarTodo;
	public boolean borrado;

	private Model model;
	private PanelPersonajes pPersonajes;
	
	public PanelArmas(Model model, PanelPersonajes pPersonajes) {
		this.model = model;
		this.pPersonajes = pPersonajes;
		setLayout(null);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(40, 29, 46, 14);
		add(lblNombre);
		
		botonesCrud = new BotonesCrud();
		botonesCrud.setBounds(30, 104, 208, 114);
		add(botonesCrud);
		
		lblAtaque = new JLabel("Ataque");
		lblAtaque.setBounds(40, 54, 46, 14);
		add(lblAtaque);
		
		lblDurabilidad = new JLabel("Durabilidad");
		lblDurabilidad.setBounds(40, 79, 70, 14);
		add(lblDurabilidad);
		
		tfDurabilidad = new JTextField();
		tfDurabilidad.setBounds(120, 76, 145, 20);
		add(tfDurabilidad);
		tfDurabilidad.setColumns(10);
		
		tfAtaque = new JTextField();
		tfAtaque.setBounds(120, 51, 145, 20);
		add(tfAtaque);
		tfAtaque.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(120, 26, 145, 20);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		busqArma = new Busqueda();
		busqArma.setBounds(301, 209, 258, 20);
		add(busqArma);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(301, 23, 258, 175);
		add(scrollPane);
		
		lArmas = new JList<Arma>();
		scrollPane.setViewportView(lArmas);
		mArmas = new DefaultListModel<>();
		lArmas.setModel(mArmas);
		lArmas.addMouseListener(this);
		botonesCrud.addListeners(this);
		busqArma.btnBuscar.addActionListener(this);
		busqArma.btnBuscar.setActionCommand("buscar");
		
		modoEdicion(false);
		refrescarLista();
		limpiar();
	}

	private void refrescarLista() {
		mArmas.removeAllElements();
		for (Arma arma : model.getArmas())
			mArmas.addElement(arma);
		pPersonajes.refrescarPersonajes();
	}
	
	private void limpiar() {
		tfNombre.setText("");
		tfAtaque.setText("");
		tfDurabilidad.setText("");
	}
	
	public void modoEdicion(boolean activo) {
		if(activo) {
			botonesCrud.btnNuevo.setEnabled(false);
			botonesCrud.btnEditar.setEnabled(false);
			botonesCrud.btnGuardar.setEnabled(true);
			botonesCrud.btnCancelar.setEnabled(true);
			botonesCrud.btnEliminar.setEnabled(false);
			
			tfNombre.setEditable(true);
			tfDurabilidad.setEditable(true);
			tfAtaque.setEditable(true);
		}else {
			botonesCrud.btnNuevo.setEnabled(true);
			botonesCrud.btnEditar.setEnabled(false);
			botonesCrud.btnGuardar.setEnabled(false);
			botonesCrud.btnCancelar.setEnabled(false);
			botonesCrud.btnEliminar.setEnabled(false);
			
			tfNombre.setEditable(false);
			tfDurabilidad.setEditable(false);
			tfAtaque.setEditable(false);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Arma arma;
		switch(actionCommand) {
			case "nuevo":
				editando = false;
				modoEdicion(true);
				break;
			case "editar":
				editando = true;
				modoEdicion(true);
				break;
			case "guardar":
				if (!editando) {
					arma = new Arma();
				}else {
					arma = lArmas.getSelectedValue();
				}
				
				arma.setNombre(tfNombre.getText());
				arma.setAtaque(Integer.parseInt(tfAtaque.getText()));
				arma.setDurabilidad(Integer.parseInt(tfDurabilidad.getText()));
				
				if(editando) {
					model.modificarArma(arma);
					mArmas.addElement(arma);
				}
				else
					model.guardarArma(arma);
					
				refrescarLista();
				limpiar();
				modoEdicion(false);
				break;
			case "eliminar":
				borrado = true;
				modoEdicion(false);
				
				arma = lArmas.getSelectedValue();
				model.eliminarArma(arma);
				limpiar();
				refrescarLista();
				break;
			case "cancelar":
				modoEdicion(false);
				limpiar();
				break;
			case "buscar":
				model.busquedaArmas(busqArma.tfBuscar.getText(), mArmas);
				break;
			default:
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == lArmas) {
			if(lArmas.getSelectedValue() == null)
				return;
			
			botonesCrud.btnEliminar.setEnabled(true);
			botonesCrud.btnEditar.setEnabled(true);
			botonesCrud.btnGuardar.setEnabled(false);
			botonesCrud.btnCancelar.setEnabled(true);
			
			Arma arma = lArmas.getSelectedValue();
			tfNombre.setText(arma.getNombre());
			tfAtaque.setText(String.valueOf(arma.getAtaque()));
			tfDurabilidad.setText(String.valueOf(arma.getDurabilidad()));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
