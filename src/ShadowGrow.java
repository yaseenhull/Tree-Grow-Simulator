// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

import java.util.concurrent.*;
import java.util.*;

public class ShadowGrow implements Runnable{
	
	Land sunmap;
	Tree [] forest;
	//Tree [] clone;
	float min;
	float max; 
	ForestPanel fp;
	public Thread t;
	boolean suspended = false;
	static long startTime = 0;
	public static volatile int year = 0;
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	static final ForkJoinPool fjPool = new ForkJoinPool();
	static final ForkJoinPool fjPool2 = new ForkJoinPool();
	
	static void sim(int lo, int hi,Land land,Tree[] arr, float min, float max){
		Simulation one = new Simulation(0,arr.length,land,arr, min, max);
		
	    fjPool.invoke(one);
	}
	static void sim2(int lo, int hi,Land land,Tree[] arr, float min, float max){
		Simulation2 one = new Simulation2(0,arr.length,land,arr, min, max);
		
	    fjPool2.invoke(one);
	}
	
	ShadowGrow(Land land,Tree[] trees, float mn, float mx, ForestPanel fp ){
	//ShadowGrow(Tree[] trees, ForestPanel fp){
		sunmap= land;
		forest = trees;
		//clone = copy;
		min = mn;
		max = mx;
		fp =fp;
	}

	public int getYear() {
		return year;
	}
	  void suspend() {
		
		suspended = true;
		//TreeGrow.done = true;
		
		
	      
	   }
	   
	   synchronized void resume() {
		
			suspended = false;
			//TreeGrow.done = false;
			notify();
	
	    
	   }
	   
	  public void start () {
		      
		      if (t == null) {
		         t = new Thread (this);
		         t.start ();
		      }
	 }
	
	public void run() {
		
		//while(TreeGrow.done == false) {
		float time;
			try {
				for(int i = 0; i<10000; i++) { // years = 10000/10
		            //allowPause();
					tick();
		            sim(0, forest.length,sunmap,forest,  min, max);
		            sim2(0, forest.length,sunmap,forest,  min, max);
		            
					max = min;
					min = min - 2.0f;

					if((i+1)%10==0) {
						//System.out.println(forest[1].getExt());
						sunmap.resetShade();
						min = 18.0f;
		 			    max = 20.0f;
		 			    time = tock();
						//System.out.println(time);
		 			    year++;
		 			    TreeGrow.counter.setText(String.valueOf(year));
		 			    Thread.sleep(30);
						 synchronized(this) {
				               while(suspended) {
				                  wait();
				               }
				            }
		 			    	
					}
					
					if(i==9999) {
						TreeGrow.done = true;
						interrupt();
					}
							
					//sleep();
						
					
		         }
			} catch (InterruptedException e) {
				//t.interrupt();
				e.printStackTrace();
			};
		//}
		
	}

	public void interrupt() {
		// TODO Auto-generated method stub
		t.interrupt();
	}
	
	public boolean isAlive() {
		return t.isAlive();
	}
}
