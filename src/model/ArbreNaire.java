package model;

public class ArbreNaire<typeItem> {

	private Noeud<typeItem> m_racine;
	private Noeud<typeItem> m_noeud;
	
	public ArbreNaire()
	{
		m_racine = null;
		m_noeud = null;
	}
	
	public ArbreNaire(typeItem racine)
	{
		m_racine = new Noeud<typeItem>(racine);
		m_noeud = m_racine;
	}
	
	public void initRacine(typeItem item)
	{
		m_racine.setM_contenu(item);
	}
	
	public void addFils(typeItem item)
	{
		m_noeud.getM_fils().add(new Noeud<typeItem>(item));
	}
	
	public typeItem getItem()
	{
		return (typeItem) m_noeud.getM_contenu();
	}
	
	public boolean isRacine()
	{
		return (m_noeud == m_racine);
	}

	public boolean isNoeudFeuille()
	{
		return (m_noeud.getM_fils().isEmpty());
	}

	public int getNbFils()
	{
		return m_noeud.getM_fils().size();
	}

	public void goToRacine()
	{
		m_noeud = m_racine;
	}
	
	public void goToFils(int i)
	{
		m_noeud = m_noeud.getM_fils().get(i);
	}
	
	public void goToPere()
	{
		m_noeud = m_noeud.getM_pere();
	}
	
	public void suppressNoeud()
	{
		Noeud<typeItem> p = m_noeud.getM_pere();
		int i = p.getM_fils().indexOf(m_noeud);
		goToPere();
		m_noeud.getM_fils().remove(i);
	}
	
	public Noeud<typeItem> getNoeud()
	{
		return m_noeud;
	}
	
	public void setM_noeud(Noeud<typeItem> m_noeud) {
		this.m_noeud = m_noeud;
	}

	public Noeud<typeItem> getM_racine() {
		return m_racine;
	}

	public void setM_racine(Noeud<typeItem> m_racine) {
		this.m_racine = m_racine;
	}

}


