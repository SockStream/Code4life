import java.util.*;

import java.io.*;
import java.lang.reflect.Array;
import java.math.*;

/**
 * Bring data on patient samples from the diagnosis machine to the laboratory with enough molecules to produce medicine!
 **/

enum modulesEnum {
	DIAGNOSIS,
	MOLECULES,
	LABORATORY
}

class World {
	public List<Echantillon> liste_echantillons;
	public int nombre_molecules_A, nombre_molecules_B, nombre_molecules_C, nombre_molecules_D, nombre_molecules_E;
	
	public World()
	{
		liste_echantillons = new ArrayList<Echantillon>();
		nombre_molecules_A =0;
		nombre_molecules_B =0;
		nombre_molecules_C =0;
		nombre_molecules_D =0;
		nombre_molecules_E =0;
	}
}

class Echantillon {
	public int nombre_molecules_A, nombre_molecules_B, nombre_molecules_C, nombre_molecules_D, nombre_molecules_E;
	public int id;
	public Boolean available;
	public int health;
	
	public Echantillon (int _nombre_molecules_A, int _nombre_molecules_B, int _nombre_molecules_C, int _nombre_molecules_D, int _nombre_molecules_E, int _id, int _available, int _health)
	{
		nombre_molecules_A = _nombre_molecules_A;
		nombre_molecules_B = _nombre_molecules_B;
		nombre_molecules_C = _nombre_molecules_C;
		nombre_molecules_D = _nombre_molecules_D;
		nombre_molecules_E = _nombre_molecules_E;
		id = _id;
		health = _health;
		if (_available == -1)
		{
			available = true;
		}
		else
		{
			available = false;
		}
	}
}

class Robot {
	public int nombre_molecules_A, nombre_molecules_B, nombre_molecules_C, nombre_molecules_D, nombre_molecules_E;
	int expertiseA, expertiseB, expertiseC, expertiseD, expertiseE;
	public List<Echantillon> echantillons;
	public modulesEnum module_courant;
	
	public Robot ()
	{
		nombre_molecules_A = 0;
		nombre_molecules_B = 0;
		nombre_molecules_C = 0;
		nombre_molecules_D = 0;
		nombre_molecules_E = 0;
		expertiseA = 0;
		expertiseB = 0;
		expertiseC = 0;
		expertiseD = 0;
		expertiseE = 0;
		echantillons = new ArrayList<Echantillon>();
	}
	
	public String allerModule(modulesEnum module)
	{
		String commande = "GOTO ";
		switch (module)
		{
		case DIAGNOSIS:
			commande += "DIAGNOSIS";
			break;
		case LABORATORY:
			commande += "LABORATORY";
			break;
		case MOLECULES:
			commande += "MOLECULES";
			break;
		default:
			break;
		
		}
        return commande;
	}
	
	/*public String connecter()
	{
		return "CONNECT";
	}*/
}

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int projectCount = in.nextInt();
        for (int i = 0; i < projectCount; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
        }
        World myWorld = new World();
        Robot myRobot = new Robot();
        Robot otherRobot = new Robot();
        // game loop
        while (true) {
        	Robot robot;
            for (int i = 0; i < 2; i++) {
            	if (i == 0)
            	{
            		robot = myRobot;
            	}
            	else
            	{
            		robot = otherRobot;
            	}
                String target = in.next();
                robot.module_courant = String_To_Module(target);
                int eta = in.nextInt();
                int score = in.nextInt();
                int storageA = in.nextInt();
                robot.nombre_molecules_A = storageA;
                int storageB = in.nextInt();
                robot.nombre_molecules_B = storageB;
                int storageC = in.nextInt();
                robot.nombre_molecules_C = storageC;
                int storageD = in.nextInt();
                robot.nombre_molecules_D = storageD;
                int storageE = in.nextInt();
                robot.nombre_molecules_E = storageE;
                int expertiseA = in.nextInt();
                robot.expertiseA = expertiseA;
                int expertiseB = in.nextInt();
                robot.expertiseB = expertiseB;
                int expertiseC = in.nextInt();
                robot.expertiseC = expertiseC;
                int expertiseD = in.nextInt();
                robot.expertiseD = expertiseD;
                int expertiseE = in.nextInt();
                robot.expertiseE = expertiseE;
            }
            int availableA = in.nextInt();
            myWorld.nombre_molecules_A = availableA;
            int availableB = in.nextInt();
            myWorld.nombre_molecules_B = availableB;
            int availableC = in.nextInt();
            myWorld.nombre_molecules_C = availableC;
            int availableD = in.nextInt();
            myWorld.nombre_molecules_D = availableD;
            int availableE = in.nextInt();
            myWorld.nombre_molecules_E = availableE;
            int sampleCount = in.nextInt();
            myWorld.liste_echantillons.clear();
            for (int i = 0; i < sampleCount; i++) {
            	int sampleId = in.nextInt();
                int carriedBy = in.nextInt();
                int rank = in.nextInt();
                String expertiseGain = in.next();
                int health = in.nextInt();
                int costA = in.nextInt();
                int costB = in.nextInt();
                int costC = in.nextInt();
                int costD = in.nextInt();
                int costE = in.nextInt();
                
                Echantillon _echantillon = new Echantillon (costA, costB, costC, costD, costE, sampleId, carriedBy, health);
                myWorld.liste_echantillons.add(_echantillon);
                
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println("GOTO DIAGNOSIS");
        }
    }

	private static modulesEnum String_To_Module(String target) {
		if (target.toUpperCase().equals("DIAGNOSIS"))
		{
			return modulesEnum.DIAGNOSIS;
		}
		else if (target.toUpperCase().equals("MOLECULES"))
		{
			return modulesEnum.MOLECULES;
		}
		else if (target.toUpperCase().equals("LABORATORY"))
		{
			return modulesEnum.LABORATORY;
		}
		else if (target.toUpperCase().equals("START_POS"))
		{
			return modulesEnum.LABORATORY;
		}
		else
		{
			throw new UnsupportedOperationException("Module " + target + " not implemented");
		}
	}
}