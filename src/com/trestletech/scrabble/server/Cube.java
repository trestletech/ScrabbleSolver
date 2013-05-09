package com.trestletech.scrabble.server;

import java.util.List;
import java.util.Vector;

/**
 * Represents a Boggle cube. It will have a character (or two) value and will have references to other cubes to which it connects.
 * @author jalle6
 *
 */
public class Cube {
	List<Cube> connections;
	String value;
	
	public Cube() {
		connections = new Vector<Cube>();
	}
	
	public Cube(List<Cube> connections, String value) {
		super();
		this.connections = connections;
		this.value = value;
	}

	public List<Cube> getConnections() {
		return connections;
	}

	public void setConnections(List<Cube> connections) {
		this.connections = connections;
	}

	public void addConnection(Cube connection){
		if (connection == null){
			return;
		}
		
		connections.add(connection);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
}
