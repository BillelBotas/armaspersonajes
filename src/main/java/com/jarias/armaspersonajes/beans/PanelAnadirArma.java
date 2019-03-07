package com.jarias.armaspersonajes.beans;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.jarias.armaspersonajes.base.Arma;
import com.jarias.armaspersonajes.mvc.Model;

public class PanelAnadirArma extends JPanel implements ActionListener {
	public JPanel panel;
	public ComboGenerico<Arma> cgArmas;
	public JScrollPane scrollPane;
	public JList<Arma> lArmas;
	public DefaultListModel<Arma> mArmas;
	public JButton btnAnadir;

	public PanelAnadirArma() {
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 450, 1);
		add(panel);
		
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 39, 202, 93);
		add(scrollPane);
		
		lArmas = new JList<>();
		scrollPane.setViewportView(lArmas);
		mArmas = new DefaultListModel<>();
		lArmas.setModel(mArmas);
		
		cgArmas = new ComboGenerico<>();
		cgArmas.setBackground(Color.WHITE);
		cgArmas.setBounds(0, 11, 154, 24);
		add(cgArmas);
		
		btnAnadir = new JButton("+");
		btnAnadir.setBackground(Color.WHITE);
		btnAnadir.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnadir.setBounds(156, 12, 46, 23);
		btnAnadir.setActionCommand("anadir");
		add(btnAnadir);

		inicializar();
	}
	
	private void inicializar() {
		Model model = new Model();
		List<Arma> armas = model.getArmasLibres();
		cgArmas.inicializar(armas);
		btnAnadir.addActionListener(this);
	}
	
	public List<Arma> getListadoArmas() {
		return Collections.list(mArmas.elements());
	}
	
	public void anadirArmas(List<Arma> armas) {
		mArmas.removeAllElements();
		for (Arma arma : armas)
			mArmas.addElement(arma);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "anadir":
				Arma armaSeleccionada = cgArmas.getDatoSeleccionado();
				if (armaSeleccionada == null)
					return;
				if (mArmas.contains(armaSeleccionada))
					return;
				
				mArmas.addElement(armaSeleccionada);
				break;
			default:
				break;
		}
	}
}