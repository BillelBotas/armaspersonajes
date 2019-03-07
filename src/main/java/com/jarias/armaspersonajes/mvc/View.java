package com.jarias.armaspersonajes.mvc;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jarias.armaspersonajes.base.Arma;
import com.jarias.armaspersonajes.beans.PanelArmas;
import com.jarias.armaspersonajes.beans.PanelPersonajes;

public class View extends JFrame implements ChangeListener {

	private JPanel contentPane;
	private Model model;
	public PanelPersonajes panelPersonajes;
	public PanelArmas panelArmas;
	public JTabbedPane tabbedPane;
	
	public View() {
		model = new Model();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		panelPersonajes = new PanelPersonajes(model);
		tabbedPane.addTab("Personajes", null, panelPersonajes, null);
		
		panelArmas = new PanelArmas(model, panelPersonajes);
		tabbedPane.addTab("Armas", null, panelArmas, null);
		
		tabbedPane.addChangeListener(this);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		model = new Model();
		List<Arma> armas = model.getArmas();
		panelPersonajes.panelAnadirArma.cgArmas.inicializar(armas);
		panelPersonajes.panelAnadirArma.cgArmas.refrescar();
	}
}
