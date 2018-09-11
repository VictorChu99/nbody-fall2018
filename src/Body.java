
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
	
	//this constructor will just assign all the instance variables above
	public Body(double xp, double yp, double xv, double yv, double mass, String filename)
	{
		myXPos = xp;
		myYPos= yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
		
		
	}
	
	//this constructor takes our instance variables from the body we are passing in
	//and just copies the instance variables so we have two bodies now
	//but with identical instance variables such as xPos
	
	//SIDENOTE: the reason we are using getters is because our instance variables are private
	//therefore, use getter to access it
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
	
	//Make all these getters to access private instance variables
	public double getX()
	{
		return myXPos;
	}
	public double getY()
	{
		return myYPos;
	}
	public double getXVel()
	{
		return myXVel;
	}
	public double getYVel()
	{
		return myYVel;
	}
	public double getMass()
	{
		return myMass;
	}
	public String getName()
	{
		return myFileName;
	}
	
	
	//this program just calculates the distance between two bodies
	public double calcDistance(Body b)
	{
		double distance;
		//the reason we are using myXPos-b.myXPos is to refer to our main
		//body, but also our copy which is b.myXPos
		distance = Math.sqrt(Math.pow(myXPos-b.myXPos, 2) + Math.pow(myYPos-b.myYPos,2));
		return distance;
	}
	
	//using body p instead. Straightforward in writing this. Not much to say about this one
	public double calcForceExertedBy(Body p)
	{
		double bigG = (6.67e-11);//uses java scientfic notation
		double massTimesMass = myMass*p.myMass;//calculates mass
		double numerator = bigG * massTimesMass;//numerator
		double denominator = calcDistance(p)*calcDistance(p);//could have Math.pow as well
		double force = numerator/denominator;
		return force;
	}

	
	//FIXME: Ask negative vs positive. Why does the order of positions matter:
	//Answer: Direction matters in this case
	//straightforward again. Tricky part was making sure that numbers were
	//positive or negative
	public double calcForceExertedByX(Body p)
	{
		double dx = (p.myXPos - myXPos);
		double force = calcForceExertedBy(p);
		double fx = (dx*force)/calcDistance(p);
		return fx;
	}
	//same thing as the previous example of calcForceExertedByX
	public double calcForceExertedByY(Body p)
	{
		double dy = (p.myYPos - myYPos);
		double force = calcForceExertedBy(p);
		double fx = (dy*force)/calcDistance(p);
		return fx;
	}
	
	
	//this method returns the net force on the body
	//by all other bodies in the array. 
	//we do this by summing all the forces of those arrays
	public double calcNetForceExertedByX(Body[] bodies)
	{
		double forceSum = 0;//force sum for all the vectors
		for(Body b: bodies)//we will loop through all the elements in the array
		{
			if(!b.equals(this))//this ensures that we don't accidently count this object
								//when we are looping through
			{
				forceSum = calcForceExertedByX(b)+forceSum;//call calcForce and add to forceSum
			}
		}
		return forceSum;
	}
	
	//same as calcNetForceExertedByX but with Y instead
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
	
	
	
	//this method will update our position and velocity
	//given a dT, and xy force.
	public void update(double deltaT, double xforce, double yforce)
	{//deltaT are small time steps. xforce and yforce are just the net forces
		double accelX = xforce/myMass;
		double accelY = yforce/myMass;
		//make an acceleration variable
		
		
		//these give us our new veloicty
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

	public void draw()
	{
		StdDraw.picture(myXPos,myYPos, "images/"+myFileName);
	}



}