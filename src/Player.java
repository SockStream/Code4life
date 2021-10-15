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
	LABORATORY,
	SAMPLES,
	START_POS
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

	public boolean echantillonsDisponibles() {
		return false;
	}
}

class Echantillon {
	public int nombre_molecules_A, nombre_molecules_B, nombre_molecules_C, nombre_molecules_D, nombre_molecules_E;
	public int id;
	public Boolean available;
	public int health;
	public int rank;
	
	public Echantillon (int _nombre_molecules_A, int _nombre_molecules_B, int _nombre_molecules_C, int _nombre_molecules_D, int _nombre_molecules_E, int _id, int _available, int _health, int _rank)
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
		rank = _rank;
		System.err.println("id : " + id + "\n" +
				"nombre_molecules_A : " + nombre_molecules_A +
				"rank : " + rank);
	}

	public int nombre_molecules() {
		return (nombre_molecules_A + nombre_molecules_B + nombre_molecules_C + nombre_molecules_D + nombre_molecules_E);
	}
}

class Robot {
	public int nombre_molecules_A, nombre_molecules_B, nombre_molecules_C, nombre_molecules_D, nombre_molecules_E;
	int expertiseA, expertiseB, expertiseC, expertiseD, expertiseE;
	public List<Echantillon> echantillons;
	public modulesEnum module_courant;
	public World world;
	public int nombre_echantillons_rejetes;
	
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
		world = null;
	}
	
	public String obtenir_sample()
	{
		int rank = 0;
		rank = nombre_echantillons_a_analyser() + 1;
		if (nombre_echantillons_rejetes > 0)
		{
			rank = rank - (nombre_echantillons_rejetes - 1);
		}
		return "CONNECT " + Integer.toString(rank);
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
		case SAMPLES:
			commande += "SAMPLES";
		default:
			break;
		}
        return commande;
	}

	public String GenerateMove() {
		if (echantillons.size() == 0 && world.echantillonsDisponibles() && module_courant.equals(modulesEnum.DIAGNOSIS))
		{
			return connecterDiagnostic(RecupererMeilleurDiagnostique());
		}
		
		//Obvious, si on a pas de diagnostique et qu'il n'y en a pas de dispo, on va aller chercher un échantillon
		if (echantillons.size() == 0 && ! module_courant.equals(modulesEnum.SAMPLES))
		{
			return allerModule(modulesEnum.SAMPLES);
		}
		if (module_courant.equals(modulesEnum.SAMPLES) && (nombre_echantillons_a_analyser() + nombre_echantillons_rejetes) < 3)
		{
			return obtenir_sample();
		}
		//si on a un échantillon pas analysé, on va aller au module DIAGNOSIS
		if (nombre_echantillons_a_analyser() > 0 && ! module_courant.equals(modulesEnum.DIAGNOSIS))
		{
			return allerModule(modulesEnum.DIAGNOSIS);
		}
		
		//si on a pas de diagnostique analysé et qu'on est au module DIAGNOSIS, on va en télécharger un
		if (echantillons.size() > 0 && echantillons.get(0).nombre_molecules_A < 0 && module_courant.equals(modulesEnum.DIAGNOSIS))
		{
			return connecterDiagnostic(RecupererMeilleurDiagnostique());
		}
		//Si on a un diagnostique, qu'on a pas les molécules nécessaires et qu'on est pas au bon module, on va aller au module MOLECULES
		if (echantillons.size() > 0 && !on_a_molecules_pour_medicament(echantillons.get(0)) && ! module_courant.equals(modulesEnum.MOLECULES))
		{
			return allerModule(modulesEnum.MOLECULES);
		}
		//si on a un diagnostique, qu'on a pas les molécules nécessaires et qu'on est au module MOLECULES, on va récupérer les molécules correspondantes
		if (echantillons.size() > 0 && !on_a_molecules_pour_medicament(echantillons.get(0)) && module_courant.equals(modulesEnum.MOLECULES))
		{
			return obtenir_molecules(echantillons.get(0));
		}
		//si on a un diagnostique, qu'on a les molécules nécessaires mais qu'on est pas au molule LABORATORY, on y va
		if (echantillons.size() > 0 && on_a_molecules_pour_medicament(echantillons.get(0)) && !module_courant.equals(modulesEnum.LABORATORY))
		{
			return allerModule(modulesEnum.LABORATORY);
		}
		//si on a un diagnostique, qu'on a les molécules nécessaires et qu'on est pas au molule LABORATORY, on uploade
		if (echantillons.size() > 0 && on_a_molecules_pour_medicament(echantillons.get(0)) && module_courant.equals(modulesEnum.LABORATORY))
		{
			return connecterLaboratory(echantillons.get(0).id);
		}

		throw new UnsupportedOperationException("AUCUNE DECISION PRISE");
	}
	
	private int nombre_echantillons_a_analyser() {
		int compteur = 0;
		for (Echantillon echantillon : echantillons)
		{
			if (echantillon.nombre_molecules_A < 0)
			{
				compteur ++;
			}
		}
		return compteur;
	}

	private String obtenir_molecules(Echantillon echantillon)
	{
		if (echantillon.nombre_molecules_A > (nombre_molecules_A + expertiseA))
		{
			return "CONNECT " + "A";
		}
		if (echantillon.nombre_molecules_B > (nombre_molecules_B + expertiseB))
		{
			return "CONNECT " + "B";
		}
		if (echantillon.nombre_molecules_C > (nombre_molecules_C + expertiseC))
		{
			return "CONNECT " + "C";
		}
		if (echantillon.nombre_molecules_D > (nombre_molecules_D + expertiseD))
		{
			return "CONNECT " + "D";
		}
		if (echantillon.nombre_molecules_E > (nombre_molecules_E + expertiseE))
		{
			return "CONNECT " + "E";
		}

		throw new UnsupportedOperationException("ON PENSE PAR ERREUR QU'ON A BESOIN DE MOLECULES");
	}
	
	private Boolean on_a_molecules_pour_medicament(Echantillon echantillon)
	{
		if (echantillon.nombre_molecules_A > (nombre_molecules_A + expertiseA))
		{
			return false;
		}
		if (echantillon.nombre_molecules_B > (nombre_molecules_B + expertiseB))
		{
			return false;
		}
		if (echantillon.nombre_molecules_C > (nombre_molecules_C + expertiseC))
		{
			return false;
		}
		if (echantillon.nombre_molecules_D > (nombre_molecules_D + expertiseD))
		{
			return false;
		}
		if (echantillon.nombre_molecules_E > (nombre_molecules_E + expertiseE))
		{
			return false;
		}
		return true;
	}
	
	private String connecterLaboratory(int id_echantillon)
	{
		return "CONNECT " + id_echantillon;
	}
	
	private int RecupererMeilleurDiagnostique() {
		int max_points = -1;
		int meilleur_echantillon = -1;
		for(Echantillon echantillon : echantillons)
		{
			if (echantillon.nombre_molecules_A < 0)
			{
				if (echantillon.health >= max_points)
				{
					max_points = echantillon.health;
					meilleur_echantillon = echantillon.id;
				}
			}
		}
		return meilleur_echantillon;
	}

	public String connecterDiagnostic(int id)
	{
		return "CONNECT " + Integer.toString(id);
	}

	public boolean peut_gerer(Echantillon _echantillon) {
		if (
				(
					(_echantillon.nombre_molecules_A - expertiseA) + 
					(_echantillon.nombre_molecules_B - expertiseB) + 
					(_echantillon.nombre_molecules_C - expertiseC) + 
					(_echantillon.nombre_molecules_D - expertiseD) + 
					(_echantillon.nombre_molecules_E - expertiseE)
				) > 10) 
		{
			return false;
		}
		return true;
	}
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
        	myRobot.nombre_echantillons_rejetes = 0;
            for (int i = 0; i < 2; i++) {
            	if (i == 0)
            	{
            		robot = myRobot;
            	}
            	else
            	{
            		robot = otherRobot;
            	}
            	robot.echantillons.clear();
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
                robot.world = myWorld;
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
                
                Echantillon _echantillon = new Echantillon (costA, costB, costC, costD, costE, sampleId, carriedBy, health, rank);
                myWorld.liste_echantillons.add(_echantillon);
                if (carriedBy == 0)
                {
                	if (myRobot.peut_gerer(_echantillon))
                	{
                		myRobot.echantillons.add(_echantillon);
                	}
                	else
                	{
	                	myRobot.nombre_echantillons_rejetes += 1;
                	}
                }
                if (carriedBy == 1)
                {
                	otherRobot.echantillons.add(_echantillon);
                }
                
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            String commande = myRobot.GenerateMove();
            System.out.println(commande);
            //System.out.println("GOTO DIAGNOSIS");
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
			return modulesEnum.START_POS;
		}
		else if (target.toUpperCase().equals("SAMPLES"))
		{
			return modulesEnum.SAMPLES;
		}
		else
		{
			throw new UnsupportedOperationException("Module " + target + " not implemented");
		}
	}
}