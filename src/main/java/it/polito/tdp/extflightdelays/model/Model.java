package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	//PRIMO TENTATIVO
	
	/*private ExtFlightDelaysDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		
	}
	*/
	public void creaGrafo() {
		this.grafo = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici = new ArrayList<String>(dao.loadAllStates());
		Graphs.addAllVertices(grafo, vertici);
		
		for(Arco a: dao.listArchi()) {
			Graphs.addEdgeWithVertices(grafo, a.getStato1(), a.getStato2(), a.getPeso());
		}
	}
/*
	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<String> statiConnessi(String stato){
		List<String> connessi = new ArrayList<>();
		for(Arco a: dao.listArchi()) {
			if(a.getStato1().compareTo(stato)==0) {
				connessi.add(a.getStato2()+" "+a.getPeso()+"\n");
			}
		}
		return connessi;
	}*/
	
	//SECONDO TENTATIVO	
	private ExtFlightDelaysDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	private List<Arco> archi;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();

	}

	public void creaGrafo2() {
		this.grafo = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.vertici= new ArrayList<String>(dao.getVertici());
		Graphs.addAllVertices(grafo, vertici);
		this.archi = new ArrayList<Arco>(dao.getArchi());
		for (Arco a : archi) {
			Graphs.addEdgeWithVertices(grafo, a.getStato1(), a.getStato2(), a.getPeso());
		}
	}
	
	public List<String> getVertici() {
		return vertici;
	}
	
	public String statiCollegati(String stato) {
		String elenco="";
		List<Arco> collegati= new ArrayList<>();
		for (Arco a : archi) {
			if(a.getStato1().equals(stato)) {
				collegati.add(a);
			}
		}
		Collections.sort(collegati);
		for (Arco arco : collegati) {
			elenco+= arco.getStato2()+" "+arco.getPeso()+"\n";
		}
		
		return elenco;
	}
}
