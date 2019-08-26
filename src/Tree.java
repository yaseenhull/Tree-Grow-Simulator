// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

public class Tree{
	
private
	int xpos;	// x-coordinate of center of tree canopy
	int ypos;	// y-coorindate of center of tree canopy
	float ext;	// extent of canopy out in vertical and horizontal from center
	
	static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
public	
	Tree(int x, int y, float e){
		xpos=x; ypos=y; ext=e;
	}
	
	 synchronized int getX() {
		return xpos;
	}
	
	synchronized int getY() {
		return ypos;
	}
	
	synchronized float getExt() {
		return ext;
	}
	
	void setExt(float e) {
		ext = e;
	}

	// return the average sunlight for the cells covered by the tree
	float sunexposure(Land land){
		// to do 
		
			//get extent values
			int dim = (int)ext*2+1;
			float avg = 0;
			float sum = 0;
			int count = 0;
			
			
			//if(ext<=1.0) {
				
			//}
			//else {
				for(int i = 0; i<dim;i++) {
					for(int j=0; j<dim; j++) {
						
						if((xpos-dim+i>=land.getDimX())||(ypos-dim+j>=land.getDimX())||xpos-dim+i<0||ypos-dim+j<0 && ext<1) {
							sum+= land.getShade(xpos, ypos);
							count++;
						}
						
						else if((xpos-dim+i>=land.getDimX())||(ypos-dim+j>=land.getDimX())||xpos-dim+i<0||ypos-dim+j<0) {
							continue;
						}
						
						else {
							sum+= land.getShade(xpos-dim+i, ypos-dim+j);
							count++;
						}
						
					}
				}
		//	}
			
			
			avg = sum/count;
			return avg; // not correct
		
		
	}
	
	// is the tree extent within the provided range [minr, maxr)
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}
	
	// grow a tree according to its sun exposure
	 synchronized void sungrow(Land land) {
		// to do
		float ex  = ext + sunexposure(land)/growfactor;
		setExt(ex);
		//return ex;
	}
}