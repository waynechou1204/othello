package vue;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.Noeud;

public class Echiquier extends JFrame{
	
	private Noeud<int[][]> res;
	private Epanel panel = new Epanel(40,8);
	private int x = 0;
    private int y = 0;
	
	public Echiquier(String title, Noeud<int[][]> res){
		super(title);
		
		this.res = res;
		Container contentPane = getContentPane();
		setJMenuBar(panel.getMenuBar());
		contentPane.add(panel);
		
		setSize(350,380);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setRes (Noeud<int[][]> res){
		this.res = res;
	}
	
	public Noeud<int[][]> getRes(){
		return res;
	}
	
	public int getx(){
		return x;
	}
	
	public void setx(int x){
		this.x = x;
	}
	
	public int gety(){
		return y;
	}
	
	public void sety(int y){
		this.y = y;
	}
	
	class Epanel extends JPanel{

		private int space = 20;
		private int grids = 8;
		private int radius = space/2;
		private int player = 1;
		
		private JMenuBar echiquierBar = new JMenuBar();
		private JMenu optMenu = new JMenu();
		private JMenuItem startMenuItem = new JMenuItem("start");
		private JMenuItem exitMenuItem = new JMenuItem("exit");
		
		public Epanel(int space, int grids){
			this.space = space;
			this.grids = grids;
			this.radius = space/2;
			
			startMenuItem.addActionListener(startHandler);
			exitMenuItem.addActionListener(exitHandler);
			addMouseListener(putChessHandler);
			setBackground(Color.LIGHT_GRAY);
			setSize(space*grids, space*grids);
			
			echiquierBar.add(optMenu);
			optMenu.add(startMenuItem);
			optMenu.add(exitMenuItem);
		}
		
		public JMenuBar getMenuBar(){
			return echiquierBar;
		}
		
		private ActionListener startHandler = new ActionListener(){
			public void actionPerformed (ActionEvent e){
				for (int i=0;i<grids;i++){
					for(int j=0;j<grids;j++){
						res.getM_contenu()[i][j] = 0;
					}
				}
				repaint();
			}
		};
		
		private ActionListener exitHandler = new ActionListener(){
			public void actionPerformed (ActionEvent e){
				System.exit(0);
			}
		};
		
		private MouseListener putChessHandler = new MouseAdapter(){
			public void mouseClicked (MouseEvent e){
				
				int m = e.getY();
				int n = e.getX();
				
				if(m<=grids*space && m>=0 && n<=space*grids && n>=0){
					if(res.getM_contenu()[round(m)][round(n)] == 0 ){
//						res.getM_contenu()[round(m)][round(n)] = player;
						x = round(m);
						y = round(n);
						repaint();
					}
				}
			}
		};
		
		public int round(float a){
			float f = a/space;
			return (int)f;
		}
		
		private void drawChess (Graphics g, int x, int y, int color){
			g.setColor(color == 1?Color.WHITE:Color.BLACK);
			g.fillOval(y*space, x*space, radius*2, radius*2);
		}
		
		private void drawGrids (Graphics g){
			g.setColor(Color.DARK_GRAY);
			for(int i=0;i<=grids;i++){
				g.drawLine(0, i*space, grids*space, i*space);
				g.drawLine(i*space, 0, i*space, grids*space);
			}
		}
		
		public void paintComponent (Graphics g){
			super.paintComponent(g);
			drawGrids(g);
			for(int i=0; i<grids; i++){
				for(int j=0;j<grids;j++){
					if(res.getM_contenu()[i][j]!=0)
						drawChess(g,i,j,res.getM_contenu()[i][j]);
				}
			}
				
		}

	}

}
