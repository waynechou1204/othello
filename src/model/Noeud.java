package model;

import java.util.ArrayList;

public class Noeud<T>{
	private T m_contenu;
	private Noeud<T> m_pere;
	private ArrayList<Noeud<T>> m_fils;	
	
	public Noeud()
	{
		m_contenu = null;
		m_pere = null;
		m_fils = new ArrayList<Noeud<T>>();
	}
	
	public Noeud(T item)
	{
		m_contenu = item;
		m_pere = null;
		m_fils = new ArrayList<Noeud<T>>();
	}

	public T getM_contenu() {
		return m_contenu;
	}

	public void setM_contenu(T m_contenu) {
		this.m_contenu = m_contenu;
	}

	public Noeud<T> getM_pere() {
		return m_pere;
	}

	public void setM_pere(Noeud<T> m_pere) {
		this.m_pere = m_pere;
	}

	public ArrayList<Noeud<T>> getM_fils() {
		return m_fils;
	}

	public void setM_fils(ArrayList<Noeud<T>> m_fils) {
		this.m_fils = m_fils;
	}
	
} 
