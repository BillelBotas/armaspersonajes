package com.jarias.armaspersonajes.beans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jarias.armaspersonajes.mvc.Model;
import com.jarias.armaspersonajes.base.Personaje;

public class PanelPersonajes extends JPanel implements ActionListener, MouseListener {
	public JTextField tfDescripcion;
	public JTextField tfNombre;
	public JTextField tfVida;
	public JTextField tfAtaque;
	public BotonesCrud botonesCrud;
	public JList<Personaje> lPersonajes;
	public DefaultListModel<Personaje> mPersonajes;
	public Busqueda busqPersonaje;
	public JLabel lblNombre;
	public JLabel lblDescripcion;
	public JLabel lblVida;
	public JLabel lblAtaque;
	public boolean editando;
	public PanelAnadirArma panelAnadirArma;
	public boolean borrado;
	
	private Model model;

	public PanelPersonajes(Model model) {
		this.model = model;
		setLayout(null);
		
		botonesCrud = new BotonesCrud();
		botonesCrud.setBounds(30, 273, 202, 108);
		add(botonesCrud);
		
		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(40, 29, 46, 14);
		add(lblNombre);
		
		lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(40, 54, 68, 14);
		add(lblDescripcion);
		
		lblVida = new JLabel("Vida");
		lblVida.setBounds(40, 79, 46, 14);
		add(lblVida);
		
		lblAtaque = new JLabel("Ataque");
		lblAtaque.setBounds(40, 104, 46, 14);
		add(lblAtaque);
		
		tfDescripcion = new JTextField();
		tfDescripcion.setBounds(118, 51, 124, 20);
		add(tfDescripcion);
		tfDescripcion.setColumns(10);
		
		tfNombre = new JTextField();
		tfNombre.setBounds(118, 26, 124, 20);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		tfVida = new JTextField();
		tfVida.setBounds(118, 76, 124, 20);
		add(tfVida);
		tfVida.setColumns(10);
		
		tfAtaque = new JTextField();
		tfAtaque.setBounds(118, 101, 124, 20);
		add(tfAtaque);
		tfAtaque.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(283, 27, 257, 295);
		add(scrollPane);
		
		lPersonajes = new JList<>();
		scrollPane.setViewportView(lPersonajes);
		mPersonajes = new DefaultListModel<>();
		lPersonajes.setModel(mPersonajes);
		lPersonajes.addMouseListener(this);
		
		busqPersonaje = new Busqueda();
		busqPersonaje.btnBuscar.setLocation(169, 0);
		busqPersonaje.setBounds(283, 333, 257, 20);
		add(busqPersonaje);
		
		panelAnadirArma = new PanelAnadirArma();
		panelAnadirArma.setBounds(30, 129, 202, 133);
		add(panelAnadirArma);
		
		inicializar();
	}
	
	public void inicializar() {
		botonesCrud.addListeners(this);
		busqPersonaje.btnBuscar.addActionListener(this);
		busqPersonaje.btnBuscar.setActionCommand("buscar");;
		
		refrescarPersonajes();
		modoEdicion(false);
		limpiar();
	}
	
	public void refrescarPersonajes() {
		mPersonajes.removeAllElements();
		for (Personaje personaje : model.getPersonajes())
			mPersonajes.addElement(personaje);
	}
	
	private void limpiar() {
		tfNombre.setText("");
		tfDescripcion.setText("");
		tfVida.setText("");
		tfAtaque.setText("");
		panelAnadirArma.mArmas.removeAllElements();
	}
	
	private void rellenarDatos(Personaje personaje) {
		tfNombre.setText(personaje.getNombre());
		tfDescripcion.setText(personaje.getDescripcion());
		tfVida.setText(String.valueOf(personaje.getVida()));
		tfAtaque.setText(String.valueOf((personaje.getAtaque())));
		panelAnadirArma.anadirArmas(personaje.getArmas());
	}

	public void modoEdicion(boolean activo) {
		if(activo) {
			botonesCrud.btnNuevo.setEnabled(false);
			botonesCrud.btnEditar.setEnabled(false);
			botonesCrud.btnGuardar.setEnabled(true);
			botonesCrud.btnCancelar.setEnabled(true);
			botonesCrud.btnEliminar.setEnabled(false);
			panelAnadirArma.btnAnadir.setEnabled(true);
			
			tfNombre.setEditable(true);
			tfDescripcion.setEditable(true);
			tfVida.setEditable(true);
			tfAtaque.setEditable(true);
		}else {
			botonesCrud.btnNuevo.setEnabled(true);
			botonesCrud.btnEditar.setEnabled(false);
			botonesCrud.btnGuardar.setEnabled(false);
			botonesCrud.btnCancelar.setEnabled(false);
			botonesCrud.btnEliminar.setEnabled(false);
			panelAnadirArma.btnAnadir.setEnabled(false);
			
			tfNombre.setEditable(false);
			tfDescripcion.setEditable(false);
			tfVida.setEditable(false);
			tfAtaque.setEditable(false);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		Personaje personaje;
		switch (actionCommand) {
			case "nuevo":
				modoEdicion(true);
				editando = false;
				break;
			case "editar":
				editando = true;
				modoEdicion(true);
				break;
			case "guardar":
				if(!editando)
					personaje = new Personaje();
				else 
					personaje = lPersonajes.getSelectedValue();
				
				personaje.setNombre(tfNombre.getText());
				personaje.setDescripcion(tfDescripcion.getText());
				personaje.setVida(Integer.parseInt(tfVida.getText()));
				personaje.setAtaque(Integer.parseInt(tfAtaque.getText()));
				personaje.setArmas(panelAnadirArma.getListadoArmas());
				if(editando)
					model.modificarPersonaje(personaje);
				else
					model.guardarPersonaje(personaje);
				
				refrescarPersonajes();
				limpiar();
				modoEdicion(false);
				break;
			case "eliminar":
				borrado = true;
				modoEdicion(false);
				
				personaje = lPersonajes.getSelectedValue();
				model.eliminarPersonaje(personaje);
				limpiar();
				refrescarPersonajes();
				break;
			case "cancelar":
				modoEdicion(false);
				limpiar();
				break;
			case "buscar":
				model.busquedaPersonajes(busqPersonaje.tfBuscar.getText(), mPersonajes);
				break;
			default:
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == lPersonajes) {
			if(lPersonajes.getSelectedValue() == null)
				return;
			
			botonesCrud.btnNuevo.setEnabled(false);
			botonesCrud.btnEliminar.setEnabled(true);
			botonesCrud.btnEditar.setEnabled(true);
			botonesCrud.btnGuardar.setEnabled(false);
			botonesCrud.btnCancelar.setEnabled(true);
			
			Personaje personaje = lPersonajes.getSelectedValue();
			rellenarDatos(personaje);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Personaje personaje = lPersonajes.getSelectedValue();
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

