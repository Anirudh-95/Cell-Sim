public class CellSimImproved {
	public static void main(String[] args){
		
		System.out.println("enter size of grid");//to store the size of the grid
		int n=IO.readInt();
		char[][] tissue=new char[n][n]; // intializing a 2D array of dimension NxN
		int totalMoves=0;// variable to store the total moves in the simulation
		int movesSat=0; //variable to store the number of rounds taken to reach satisfaction
		
		
		System.out.println("enter the percentage of blank cells");//accepting the value for the percentage of blank cells
		int empty= IO.readInt();
		System.out.println("enter the percentage of x"); //accepting the value for the percentage of x
		int x= IO.readInt();
		
		System.out.println("Enter the threshold value of the the tissue sample");// enter the threshold for the agents
		int threshold=IO.readInt();
		
		if(threshold<0 || threshold>100){
			System.out.println("Error : enter threshold between 0 to 100");
			threshold=IO.readInt();
		}

        System.out.println("Enter the maximum number of round you want to run the program");// enter the max rounds for the program
		int maxRounds=IO.readInt();
		
		System.out.println("Enter the frequency at which you want to print the tissue sample");
		int frequency=IO.readInt();
		
		if(frequency==0){
			System.out.println("Error: please enter a frequency above 0");
		    frequency=IO.readInt();
		}
		
		assignCellTypes(tissue, empty, x); // calling the method to assign the different positions of the cells 
		
		System.out.println("Intial Board: ");
		printTissue(tissue); //printing the tissue grid 
		System.out.println("");
		
		int rounds=maxRounds;// storing the value of max rounds in rounds
		   while(maxRounds>0){// to run the program for the max rounds
			   
			   
		   if(boardSatisfied(tissue,threshold)==false){// to check if the board is satisfied
		       totalMoves=totalMoves +	moveAllUnsatisfied(tissue, threshold);// to count the total number moves
		   }
		
	     	else{ 
		    	rounds=rounds-maxRounds;// to calculate the rounds taken for the tissue to reach satisfaction
	     		break;
		   }
		
		   if(maxRounds<rounds && maxRounds%frequency==0){// to print the tissue sample according to the frequency inputed
			   printTissue(tissue);
		    	System.out.println("");
		   }
		
	    	maxRounds--;
		}
		   
		System.out.println("Total number of simulations that occured in the movement: "+totalMoves);// to print the total number of simulations that occured in the tissue movement
		System.out.println("");
		
		System.out.println("Final Board: ");// to print the final board
		printTissue(tissue); 
		System.out.println("");
		if(boardSatisfied(tissue,threshold)==true){// to check if the board is satisfied
			System.out.println("The board is satisfied ");
		    System.out.println("Total rounds required for satisfaction is "+rounds
		    		);
		}
		else{
			System.out.println("The board is not satisfied ");
			// logic to calculate the total number of cells that are satisfied
			int totalSat=0;
			double percentSat=0.0;
			for(int i=0; i<tissue.length; i++){
				  for(int k=0;k< tissue[i].length; k++){
				     if(isSatisfied(tissue, i, k, threshold)==true){
				    	 totalSat++;
				     }
				 }
			  }
			percentSat=(double)(totalSat*100.0)/(n*n);// converting the number of satisfied cells to a percentage
			System.out.println("The percentage of satisfied agents is "+percentSat+ "  "+totalSat+ " agents are satisfied");
	       }
		
	}
	/**
	* Given a tissue sample, prints the cell make up in grid form
	*
	* @param tissue a 2-D character array representing a tissue sample
	* 
	***/
	public static void printTissue(char[][] tissue){
		int i,k;
		int l=tissue.length; //computing the length of the grid 
		
		for(i=0; i<l;i++){
			for(k=0;k<l;k++){
				
				System.out.print(tissue[i][k]+ " "); // printing the tissue grid 
			}
			System.out.println(""); // to go the new line 
		}
	
	}
	
	/**
	* Given a blank tissue sample, populate it with the correct cell makeup given the parameters. 
	* Cell type 'X' will be represented by the character 'X'
	* Cell type 'O' will be represented by the character 'O'
	* Vacant spaces will be represented by the character ' '
	*
	* Phase I: alternate X and O cells throughout, vacant cells at the "end" (50% credit)
	*		e.g.:	'X' 'O' 'X' 'O' 'X'
	*				'O' 'X' 'O' 'X' 'O'
	*				'X' 'O' 'X' 'O' 'X'
	*				' ' ' ' ' ' ' ' ' '
	*				' ' ' ' ' ' ' ' ' '
	*
	* Phase II: Random assignment of all cells (100% credit)
	*
	* @param tissue a 2-D character array that has been initialized
	* @param percentBlank the percentage of blank cells that should appear in the tissue
	* @param percentX Of the remaining cells, not blank, the percentage of X cells that should appear in the tissue. Round up if not a whole number
	*
	**/
	public static void assignCellTypes(char[][] tissue, int percentBlank, int percentX){
	    int l= tissue.length;
		double numberBlank=(double)(percentBlank*((l*l)/100.0));//to find the number of blank cells in the tissue 
		numberBlank=Math.ceil(numberBlank); // method to round the number 
		double numberX=(percentX*((l*l)-numberBlank))/100.0; //to find the number of X cells from the remaining cells in the tissue
		numberX=Math.ceil(numberX); // method to round the number 
		
				
		   while(numberBlank>0){ // loop to assign number of x cells throughout the grid 
					int r =(int)(Math.random()*(l*l)); // to generate a random cell to a store a cell
					int row=r/l; //find the row of the random cell
					int col=(r%l);//find the column of the random cell
					
					if(tissue[row][col]!=' '){ // to check if the cells has a blank space
						
						  tissue[row][col]= ' '; // store blank space in the cell 
						  numberBlank--; // decrement the value of number of blank cells to be assigned 
				
		     }	
		   }
				
		   while(numberX>0){ // loop to assign number of x cells throughout the grid 
					int r=(int)(Math.random()*(l*l));// to generate a random cell to a store a cell
				    int row= r/l; //find the row of the random cell
				    int col=r%l; //find the column of the random cell
					
				    if(tissue[row][col]!=' '){   // to check if the cells have a blank
				    	if(tissue[row][col]!='X'){// to check if the cells have an X
				    	tissue[row][col]='X'; // store X in the cell 
				    	
				    	numberX--; //decrement the value of number of X cells to be assigned
				    	}
					}
		   }
			 for(int i=0;i<tissue.length;i++){
				for(int k=0;k<tissue[i].length;k++){
							
						if(tissue[i][k]!=' '){// to check if the cells has a blank space
							if(tissue[i][k]!='X'){ // to check if the cells has a x
						    	   tissue[i][k]='O'; // store O space in the cell 
							     
						     }
					    }    
				   }
				
			  }
		   
     }
	/**
	    * Given a tissue sample, and a (row,col) index into the array, determines if the agent at that location is satisfied.
	    * Note: Blank cells are always satisfied (as there is no agent)
	    *
	    * @param tissue a 2-D character array that has been initialized
	    * @param row the row index of the agent
	    * @param col the col index of the agent
	    * @param threshold the percentage of like agents that must surround the agent to be satisfied
	    * @return boolean indicating if given agent is satisfied
	    *
	    **/
	    public static boolean isSatisfied(char[][] tissue, int row, int col, int threshold){
                   char ch=tissue[row][col];///store the agent in tissue in given row and column
	    	       int totalX;// declare a variable to store the number of X cells 
                   int totalC;// declare a variable to store the total number of cells around the agent 
                   totalX=0;//intialising totalX to 0
                   totalC=0;//intialising totalC to 0
                   
                   
                   double percentX=0.0;// variable to store the percentage of similar cells agents 
                   if(ch==' ')
                	   return true;
                   else{
                   for(int j=1;j<=8;j++){/*loop that is used to traverse the cells around the agent-> 3 for corner cells,
                	       
                	                       5 for cells at the side and 8 for all the other cells*/
                	   
                	   int nrow=row;// to store new value of the row to traverse elements around the given agent
                       int ncol=col;// to store new value of the column to traverse elements around the given agent
                       
                       
                	   switch(j){
                	   case 1:
                		   nrow++;
                		   break;
                	   case 2:
                		   ncol++;
                		   break;
                	   case 3:
                		   nrow--;
                		   break;
                	   case 4:
                		   ncol--;
                		   break;
                	   case 5:
                		   nrow++;
                		   ncol++;
                		   break;
                	   case 6:
                		   nrow++;
                		   ncol--;
                		   break;
                	   case 7:
                		   nrow--;
                		   ncol++;
                		   break;
                	   case 8:
                		   nrow--;
                		   ncol--;
                		   break;
                   }
                	   if(nrow<0|| ncol<0 || nrow>=tissue.length|| ncol>=tissue.length)// check if the surrounding cell is in a valid position
                		  continue;
                	
                		   if(tissue[nrow][ncol]==ch)// check if the surrounding cell is equal to the agent
                			   totalX++;//increment the value of total similar agents 
                		   if(tissue[nrow][ncol]==' ')// check if the surrounding cell is equal to ' '
                			   continue;
                		   if(tissue[nrow][ncol]=='X'|| tissue[row][col]=='O')// check if the surrounding cell is equal to 'O' or 'X'
                			   totalC++;// increment the value of total agents around the cell
                		   
                		   
                	   
                   }
                  
                 percentX=(double)(totalX*100)/totalC; // find the percentage of similar agents 
                 
                 if(percentX>=threshold)//check if the percentage of similar agents is greater than or equal to the threshold
                	return true;
                 else 
                	 return false;
                	
              
         }
	    }
	    /**
	    * Given a tissue sample, determines if all agents are satisfied.
	    * Note: Blank cells are always satisfied (as there is no agent)
	    *
	    * @param tissue a 2-D character array that has been initialized
	    * @return boolean indicating whether entire board has been satisfied (all agents)
	    **/
	    public static boolean boardSatisfied(char[][] tissue, int threshold){
	    	
			boolean isStabilized = true;// if all the cells are satisfied, this will remain true else it will change to false

			//the nested loops are used to access each cell in the tissue
			OUTERLOOP: for(int i=0; i<tissue.length; i++)
			{
				for(int j=0; j<tissue.length; j++)
				{
					//check if the cell at row i and column j is satisfied
					if(isSatisfied(tissue, i, j, threshold))
					{
						continue;// the cell is satisfied
					}
					else //cell is unsatisfied
					{
						isStabilized = false; //tissue is not fully satisfied
						break OUTERLOOP;
					}
				}
			}
			return isStabilized; // returning the boolean variable isStabilized
	    }
	    /**
	     * Given a tissue sample, move all unsatisfied agents to a vacant cell
	     *
	     * @param tissue a 2-D character array that has been initialized
	     * @param threshold the percentage of like agents that must surround the agent to be satisfied
	     * @return an integer representing how many cells were moved in this round
	     **/
	     public static int moveAllUnsatisfied(char[][] tissue, int threshold){
	    	    int n=tissue.length;
	    		int l=0;// to store the number of movements in the round
	    		

	    			for(int i=0; i<n; i++){//the nested loops are used to access each cell in the tissue
	    				for(int j=0; j<n; j++){
	    					
	    					if(isSatisfied(tissue, i, j, threshold)){//check if the cell at row i and column j is satisfied
	    					   continue;//the cell is satisfied.
	    					}
	    					else {//cell is unsatisfied
	    						l++;// method to calculate number of movements in the rounds 
	    						
	    						// to traverse through the whole tissue sample to check for a vacant cell
	    						loop: for(int k=0; k<n; k++)
		    					{
		    						for(int p=0; p<n; p++)
		    						{
		    							if(tissue[k][p]==' ')// check if the cell at row k and column p is blank
		    							{
		    								tissue[k][p]=tissue[i][j];// store the unsatisfied cell in the blank location 
		    								tissue[i][j]=' ';
		    								break loop;// breaking from the outer loop and continue to check for the next cell
		    							}
		    						}
		    					}
	    					}
	    				}
	    			}
	    		
      return l; // returning the number of movements in that occured in the round 
      }// end of the  method
}
