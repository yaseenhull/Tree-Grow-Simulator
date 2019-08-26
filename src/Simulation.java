// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

import java.util.concurrent.*;

public class Simulation extends RecursiveAction {
	
	int low;
	int high;
	Land sunmap;
	Tree [] forest;
	float min;
	float max; 
	static final int SEQUENTIAL_CUTOFF=250000;	
	
	
	Simulation(int lo, int hi,Land land,Tree[] trees, float mn, float mx ){
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
						
						
						
				
					forest[i].sungrow(sunmap);// obtains average/1000 + ext;
			
						
						
							
					}
		      }

		      
		}
		 else {
			  Simulation left = new Simulation(low,(high+low)/2,sunmap,forest, min, max);
			  Simulation right = new Simulation((high+low)/2,high,sunmap,forest, min, max);
			  
			  // order of next 4 lines
			  // essential why?
			  left.fork();
			  right.compute();
			  left.join();
			  
			 
			 
			 
			 
		  }
	
		
	}

}
