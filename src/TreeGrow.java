// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class TreeGrow {
	static long startTime = 0;
	static int frameX;
	static int frameY;
	static ForestPanel fp;
	static ShadowGrow ps1;
	public volatile static boolean done = true;
    static boolean paused = false;
	static int count = 0;
	public volatile static JTextField counter;
	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	static final ForkJoinPool fjPool = new ForkJoinPool();
	
	static void sim(int lo, int hi,Land land,Tree[] arr, float min, float max){
		Simulation one = new Simulation(0,arr.length,land,arr, min, max);
		
	    fjPool.invoke(one);
	}
	
	
	
	
	public static void setupGUI(int frameX,int frameY,Tree [] trees, Tree [] clone, Land sunmap) {
		Dimension fsize = new Dimension(800, 800);
		// Frame init and dimensions
    	JFrame frame = new JFrame("Photosynthesis"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setPreferredSize(fsize);
    	frame.setSize(800, 800);
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setPreferredSize(fsize);
      	
      	
      	
		fp = new ForestPanel(trees);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		JScrollPane scrollFrame = new JScrollPane(fp);
		fp.setAutoscrolls(true);
		scrollFrame.setPreferredSize(fsize);
	    g.add(scrollFrame);
	    
	    frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
  
        Thread fpt = new Thread(fp);
        fpt.start();
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        
        frame.add(p);
      
        Thread ps = new Thread(fp);
    	float min = 18.0f;
	    float max = 20.0f;
      	
      	
      	
     
	    JButton Resetbtn = new JButton("Reset");
	    JButton Playbtn = new JButton("Play");
	    JButton Pausebtn = new JButton("Pause");
		JButton Quitbtn = new JButton("End");
		counter = new JTextField(10);
		
		p.add(Resetbtn);
		p.add(Playbtn);
		p.add(Pausebtn);
		p.add(Quitbtn);
       	p.add(counter);
       	
        //	Playbtn.setAlignmentY(Component.LEFT_ALIGNMENT);
        	
     
       	//Pausebtn.setAlignmentY(Component.LEFT_ALIGNMENT);
       	
       
       	//Pausebtn.setAlignmentY(Component.LEFT_ALIGNMENT);
       	ps1 =  new ShadowGrow(sunmap, trees, min, max, fp);
    	
      	Resetbtn.addActionListener(new ActionListener() {
      	
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// TODO Auto-generated method stub
					/**/
				
						for(int i = 0 ; i <trees.length;i++) {
							 trees[i].setExt(0.4f);
						}
						done = false;
						
						ps.start();
				
					
				/*	else if (ps1.isAlive() && ps.isAlive()) {
						ps1.interrupt();
						//ps.interrupt();
						for(int i = 0 ; i <trees.length;i++) {
							 trees[i].setExt(0.4f);
						}
						done = false;
						ps1 =  new ShadowGrow(sunmap, trees, min, max, fp);
						//ps1.start();
					}*/
			
				
					
					
					
					

				
			}
			
			
      		
      	});
      	
      
	    
      
       	Playbtn.addActionListener(new ActionListener() {

			@Override
 			public void actionPerformed(ActionEvent e) {
 				// TODO Auto-generated method stub
				//done = true;
					
					if(done == false) {
						
					    
					    ps1.start();
					   
				
					}
					
					else if (done == true) {
						
						done = false;
						ps.interrupt();
						Thread ps2 = new Thread(fp);
						ps2.start();
						
						try {
							Thread.sleep(1000);
							ps1.resume();
							
							//ps.notify();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
	
 				}

       		
       	});
      	
      
      	Pausebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					//pause = true;
					done = true;
					paused = true;
			
					try {
						Thread.sleep(100);
						ps1.suspend();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
      		
      	});
      	
      	
      	Quitbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
      		
      	});
      	
      	
     	//count.setText(arg0);
    	
      	
       
	} 
	
	
		
	public static void main(String[] args) {
		SunData sundata = new SunData();
		
		// check that number of command line arguments is correct
		/*if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
			System.exit(0);
		}*/
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
				
		// read in forest and landscape information from file supplied as argument
		sundata.readData(input);
		System.out.println("Data loaded");
		
		frameX = sundata.sunmap.getDimX();
		frameY = sundata.sunmap.getDimY();
		setupGUI(frameX, frameY, sundata.trees, sundata.clone, sundata.sunmap);
		//done = true;
		// create and start simulation loop here as separate thread

      	
		
	}
}