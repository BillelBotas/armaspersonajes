package com.jarias.armaspersonajes.beans;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

import com.jarias.armaspersonajes.base.Arma;

public class ComboGenerico<T> extends JComboBox<T> {

	private List<T> datos;
	
	public ComboGenerico() {
		super();
	}
	
	public void inicializar(List<T> datos) {
		this.datos = datos;
		listar();
	}
	
	public void refrescar() {
		
		limpiar();
		listar();
	}
	
	public void listar() {
		
		if (datos == null)
			return;
		
		for(T dato : datos)
			addItem(dato);
	}
	
	public void limpiar() {
		removeAllItems();
	}
	
	public T getDatoSeleccionado() {
		T dato = (T) getSelectedItem();
		return dato;
	}
}

