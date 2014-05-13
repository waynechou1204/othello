package controleur;

import java.io.IOException;

import vue.Echiquier;
  
import model.ArbreNaire;
import model.Noeud;
import model.Pos;

public class Othello_main {

	/**
	 * @param args
	 */
	static int W = 1; // white
	static int B = -1; // black

	//directions
	static int UP = 0;
	static int DOWN = 1;
	static int LEFT = 2;
	static int RIGHT = 3;
	static int UP_RT = 4;
	static int DOWN_RT = 5;
	static int DOWN_LT = 6;
	static int UP_LT = 7;
	
	

	static int g_chessBoard[][] = { 
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, W, B, 0, 0, 0 }, 
			{ 0, 0, 0, B, W, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 } };

	public static void main(String[] args) throws NumberFormatException,
			IOException, InterruptedException {

		ArbreNaire<int[][]> arbre = new ArbreNaire<int[][]>(g_chessBoard); // initialize
		System.out.println("Original chess board");
        Echiquier othello = new Echiquier ("Othello", arbre.getNoeud());
		
		Noeud<int[][]> res = arbre.getNoeud();
		int turn = B;
		int end = 0;
		Pos p = new Pos();

		while (end != 2) {	//if end = 2, that means the two opposition have no chess to put, game over
			if (turn == B) {
				if (Control.getLegalPositions(res, B).size() != 0) {
					p = Control.calculate(res, 6, turn);

				/*	if(p.x == -10)
					{
						System.out.println("You Win.");
						return;
					}
					else if(p.x == 10)
					{
						System.out.println("You Loose!");
						return;
					}
					else if(p.x == 10)
					{
						System.out.println("Draw");
						return;
					}
					*/	
					res = Control.putChess(res, p, turn);
					othello.setRes(res);
					Thread.sleep(1000);
					othello.repaint();
					
					end = 0;
				} else {
					end++;
				}
				turn = -turn;

			} else {
				if (Control.getLegalPositions(res, W).size() != 0) {

					othello.setx(p.x);
					othello.sety(p.y);
					while(true){
						if(othello.getx() != p.x || othello.gety() != p.y)
						{
							p.x = othello.getx();
							p.y = othello.gety();
							if(Control.isLegalPos(res, p, W))
							{
								break;
							}
						}
							
					}
					
					res = Control.putChess(res, p, turn);
					othello.setRes(res);
					othello.repaint();
					
					end = 0;
				}else {
					end++;
				}
				turn = -turn;
			}
		}
		
		int result = Control.getChessNb(res, W)-Control.getChessNb(res, B);
		if (result > 0)
		{
			System.out.print("white win");
		}
		else if(result < 0)
		{
			System.out.print("black win");
		}
		else
		{
			System.out.print("Draw");
		}
	}

}
