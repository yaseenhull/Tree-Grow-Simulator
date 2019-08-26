// Concurrent Tree Growth Sim
// 03 October 2018
// Yaseen Hull

public class Land{
	
	// to do
	// sun exposure data here
	int Dx;
	int Dy;
	float [][] sunData;
	float [][] origin;
	static float shadefraction = 0.1f; // only this fraction of light is transmitted by a tree

	Land(int dx, int dy) {
		// to do
		Dx = dx;
		Dy = dy;
		sunData = new float[Dx][Dy];
		origin = new float[Dx][Dy];
	}

	 int getDimX() {
		// to do
		return Dx; // incorrect value
	}
	
	 int getDimY() {
		// to do
		return Dy; // incorrect value
	}
	
	int getLen() {
		return sunData.length;
	}
	

	// Reset the shaded landscape to the same as the initial sun exposed landscape
	// Needs to be done after each growth pass of the simulator
	void resetShade() {//after all layers processed and sunmap values now changed reset to original amount.
		// to do
		/*for (int i = 0; i< Dx ; i++) {
			for(int j = 0; j< Dy ; j++) {
				sunData[i][j] = 0;
			}
		}*/
		
		sunData = origin;
		
		
	}
	
	 float getFull(int x, int y) { // return value corresponding to x and y coordinate
		float value = origin[x][y];
		return value; // incorrect value
	}
	
	 void setFull(int x, int y, float val) {// store data in data structure, maybe hashmap
		// to do 
		sunData[x][y] = val;
		origin[x][y]= val;
	}
	
	 float getShade(int x, int y) { //return associated new shaded value calculated in previous layer
		// to do 
		float value = sunData[x][y];
		return value; // incorrect value
	}
	
	 void setShade(int x, int y, float val){ //calculate new values in sunmap
		// to do
		sunData[x][y] = val*shadefraction;
		
	}
	
	// reduce the 
	 void shadow(Tree tree){ 
		// to do
		synchronized(this) {
			float ext = tree.getExt();
			int xt = tree.getX()-(int)ext;;
			int yt = tree.getY()-(int)ext;
			int dim = (int)ext*2+1;
			int count = 0;
		
			
			for(int i = 0; i<dim;i++) {
				for(int j=0; j<dim; j++) {
					if((xt+i>=getDimX())||(yt+j>=getDimX())||(xt+i<0)||(yt+j<0)) {
						continue;
					}
					else {
						setShade(xt+i, yt+j, getShade(xt+i,yt+j));
						count++;
					}
					
				}
			}
		
		}
		
	}
}