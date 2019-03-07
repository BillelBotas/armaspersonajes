package com.jarias.armaspersonajes.base;

import java.util.List;

import org.bson.types.ObjectId;

public class Personaje {

	private ObjectId id;
	private String nombre;
	private String descripcion;
	private int vida;
	private int ataque;
	private List<Arma> armas;
	
	public Personaje() {}
	
	public Personaje(ObjectId id, String nombre, String descripcion, int vida, int ataque) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.vida = vida;
		this.ataque = ataque;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public List<Arma> getArmas() {
		return armas;
	}

	public void setArmas(List<Arma> armas) {
		this.armas = armas;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
