/**
 * @author Victor Chu
 * netid: vic4
 * email: vic4@duke.edu
 * 
 * Simulation program for the NBody assignment
 */


public class Body {
	//REMINDER: Make all methods public and all instance
	//variables private. Since they are private, we can't access them directly in other classes.
	
	private double myXPos;//x position
	private double myYPos;//y position
	private double myXVel;//x velocity
	private double myYVel;//y velocity
	private double myMass;//mass
	private String myFileName;
	//Two constructors
	
	/**
	 * this constructor will just assign all the instance variables above
	 * @param xp
	 * @param yp
	 * @param xv
	 * @param yv
	 * @param mass
	 * @param filename
	 */
	public Body(double xp, double yp, double xv, double yv, double mass, String filename)
	{
		myXPos = xp;
		myYPos= yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
		
		
	}
	
	/**
	 * and just copies the instance variables so we have two bodies now
	   but with identical instance variables such as xPos
	
	   SIDENOTE: the reason we are using getters is because our instance variables are private
       therefore, use getter to access it
	 * @param b
	 */
	public Body(Body b)
	{
		//other way would be to use this
		// this(b.getX()). 'this' is referring to this instance of the class. Calls the constructor
		myXPos = b.getX();
		myYPos= b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	
	//The methods that are named get(?) are called getters
	//they access the instance variabesl
	
	/**
	 * 
	 * @return instance variables
	 */
	/**
	 * 
	 * @return myXPos
	 */
	public double getX()
	{
		return myXPos;
	}
	
	/**
	 * 
	 * @return myYPos
	 */
	public double getY()
	{
		return myYPos;
	}
	/**
	 * 
	 * @return myXVel
	 */
	public double getXVel()
	{
		return myXVel;
	}
	
	/**
	 * 
	 * @return myYVel
	 */
	public double getYVel()
	{
		return myYVel;
	}
	
	/**
	 * 
	 * @return myMass
	 */
	public double getMass()
	{
		return myMass;
	}
	
	/**
	 * 
	 * @return myFileName
	 */
	public String getName()
	{
		return myFileName;
	}
	
	
	
	//this method just calculates the distance between two bodies
	/**
	 * 
	 * @param b
	 * @return distance between two bodies
	 */
	public double calcDistance(Body b)
	{
		double distance;
		//the reason we are using myXPos-b.myXPos is to refer to our main
		//body, but also our copy which is b.myXPos. Two objects
		distance = Math.sqrt(Math.pow(myXPos-b.myXPos, 2) + Math.pow(myYPos-b.myYPos,2));
		return distance;
	}
	
	
	
	/**
	 * using body p instead. Straightforward in writing this. Not much to say about this one
	 * @param p
	 * @return force
	 */
	public double calcForceExertedBy(Body p)
	{
		double bigG = (6.67e-11);//uses java scientfic notation
		double massTimesMass = myMass*p.myMass;//calculates mass
		double numerator = bigG * massTimesMass;//numerator
		double denominator = calcDistance(p)*calcDistance(p);//could have Math.pow as well
		double force = numerator/denominator;
		return force;
	}

	
	/**
	 * This method calculates the force exerted by the x-force vector. 
	 * @param p
	 * @return fx(change in x)
	 */
	public double calcForceExertedByX(Body p)
	{
		double dx = (p.myXPos - myXPos);
		double force = calcForceExertedBy(p);
		double fx = (dx*force)/calcDistance(p);
		return fx;
	}
	
	
	/**
	 * same thing as the previous example of calcForceExertedByX
	 * @param p
	 * @return fx(change in y)
	 */
	public double calcForceExertedByY(Body p)
	{
		double dy = (p.myYPos - myYPos);
		double force = calcForceExertedBy(p);
		double fx = (dy*force)/calcDistance(p);
		return fx;
	}
		
	
	/**
	 * this method returns the net force on the body
	  by all other bodies in the array. 
	  we do this by summing all the forces of those elements
	   the array contains each body. 
	 * @param bodies
	 * @return netforce by x. 
	 */
	public double calcNetForceExertedByX(Body[] bodies)
	{
		double forceSum = 0;//force sum for all the vectors
		for(Body b: bodies)//we will loop through all the elements in the array
		{
			if(!b.equals(this))//this ensures that we don't accidently count 'this' object
								//when we are looping through. Don't want to self reference
			{
				forceSum = calcForceExertedByX(b)+forceSum;//call calcForce and add to forceSum
			}
		}
		return forceSum;
	}
	
	
	/**
	 * same as calcNetForceExertedByX but with Y instead
	 * @param bodies
	 * @return net force by y
	 */
	public double calcNetForceExertedByY(Body[] bodies)
	{
		double forceSum = 0;
		for(Body b: bodies)
		{
			if(!b.equals(this))
			{
				forceSum = calcForceExertedByY(b)+forceSum;
			}
		}
		return forceSum;
	}
	
	
	/**
	 * this method will update our position and velocity
	   given a dT, and xy force.
	   this is a mutator since we actually changing our
	   instance variables
	 * 
	 * @param deltaT
	 * @param xforce
	 * @param yforce
	 * @returns new position and velocity
	 */
	public void update(double deltaT, double xforce, double yforce)
	{//deltaT are small time steps. xforce and yforce are just the net forces
		double accelX = xforce/myMass;
		double accelY = yforce/myMass;
		//make an acceleration variable
		
		
		//these give us our new velocity
		double nvx = myXVel + deltaT * accelX;
		double nvy = myYVel + deltaT * accelY;
		
		//gives our new position
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;
		
		
		//we are just updating our instance variables here 
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
				
	}

	
	/**
	 * simulates our bodies in space
	 */
	public void draw()//this function is just actually simulating the image on the screen. 
	{
		StdDraw.picture(myXPos,myYPos, "images/"+myFileName);
	}



}


