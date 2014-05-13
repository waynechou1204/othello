package controleur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.ArbreNaire;
import model.Noeud;

public class Main {

	/**
	 * @param <T>
	 * @param args
	 */
	public static Noeud<Integer> RecurBuild(int prof, int large,
			Noeud<Integer> root) {
		if (prof == 0) {
			return null;
		}

		Integer i = root.getM_contenu();
		for (int j = 0; j < large; j++) {
			root.getM_fils().add(new Noeud<Integer>(i * 10 + j));
			root.getM_fils().get(j).setM_pere(root);
			RecurBuild(prof - 1, large, root.getM_fils().get(j));
		}
		return root;
	}

	public static ArbreNaire<Integer> BuildArbre(int p, int l) {
		ArbreNaire<Integer> arbre = new ArbreNaire<Integer>();

		Noeud<Integer> racine = new Noeud<Integer>(1);

		racine = RecurBuild(p, l, racine);
		arbre.setM_racine(racine);
		arbre.setM_noeud(racine);
		return arbre;
	}

	public static void DepthSearch(ArbreNaire<Integer> a) {
		System.out.println(a.getItem());
		int t = a.getNbFils();
		for (int i = 0; i < t; i++) {
			a.goToFils(i);
			DepthSearch(a);
			a.goToPere();
		}
	}

	public static int MinMax(ArbreNaire<Integer> a, int profondeur) {
		int val = 0;
		int nb = a.getNbFils();
		ArrayList<Integer> fs = new ArrayList<Integer>();

		if (a.isNoeudFeuille()) {
			return (Integer) a.getItem();
		}

		if (profondeur % 2 == 0) {
			val = Integer.MIN_VALUE;
			for (int i = 0; i < nb; i++) {
				a.goToFils(i);
				fs.add(MinMax(a, profondeur + 1));
				a.goToPere();
			}
			val = Max(fs);
		} else {
			val = Integer.MAX_VALUE;
			for (int i = 0; i < nb; i++) {
				a.goToFils(i);
				fs.add(MinMax(a, profondeur + 1));
				a.goToPere();
			}
			val = Min(fs);
		}
		return val;
	}

	public static int Max(ArrayList<Integer> in) {
		int s = in.size();
		int val = in.get(0);
		for (int i = 1; i < s; i++) {
			if (val < in.get(i)) {
				val = in.get(i);
			}
		}
		return val;
	}
	
	public static int Min(ArrayList<Integer> in) {
		int s = in.size();
		int val = in.get(0);
		for (int i = 1; i < s; i++) {
			if (val > in.get(i)) {
				val = in.get(i);
			}
		}
		return val;
	}
	
	public static void setNb(ArbreNaire<Integer> a, Noeud<Integer> point){
		a.setM_noeud(point);
		if(a.isNoeudFeuille()){
			System.out.print("Saisissez un chiffre pour cette noeudfeuille : ");
			BufferedReader temp = new BufferedReader(new InputStreamReader(System.in));
			try{
				int t = new Integer(temp.readLine());
				a.getNoeud().setM_contenu(t);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		else{
			int t = a.getNbFils();
			for(int i=0;i<t;i++){
				a.goToFils(i);
				setNb(a,a.getNoeud());
				a.goToPere();
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArbreNaire<Integer> a = BuildArbre(2, 3);
		setNb(a,a.getM_racine());
		System.out.println("Le resultat de DFS : ");
		DepthSearch(a);
		System.out.println("Le resultat de MinMax : ");
		System.out.println(MinMax(a, 0));
		
	}

}
