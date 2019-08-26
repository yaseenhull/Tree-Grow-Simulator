// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.*;


public class ForestPanel extends JPanel implements Runnable {
	Tree[] forest;	// trees to render
	List<Integer> rndorder; // permutation of tree indices so that rendering is less structured
	public volatile static boolean done = true;
	
	ForestPanel(Tree[] trees) {
		forest = trees;
		done = false;
	}
		
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		    
		// draw the forest in different canopy passes
		Random rnd = new Random(0); // providing the same seed gives trees consistent colouring

		// start from small trees of [0, 2) extent
		float minh = 0.0f;
		float maxh = 2.0f;
		for(int layer = 0; layer < 10; layer++) {
			for(int t = 0; t < forest.length; t++){ //for multiple trees process shadow and new extent using threads
				int rt = rndorder.get(t); 
				
				if(forest[rt].getExt() >= minh && forest[rt].getExt() < maxh) { // only render trees in current band
					// draw trees as rectangles centered on getX, getY with random greenish colour
					g.setColor(new Color(rnd.nextInt(100), 150+rnd.nextInt(100), rnd.nextInt(100)));
					g.fillRect(forest[rt].getY() - (int) forest[rt].getExt(), forest[rt].getX() - (int) forest[rt].getExt(),
						   2*(int) forest[rt].getExt()+1,2*(int) forest[rt].getExt()+1);
			;	
				}
				 //g.setColor(Color.black);
				 //g.fillRect(forest[rt].getY(), forest[rt].getX(), 1, 1); // draw the trunk
		
			}
			minh = maxh;  // next band of trees
			maxh +=2.0f;
		}	
	}
	
	public void run() {
			
		// reordering so that trees are rendered in a more random fashion
		rndorder = new ArrayList<Integer>();
		for(int l = 0; l < forest.length; l++)
			rndorder.add(l);  
		java.util.Collections.shuffle(rndorder);
		
		// render loop
		while(TreeGrow.done == false) {

			//System.out.println("paint");
			//repaint();
			paintComponent(getGraphics());
			try {
				Thread.sleep(10); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			};
			
		}
	}

}