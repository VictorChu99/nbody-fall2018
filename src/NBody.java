/**
 * @author Victor Chu
 * netid: vic4
 * email: vic4@duke.edu
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBody{
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		Scanner s = new Scanner(new File(fname));
		
		
		// TODO: read values at beginning of file to
		// find the radius
		
		double radius;
		int numberOfBodies;
		
		numberOfBodies = s.nextInt();//finds the next int
		radius = s.nextDouble();//finds the next double
		
		s.close();
		
		// TODO: return radius read
		return radius;	
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static Body[] readBodies(String fname) throws FileNotFoundException 
	{
			//this method will return an array
			Scanner s = new Scanner(new File(fname));
			
			// TODO: read # bodies, create array, ignore radius
			int nb = 0; // # bodies to be read
			nb = s.nextInt();
			Body[] arrayBody = new Body[nb];
			
			double y = s.nextDouble();//we have to move the counter step to pass the radius
			
			for(int k=0; k < nb; k++) 
			{
				
				// TODO: read data for each body
				// construct new body object and add to array
				
				//you want to create a body object each time for each array element
				//This is what is  happening below
				Body arrayPart = new Body(s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.nextDouble(),s.next());
				arrayBody[k] = arrayPart;//we are just assigning the element to the body
				
			}
			
			s.close();//close the file
			
			// TODO: return array of body objects read
			return arrayBody;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 157788000.0;//time for which this program can run
		double dt = 25000.0;
		
		String fname= "./data/planets.txt";
		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		Body[] bodies = readBodies(fname);
		double radius = readRadius(fname);
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");
	
		for(double t = 0.0; t < totalTime; t += dt) {
			
			// TODO: create double arrays xforces and yforces
			// to hold forces on each body
			double[] xforces = new double[bodies.length];
			double[] yforces = new double[bodies.length];
			
			// TODO: loop over all bodies, calculate
			// net forces and store in xforces and yforces
			for(int i =0; i < bodies.length;i++)
			{
				xforces[i] = bodies[i].calcNetForceExertedByX(bodies); //first we have to pass along a body into body class. Then we can use the calcnetForce
				
				yforces[i] = bodies[i].calcNetForceExertedByY(bodies); //same explanation as before but on the y coordinate
			}
			
			
			// TODO: loop over all bodies and call update
			// with dt and corresponding xforces, yforces values
			for (int i = 0; i < bodies.length; i++)
			{
				bodies[i].update(dt, xforces[i], yforces[i]);//calls update on each body
			}
			
			
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// TODO: loop over all bodies and call draw on each one
			for(Body b: bodies)//for each loop to go over all the bodies
			{
				b.draw();
			}		
			
			StdDraw.show(10);
		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
