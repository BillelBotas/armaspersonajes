package com.jarias.armaspersonajes.mvc;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.jarias.armaspersonajes.base.Arma;
import com.jarias.armaspersonajes.base.Personaje;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Model {

	private MongoClient cliente;
	private MongoDatabase db;
	
	public Model() {
		conectar();
	}
	
	public void conectar() {
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
			    CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		cliente = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		db = cliente.getDatabase("juego");
	}
	
	public void desconectar() {
		cliente.close();
	}
	
	public void guardarPersonaje(Personaje personaje) {
		Document documento = new Document()
				.append("nombre", personaje.getNombre())
				.append("descripcion", personaje.getDescripcion())
				.append("vida", personaje.getVida())
				.append("ataque", personaje.getAtaque())
				.append("armas", personaje.getArmas());
		db.getCollection("personajes").insertOne(documento);
	}
	
	public void eliminarPersonaje(Personaje personaje) {
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		coleccionPersonajes.deleteOne(eq("_id", personaje.getId()));
	}
	
	public void modificarPersonaje(Personaje personaje) {
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		coleccionPersonajes.replaceOne(eq("_id", personaje.getId()), personaje);
	}
	
	public List<Personaje> getPersonajes(){
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		return coleccionPersonajes.find().into(new ArrayList<Personaje>());
	}
	
	public void guardarArma(Arma arma) {
		Document documento = new Document()
				.append("nombre", arma.getNombre())
				.append("ataque", arma.getAtaque())
				.append("durabilidad", arma.getDurabilidad());
		db.getCollection("armas").insertOne(documento);
	}
	
	public void eliminarArma(Arma arma) {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		coleccionArmas.deleteOne(eq("_id", arma.getId()));
	}
	
	public void modificarArma(Arma arma) {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		coleccionArmas.replaceOne(eq("_id", arma.getId()), arma);
	}
	
	public List<Arma> getArmas() {
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		return coleccionArmas.find().into(new ArrayList<Arma>());
	}
	
	public List<Arma> getArmasLibres(){
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		return coleccionArmas.find(eq("personaje", null)).into(new ArrayList<Arma>());
	}
	
	public void iniciarListaArmas(String busqueda, DefaultListModel<Arma> mArmas) {
		if(busqueda.equals("")) {
			mArmas.removeAllElements();
			for(Arma arma : getArmas())
				mArmas.addElement(arma);
			return;
		}
	}
	
	public void busquedaArmas(String busqueda, DefaultListModel<Arma> mArmas) {
		iniciarListaArmas(busqueda, mArmas);
		MongoCollection<Arma> coleccionArmas = db.getCollection("armas", Arma.class);
		List<Arma> armas = coleccionArmas.find(regex("nombre", "^" + busqueda + ".*$")).into(new ArrayList<Arma>());
		mArmas.clear();
		for(Arma arma : armas) {
			mArmas.addElement(arma);
		}
	}
	
	public void busquedaArmasCompleja(String busqueda, DefaultListModel<Arma> mArmas) {
		iniciarListaArmas(busqueda, mArmas);
		FindIterable iterable = db.getCollection("armas").find(and(gte("ataque", Integer.parseInt(busqueda)), gte("durabilidad", Integer.parseInt(busqueda))));
		List<Arma> armas = new ArrayList<>();
		Arma arma = null;
		Iterator<Document> iter = iterable.iterator();
		while(iter.hasNext()) {
			Document documento = iter.next();
			arma = new Arma();
			arma.setId(documento.getObjectId("id"));
			arma.setNombre(documento.getString("nombre"));
			arma.setDurabilidad(documento.getInteger("durabilidad"));
			arma.setAtaque(documento.getInteger("ataque"));
			armas.add(arma);
		}
		mArmas.clear();
		for(Arma arma2 : armas) {
			mArmas.addElement(arma2);
		}
	}
	
	public void iniciarListaPersonajes(String busqueda, DefaultListModel<Personaje> mPersonajes) {
		if(busqueda.equals("")) {
			mPersonajes.removeAllElements();
			for(Personaje personaje : getPersonajes())
				mPersonajes.addElement(personaje);
			return;
		}
	}
	
	public void busquedaPersonajes(String busqueda, DefaultListModel<Personaje> mPersonajes) {
		iniciarListaPersonajes(busqueda, mPersonajes);
		MongoCollection<Personaje> coleccionPersonajes = db.getCollection("personajes", Personaje.class);
		List<Personaje> personajes = coleccionPersonajes.find(regex("nombre", "^" + busqueda + ".*$")).into(new ArrayList<Personaje>());
		mPersonajes.clear();
		for(Personaje personaje : personajes) {
			mPersonajes.addElement(personaje);
		}
	}
	
	public void busquedaPersonajesCompleja(String busqueda, DefaultListModel<Personaje> mPersonajes) {
		iniciarListaPersonajes(busqueda, mPersonajes);
		FindIterable iterable = db.getCollection("personajes").find(and(gte("ataque", Integer.parseInt(busqueda)), gte("vida", Integer.parseInt(busqueda))));
		List<Personaje> personajes = new ArrayList<>();
		Personaje personaje = null;
		Iterator<Document> iter = iterable.iterator();
		while(iter.hasNext()) {
			Document documento = iter.next();
			personaje = new Personaje();
			personaje.setId(documento.getObjectId("id"));
			personaje.setNombre(documento.getString("nombre"));
			personaje.setDescripcion(documento.getString("descripcion"));
			personaje.setAtaque(documento.getInteger("ataque"));
			personaje.setVida(documento.getInteger("vida"));
			personajes.add(personaje);
		}
		mPersonajes.clear();
		for(Personaje personaje2 : personajes) {
			mPersonajes.addElement(personaje2);
		}
	}
}
