package com.jarias.armaspersonajes.base;

import org.bson.types.ObjectId;

public class Arma {

	private ObjectId id;
	private String nombre;
	private int ataque;
	private int durabilidad;
	private Personaje personaje;
	
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
	
	public int getAtaque() {
		return ataque;
	}
	
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	
	public int getDurabilidad() {
		return durabilidad;
	}
	
	public void setDurabilidad(int durabilidad) {
		this.durabilidad = durabilidad;
	}
	
	public Personaje getPersonaje() {
		return personaje;
	}
	
	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
