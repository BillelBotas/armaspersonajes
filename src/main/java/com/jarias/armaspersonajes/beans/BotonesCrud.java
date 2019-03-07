package com.jarias.armaspersonajes.beans;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class BotonesCrud extends JPanel {
	public JButton btnNuevo, btnGuardar, btnEliminar, btnCancelar, btnEditar;
	
	public BotonesCrud() {
		setLayout(null);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(10, 11, 89, 23);
		btnNuevo.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		btnNuevo.setBackground(Color.WHITE);
		add(btnNuevo);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(10, 45, 89, 23);
		btnGuardar.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		btnGuardar.setBackground(Color.WHITE);
		add(btnGuardar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(109, 11, 89, 23);
		btnEditar.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		btnEditar.setBackground(Color.WHITE);
		add(btnEditar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(109, 45, 89, 23);
		btnCancelar.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		btnCancelar.setBackground(Color.WHITE);
		add(btnCancelar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(109, 79, 89, 23);
		btnEliminar.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		btnEliminar.setBackground(Color.WHITE);
		add(btnEliminar);
	}

	public void addListeners(ActionListener listener) {
		btnNuevo.addActionListener(listener);
		btnNuevo.setActionCommand("nuevo");
		btnGuardar.addActionListener(listener);
		btnGuardar.setActionCommand("guardar");
		btnEditar.addActionListener(listener);
		btnEditar.setActionCommand("editar");
		btnEliminar.addActionListener(listener);
		btnEliminar.setActionCommand("eliminar");
		btnCancelar.addActionListener(listener);
		btnCancelar.setActionCommand("cancelar");
	}
}
