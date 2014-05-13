package controleur;

import java.util.ArrayList;

import model.Noeud;
import model.Pos;

public class Control {

	//experience values of the positions on chess board
	final static int valueTable[][] = { { 100, -5, 10, 5, 5, 10, -5, 100 },
			{ -5, -45, 1, 1, 1, 1, -45, -5 }, { 10, 1, 3, 2, 2, 3, 1, 10 },
			{ 5, 1, 2, 1, 1, 2, 1, 5 }, { 5, 1, 2, 1, 1, 2, 1, 5 },
			{ 10, 1, 3, 2, 2, 3, 1, 10 }, { -5, -45, 1, 1, 1, 1, -45, -5 },
			{ 100, -5, 10, 5, 5, 10, -5, 100 } };

	//evaluation method
	public static int evaluate(Noeud<int[][]> n, int color) {
		int p1 = 3;// coefficients
		int p2 = 7;

		// final period of the game
		if (getSpaceNb(n) < 6) // depends on the research depth
		{
			return getChessNb(n, color)-getChessNb(n, -color); //count the chess nb
		}

		// position value 
		int positionVal = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				positionVal += n.getM_contenu()[i][j] * valueTable[i][j];
			}
		}

		// mobility value
		int mobilityVal = getMobility(n, color) - getMobility(n, -color);
		
		// calculate
		return (color * p1 * positionVal + p2 * mobilityVal);
	}

	//get the potential values in the next positions
	public static int getMobility(Noeud<int[][]> n, int color) {
		ArrayList<Pos> arrayPos = getLegalPositions(n, color);
		int result = 0;
		for (int i = 0; i < arrayPos.size(); i++) {
			Pos p = arrayPos.get(i);
			result += valueTable[p.x][p.y];
		}
		return result;
	}

	public static int getChessNb(Noeud<int[][]> n, int color) {
		int[][] board = n.getM_contenu();
		int result = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == color) {
					result++;
				}
			}
		}
		return result;
	}

	public static int getSpaceNb(Noeud<int[][]> n) {
		int result = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (n.getM_contenu()[i][j] == 0) {
					result++;
				}
			}
		}
		return result;
	}

	//get all the positions in the next step
	public static ArrayList<Pos> getLegalPositions(Noeud<int[][]> n, int color) {
		ArrayList<Pos> ps = new ArrayList<Pos>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Pos p = new Pos(i, j);
				if (isLegalPos(n, p, color)) {
					ps.add(p);
				}
			}
		}
		return ps;
	}

	// put a chess in a position and return the result board 
	public static Noeud<int[][]> putChess(Noeud<int[][]> noeud, Pos p, int color) {

		int val = 0; // value of the next position

		Noeud<int[][]> result = new Noeud<int[][]>(); // result Noeud after
														// calcul
		// copy
		copyNoeud(noeud, result);

		if (!Control.isLegalPos(result, p, color)) {
			return result;
		}

		// put the chess
		result.getM_contenu()[p.x][p.y] = color;

		// reverse the other chess
		for (int i = 0; i < 8; i++) {
			if (Control.isLegalDirection(result, p, color, i)) {
				int j = 1;
				switch (i) {
				case 0:
					val = result.getM_contenu()[p.x - j][p.y];
					// value of the next position

					while (val + color == 0) {
						result.getM_contenu()[p.x - j][p.y] = color;
						j++;
						val = result.getM_contenu()[p.x - j][p.y];
					}
					break;
				case 1:
					val = result.getM_contenu()[p.x + j][p.y];

					while (val + color == 0) {
						result.getM_contenu()[p.x + j][p.y] = color;
						j++;
						val = result.getM_contenu()[p.x + j][p.y];
					}
					break;
				case 2:
					val = result.getM_contenu()[p.x][p.y - j];

					while (val + color == 0) {
						result.getM_contenu()[p.x][p.y - j] = color;
						j++;
						val = result.getM_contenu()[p.x][p.y - j];
					}
					break;
				case 3:
					val = result.getM_contenu()[p.x][p.y + j];

					while (val + color == 0) {
						result.getM_contenu()[p.x][p.y + j] = color;
						j++;
						val = result.getM_contenu()[p.x][p.y + j];
					}
					break;
				case 4:
					val = result.getM_contenu()[p.x - j][p.y + j];

					while (val + color == 0) {
						result.getM_contenu()[p.x - j][p.y + j] = color;
						j++;
						val = result.getM_contenu()[p.x - j][p.y + j];
					}
					break;
				case 5:
					val = result.getM_contenu()[p.x + j][p.y + j];

					while (val + color == 0) {
						result.getM_contenu()[p.x + j][p.y + j] = color;
						j++;
						val = result.getM_contenu()[p.x + j][p.y + j];
					}
					break;
				case 6:
					val = result.getM_contenu()[p.x + j][p.y - j];

					while (val + color == 0) {
						result.getM_contenu()[p.x + j][p.y - j] = color;
						j++;
						val = result.getM_contenu()[p.x + j][p.y - j];
					}
					break;
				case 7:
					val = result.getM_contenu()[p.x - j][p.y - j];

					while (val + color == 0) {
						result.getM_contenu()[p.x - j][p.y - j] = color;
						j++;
						val = result.getM_contenu()[p.x - j][p.y - j];
					}
					break;
				default:
					break;
				}
			}
		}

		return result;
	}

	public static boolean isLegalPos(Noeud<int[][]> n, Pos p, int color) {
		// out of board
		if (p.x < 0 || p.x > 7 || p.y < 0 || p.y > 7) {
			return false;
		}
		// position is occupied
		if (n.getM_contenu()[p.x][p.y] != 0) {
			return false;
		}
		// directions are legal
		for (int i = 0; i < 8; i++) {
			if (isLegalDirection(n, p, color, i)) {
				return true;
			}
		}
		return false;
	}

	//if it can reverse chess in a direction 
	public static boolean isLegalDirection(Noeud<int[][]> n, Pos p, int color,
			int direct) {
		int i = 2;

		switch (direct) {
		case 0:// UP
			if (p.x <= 1) {
				return false;
			}
			if (n.getM_contenu()[p.x - 1][p.y] + color != 0) {
				return false;
			}
			i = 2;
			while (p.x - i >= 0) {
				if (n.getM_contenu()[p.x - i][p.y] == color) {
					return true;
				} else if (n.getM_contenu()[p.x - i][p.y] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 1:// DOWN
			if (p.x >= 6) {
				return false;
			}
			if (n.getM_contenu()[p.x + 1][p.y] + color != 0) {
				return false;
			}
			i = 2;
			while (p.x + i <= 7) {
				if (n.getM_contenu()[p.x + i][p.y] == color) {
					return true;
				} else if (n.getM_contenu()[p.x + i][p.y] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 2:// left
			if (p.y <= 1) {
				return false;
			}
			if (n.getM_contenu()[p.x][p.y - 1] + color != 0) {
				return false;
			}
			i = 2;
			while (p.y - i >= 0) {
				if (n.getM_contenu()[p.x][p.y - i] == color) {
					return true;
				} else if (n.getM_contenu()[p.x][p.y - i] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 3:// right
			if (p.y >= 6) {
				return false;
			}
			if (n.getM_contenu()[p.x][p.y + 1] + color != 0) {
				return false;
			}
			i = 2;
			while (p.y + i <= 7) {
				if (n.getM_contenu()[p.x][p.y + i] == color) {
					return true;
				} else if (n.getM_contenu()[p.x][p.y + i] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 4:// up_right
			if (p.x <= 1 || p.y >= 6) {
				return false;
			}
			if (n.getM_contenu()[p.x - 1][p.y + 1] + color != 0) {
				return false;
			}
			while (p.x - i >= 0 && p.y + i <= 7) {
				if (n.getM_contenu()[p.x - i][p.y + i] == color) {
					return true;
				} else if (n.getM_contenu()[p.x - i][p.y + i] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 5:// down_right
			if (p.x >= 6 || p.y >= 6) {
				return false;
			}
			if (n.getM_contenu()[p.x + 1][p.y + 1] + color != 0) {
				return false;
			}
			while (p.x + i <= 7 && p.y + i <= 7) {
				if (n.getM_contenu()[p.x + i][p.y + i] == color) {
					return true;
				} else if (n.getM_contenu()[p.x + i][p.y + i] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 6:// down_left
			if (p.x >= 6 || p.y <= 1) {
				return false;
			}
			if (n.getM_contenu()[p.x + 1][p.y - 1] + color != 0) {
				return false;
			}
			while (p.x + i <= 7 && p.y - i >= 0) {
				if (n.getM_contenu()[p.x + i][p.y - i] == color) {
					return true;
				} else if (n.getM_contenu()[p.x + i][p.y - i] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
			break;
		case 7:// up_left
			if (p.x <= 1 || p.y <= 1) {
				return false;
			}
			if (n.getM_contenu()[p.x - 1][p.y - 1] + color != 0) {
				return false;
			}
			while (p.x - i >= 0 && p.y - i >= 0) {
				if (n.getM_contenu()[p.x - i][p.y - i] == color) {
					return true;
				} else if (n.getM_contenu()[p.x - i][p.y - i] + color == 0) {
					i++;
					continue;
				} else {
					return false;
				}
			}
		default:
			return false;
		}
		return false;
	}

	// get the best position after a fixed search depth
	public static Pos calculate(Noeud<int[][]> root, int deepth, int color) {
		Pos p = new Pos();
		int score = AlphaBeta(root, deepth, color, false, p, Integer.MIN_VALUE, Integer.MAX_VALUE);
	/*	if (score == Integer.MAX_VALUE) {
			p.x = 10;	//win
		} else if (score == Integer.MIN_VALUE) {
			p.x = -10;	//loose
		} else if (score == Integer.MAX_VALUE - 1) {
			p.x = -1;	//draw
		}*/
		return p;
	}

	public static int AlphaBeta(Noeud<int[][]> n, int deepth, int color,
			boolean pass, Pos choosePos,int alpha,int beta) {

		int best_val = Integer.MIN_VALUE;
		int tempval = 0;
		int index = 0;

		if (deepth <= 0) {				//final node of the search
			return evaluate(n, color);
		}

		Noeud<int[][]> tempNoeud = new Noeud<int[][]>();

		ArrayList<Pos> ps = getLegalPositions(n, color);
		int nb = ps.size();

		if (nb == 0) {
			if (pass) { // game over
				choosePos.x = -1;
				if (getChessNb(n, color) > getChessNb(n, -color)) {
					return Integer.MAX_VALUE;
				} else if (getChessNb(n, color) < getChessNb(n, -color)) {
					return Integer.MIN_VALUE;
				} else
					return Integer.MAX_VALUE - 1;
			} else { // pass
				return -AlphaBeta(n, deepth, -color, true, choosePos,-beta, -alpha);
			}
		}

		// try every position
		for (int i = 0; i < nb; i++) {
			tempNoeud = putChess(n, ps.get(i), color);
			tempval = -AlphaBeta(tempNoeud, deepth - 1, -color, false, choosePos,-beta, -alpha);
			if(tempval >= beta)
			{
				return tempval;
			}
			if (tempval > best_val) {
				best_val = tempval;		//update the best value
				index = i;
				if(tempval > alpha)		//refresh the value of alpha
				{
					alpha = tempval;
				}
			}
		}

		choosePos.x = ps.get(index).x;
		choosePos.y = ps.get(index).y;	//also return the best position
		
		return best_val;

		/*
		 * if (deepth % 2 == 0) { val = Integer.MIN_VALUE; for (int i = 0; i <
		 * nb; i++) { choosePos.x = ps.get(i).x; choosePos.y = ps.get(i).y;
		 * tempNoeud = putChess(n, choosePos, color); tempval =
		 * MinMax(tempNoeud, deepth - 1, -color, false, choosePos); if (tempval
		 * > val) { val = tempval; index = i; } } } else { val =
		 * Integer.MAX_VALUE; for (int i = 0; i < nb; i++) { choosePos.x =
		 * ps.get(i).x; choosePos.y = ps.get(i).y; tempNoeud = putChess(n,
		 * choosePos, color); tempval = MinMax(tempNoeud, deepth - 1, -color,
		 * false, choosePos); if (tempval < val) { val = tempval; index = i; }
		 * 
		 * } }
		 */

	}

	public static void copyNoeud(Noeud<int[][]> src, Noeud<int[][]> target) {
		int c[][] = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 } };
		target.setM_contenu(c);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int it = src.getM_contenu()[i][j];
				target.getM_contenu()[i][j] = it;
			}
		}
		return;
	}

	public static void showChessBoard(Noeud<int[][]> n) {
		System.out.println("Show chess board:");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) { 
				if (n.getM_contenu()[i][j] == 1) {
					System.out.print("O ");
				} else if (n.getM_contenu()[i][j] == -1) {
					System.out.print("X ");
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}
	}
	
}












