// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

import java.util.concurrent.RecursiveAction;

public class Simulation2 extends RecursiveAction {
	
	int low;
	int high;
	Land sunmap;
	Tree [] forest;
	float min;
	float max; 
	static final int SEQUENTIAL_CUTOFF=250000;	
	
	
	Simulation2(int lo, int hi,Land land,Tree[] trees, float mn, float mx ){
		//ShadowGrow(Tree[] trees, ForestPanel fp){
			low = lo;
			high = hi;
			sunmap= land;
			forest = trees;
			min = mn;
			max = mx;
		
			
		}


	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		
		if((high-low) < SEQUENTIAL_CUTOFF) {
			  
		      for(int i=low; i < high; i++) {
		    	  if(forest[i].inrange(min,max)) {
						
						
						
			
					sunmap.shadow(forest[i]);
				
						
						
							
					}
		      }

		      
		}
		 else {
			  Simulation2 left = new Simulation2(low,(high+low)/2,sunmap,forest, min, max);
			  Simulation2 right = new Simulation2((high+low)/2,high,sunmap,forest, min, max);
			  
			  // order of next 4 lines
			  // essential why?
			  left.fork();
			  right.compute();
			  left.join();
			  
			 
			 
			 
			 
		  }
	
		
	}

}
