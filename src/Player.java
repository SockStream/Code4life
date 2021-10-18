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
	public int molecules_totales_A;
	public int molecules_totales_B;
	public int molecules_totales_C;
	public int molecules_totales_D;
	public int molecules_totales_E;
	public List<Echantillon> liste_projets_scientifiques;
	
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

	public void sauvegarder_molecules_disponibles() {
		molecules_totales_A = nombre_molecules_A;
		molecules_totales_B = nombre_molecules_B;
		molecules_totales_C = nombre_molecules_C;
		molecules_totales_D = nombre_molecules_D;
		molecules_totales_E = nombre_molecules_E;
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
		//System.err.println("id : " + id + "\n" +
		//"nombre_molecules_A : " + nombre_molecules_A +
		//"rank : " + rank);
	}
		
	public int nombre_molecules() {
		return (nombre_molecules_A + nombre_molecules_B + nombre_molecules_C + nombre_molecules_D + nombre_molecules_E);
	}
}

class Robot {
	private static final int THRESHOLD = 5;
	public int nombre_molecules_A, nombre_molecules_B, nombre_molecules_C, nombre_molecules_D, nombre_molecules_E;
	int expertiseA, expertiseB, expertiseC, expertiseD, expertiseE;
	public List<Echantillon> echantillons;
	public modulesEnum module_courant;
	public World world;
	private modulesEnum target;
	private int travel_time;
	
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

	public String obtenir_sample(int nombre_emplacements_libres)
	{
		int rank = 1;
		//Je récupère un sample
		//3 espaces => rank 2
		if (mon_expertise() <= 4)
		{
			rank = 1;
		}
		else
		{
			if (nombre_emplacements_libres == 3)
			{
				rank = 2;
			}
			//2 espaces => rank 2
			if (nombre_emplacements_libres == 2)
			{
				rank = 2;
			}
			//1 espace => rank 1
			if (nombre_emplacements_libres == 1)
			{
				rank = 1;
			}
			if (mon_expertise() >= 8)
			{
				rank ++;
			}
		}
		return "CONNECT " + Integer.toString(rank);
	}
	
	private int mon_expertise() {
		// TODO Auto-generated method stub
		return expertiseA + expertiseB + expertiseC + expertiseD + expertiseE;
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
		calcul_travel_time(module);
        return commande;
	}
	
	private void calcul_travel_time(modulesEnum destination)
	{
		switch(destination)
		{
			case DIAGNOSIS:
				switch(module_courant)
				{
					case DIAGNOSIS:
						travel_time = 0;
						break;
					case LABORATORY:
						travel_time = 4;
						break;
					case MOLECULES:
						travel_time = 3;
						break;
					case SAMPLES:
						travel_time = 3;
						break;
					case START_POS:
						travel_time = 2;
						break;
					default:
						break;
				}
			break;
			case LABORATORY:
				switch(module_courant)
				{
					case DIAGNOSIS:
						travel_time = 4;
						break;
					case LABORATORY:
						travel_time = 0;
						break;
					case MOLECULES:
						travel_time = 3;
						break;
					case SAMPLES:
						travel_time = 3;
						break;
					case START_POS:
						travel_time = 2;
						break;
					default:
						break;
				}
			break;
			case MOLECULES:
				switch(module_courant)
				{
					case DIAGNOSIS:
						travel_time = 3;
						break;
					case LABORATORY:
						travel_time = 3;
						break;
					case MOLECULES:
						travel_time = 0;
						break;
					case SAMPLES:
						travel_time = 3;
						break;
					case START_POS:
						travel_time = 2;
						break;
					default:
						break;
				}
			break;
			case SAMPLES:
				switch(module_courant)
				{
					case DIAGNOSIS:
						travel_time = 3;
						break;
					case LABORATORY:
						travel_time = 3;
						break;
					case MOLECULES:
						travel_time = 3;
						break;
					case SAMPLES:
						travel_time = 0;
						break;
					case START_POS:
						travel_time = 2;
						break;
					default:
						break;
			}
			break;
			case START_POS:
				switch(module_courant)
				{
					case DIAGNOSIS:
						break;
					case LABORATORY:
						break;
					case MOLECULES:
						break;
					case SAMPLES:
						break;
					case START_POS:
						break;
					default:
						break;
				}
				break;
			default:
				break;
		
		}
	}
	
	public String GenerateMove()
	{
		System.err.println("Module courant : " + module_courant);
		Echantillon _echantillon = null;
		travel_time --;
		if (travel_time > 0)
		{
			return "";
		}
		
		switch(module_courant)
		{
			case DIAGNOSIS:
				//lorsque je suis au module DIAGNOSIS
				//si j'ai des samples à faire analyser
				if (samples_a_analyser())
				{
					//je les analyse
					return analyserDiagnostique();
				}
				//si un résultat d'analyse n'est pas faisable parce qu'il n'y a pas assez de molécules en jeu et que je n'ai pas l'expertise nécessaire
				if (( _echantillon = je_ne_suis_pas_assez_expert()) != null)
				{
					System.err.println("PROUT 2");
					// Je stocke le résultat
					return stockerProjet(_echantillon);
				}
				if (!jai_molecules_pour_au_moins_un_medicament() && nombre_echantillons_analyses() > 0)
				{
					return StockerProjetPlusComplique();
				}
				if (nombre_echantillons_analyses() == 0 && nombre_echantillons_a_analyser() == 0)
				{
					return allerModule(modulesEnum.SAMPLES);
				}
				// sinon
				// Je vais au module MOLECULES
				return allerModule(modulesEnum.MOLECULES);
				//break;
			case MOLECULES:
				// Lorsque je suis au module MOLECULES
				// si je n'ai pas de médicament de prêt
				if (((_echantillon = un_medicament_est_pret()) == null) && jai_molecules_pour_au_moins_un_medicament())
				{
					// Je prépare le médicament qui apporte le plus de points
					return preparer_un_medicament();
				}
				// sinon,
				if ((_echantillon = un_medicament_est_pret()) != null)
				{
					// je vais au module LABORATORY
					return allerModule(modulesEnum.LABORATORY);
				}
				if (!jai_molecules_pour_au_moins_un_medicament() && echantillons.size() < 3)
				{
					return allerModule(modulesEnum.SAMPLES);
				}
				return allerModule(modulesEnum.DIAGNOSIS);
				//break;
			case LABORATORY:
				// Lorsque je suis au module au module LABORATORY
				// si j'ai un médicament préparé
				if ((_echantillon = un_medicament_est_pret()) != null)
				{
					// Je l'envoie
					return envoyer_echantillon(_echantillon);
				}
				if (lister_medicaments_craftables().size() > 0)
				{
					return allerModule(modulesEnum.MOLECULES);
				}
				// Sinon
				// Je vais au module SAMPLES
				return allerModule(modulesEnum.SAMPLES);
			    //break;
			case SAMPLES:
				// Lorsque je suis au module SAMPLES
				// Si j'ai 3 échantillons (samples + analysés)
				if (nombre_emplacements_libres() == 0)
				{
					// Je vais au module DIAGNOSIS
					return allerModule(modulesEnum.DIAGNOSIS);
				}
				// sinon,
				else
				{
					return obtenir_sample(nombre_emplacements_libres());
				}
				//break;
			case START_POS:
				//lors du premier tour (quand on est en position initiale), je note le nombre de molécules disponibles pour chaque type
				world.sauvegarder_molecules_disponibles();
				return allerModule(modulesEnum.SAMPLES);
				//break;
			default:
				break;
		
		}
		
		throw new UnsupportedOperationException("AUCUNE DECISION PRISE");
	}
	
	private String StockerProjetPlusComplique() {
		int min = -1;
		int id = -1;
		for (Echantillon _echantillon : echantillons)
		{
			if (_echantillon.nombre_molecules_A < 0)
			{
				continue;
			}
			
			if (_echantillon.rank > min)
			{
				min = _echantillon.rank;
				id = _echantillon.id;
			}
		}
		System.err.println("PROUT 1");
		return "CONNECT " + id;
	}

	private boolean jai_molecules_pour_au_moins_un_medicament() {
		boolean value = false;
		for (Echantillon _echantillon : echantillons)
		{
			if (_echantillon.nombre_molecules_A < 0)
			{
				continue;
			}
			if (
					(_echantillon.nombre_molecules_A <= (expertiseA + nombre_molecules_A + world.nombre_molecules_A)) &&
					(_echantillon.nombre_molecules_B <= (expertiseB + nombre_molecules_B + world.nombre_molecules_B)) &&
					(_echantillon.nombre_molecules_C <= (expertiseC + nombre_molecules_C + world.nombre_molecules_C)) &&
					(_echantillon.nombre_molecules_D <= (expertiseD + nombre_molecules_D + world.nombre_molecules_D)) &&
					(_echantillon.nombre_molecules_E <= (expertiseE + nombre_molecules_E + world.nombre_molecules_E))&&
					(
						Math.max( Math.max(0,_echantillon.nombre_molecules_A - expertiseA),nombre_molecules_A) +
						Math.max( Math.max(0,_echantillon.nombre_molecules_B - expertiseB),nombre_molecules_B) +
						Math.max( Math.max(0,_echantillon.nombre_molecules_C - expertiseC),nombre_molecules_C) +
						Math.max( Math.max(0,_echantillon.nombre_molecules_D - expertiseD),nombre_molecules_D) +
						Math.max( Math.max(0,_echantillon.nombre_molecules_E - expertiseE),nombre_molecules_E) <= 10
					)
			)
			{
				System.err.println("TRUE => " + _echantillon.id);
				value = true;
			}
				
		}
		return value;
	}

	public Echantillon un_medicament_est_pret()
	{
		
		List<Echantillon> liste_medicaments = new ArrayList<Echantillon>();
		for (Echantillon echantillon : echantillons)
		{
			if (
					(echantillon.nombre_molecules_A <= (expertiseA + nombre_molecules_A)) &&
					(echantillon.nombre_molecules_B <= (expertiseB + nombre_molecules_B)) &&
					(echantillon.nombre_molecules_C <= (expertiseC + nombre_molecules_C)) &&
					(echantillon.nombre_molecules_D <= (expertiseD + nombre_molecules_D)) &&
					(echantillon.nombre_molecules_E <= (expertiseE + nombre_molecules_E))
				)
			{
				liste_medicaments.add(echantillon);
			}
		}
		
		if (liste_medicaments.size() == 0)
		{
			return null;
		}
		return liste_medicaments.get(0);
	}	
	
	public List<Echantillon> lister_medicaments_craftables()
	{
		List<Echantillon> medicaments_craftables = new ArrayList<Echantillon>();
		for (Echantillon echantillon : echantillons)
		{
			if (
					(echantillon.nombre_molecules_A <= (expertiseA + world.nombre_molecules_A + nombre_molecules_A)) &&
					(echantillon.nombre_molecules_B <= (expertiseB + world.nombre_molecules_B + nombre_molecules_B)) &&
					(echantillon.nombre_molecules_C <= (expertiseC + world.nombre_molecules_C + nombre_molecules_C)) &&
					(echantillon.nombre_molecules_D <= (expertiseD + world.nombre_molecules_D + nombre_molecules_D)) &&
					(echantillon.nombre_molecules_E <= (expertiseE + world.nombre_molecules_E + nombre_molecules_E)) &&
					(
						Math.max( Math.max(0,echantillon.nombre_molecules_A - expertiseA),nombre_molecules_A) +
						Math.max( Math.max(0,echantillon.nombre_molecules_B - expertiseB),nombre_molecules_B) +
						Math.max( Math.max(0,echantillon.nombre_molecules_C - expertiseC),nombre_molecules_C) +
						Math.max( Math.max(0,echantillon.nombre_molecules_D - expertiseD),nombre_molecules_D) +
						Math.max( Math.max(0,echantillon.nombre_molecules_E - expertiseE),nombre_molecules_E) <= 10
					)
				)
			{
				medicaments_craftables.add(echantillon);
			}
		}
		return medicaments_craftables;
	}
	
	private String preparer_un_medicament() {
		Echantillon _echantillon = null;
		int min = -1;
		for (Echantillon echantillon : lister_medicaments_craftables())
		{
			System.err.println("Echantillon craftable : " + echantillon.id);
			if (echantillon.health > min )
			{
				_echantillon = echantillon;
				min = _echantillon.health;
			}
		}
		
		return  obtenir_molecules(_echantillon);
	}

	private int nombre_emplacements_libres() {
		return 3 - echantillons.size();
	}

	// private String stockerProjetInfaisable() {
	// Echantillon a_supprimer = null;
	// int rang_max = -1;
	// for (Echantillon _echantillon : obtenir_projet_infaisable())
	// {
	// System.err.println("COUCOU !");
	// if (rang_max <= _echantillon.rank)
	// {
	// rang_max = _echantillon.rank;
	// a_supprimer = _echantillon;
	// }
	// }
	// return "CONNECT " +  a_supprimer.id;
	// }
	
	// private Echantillon obtenir_medicament_fabrique()
	// {
	// for (Echantillon _echantillon : echantillons)
	// {
	// if (_echantillon.nombre_molecules_A <0)
	// {
	// continue;
	// }
	// if (
	// _echantillon.nombre_molecules_A <= (nombre_molecules_A + expertiseA) &&
	// _echantillon.nombre_molecules_B <= (nombre_molecules_B + expertiseB) &&
	// _echantillon.nombre_molecules_C <= (nombre_molecules_C + expertiseC) &&
	// _echantillon.nombre_molecules_D <= (nombre_molecules_D + expertiseD) &&
	// _echantillon.nombre_molecules_E <= (nombre_molecules_E + expertiseE)
	// )
	// {
	// return _echantillon;
	// }
	// }
	// return null;
	// }
	
	// private List<Echantillon> obtenir_projet_infaisable() {
	// //regarder si un des médicaments est craftable
	// Echantillon _echantillon_a_faire = null;
	// List<Echantillon> projets_infaisables = new ArrayList<Echantillon>();
	// System.err.println("world.nombre_molecules_A : " + world.nombre_molecules_A);
	// int max_health = -1;
	// for (Echantillon echantillon : echantillons)
	// {
	// if (echantillon.nombre_molecules_A < 0)
	// {
	// continue;
	// }
	// if (
	// (echantillon.nombre_molecules_A <= (nombre_molecules_A + world.nombre_molecules_A + expertiseA)) &&
	// (echantillon.nombre_molecules_B <= (nombre_molecules_B + world.nombre_molecules_B + expertiseB)) &&
	// (echantillon.nombre_molecules_C <= (nombre_molecules_C + world.nombre_molecules_C + expertiseC)) &&
	// (echantillon.nombre_molecules_D <= (nombre_molecules_D + world.nombre_molecules_D + expertiseD)) &&
	// (echantillon.nombre_molecules_E <= (nombre_molecules_E + world.nombre_molecules_E + expertiseE)) &&
	// echantillon.health >= max_health &&
	// (
	// Math.max( Math.max(0,echantillon.nombre_molecules_A - expertiseA),nombre_molecules_A) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_B - expertiseB),nombre_molecules_B) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_C - expertiseC),nombre_molecules_C) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_D - expertiseD),nombre_molecules_D) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_E - expertiseE),nombre_molecules_E) <= 10
	//
	// )
	// )
	// {
	// projets_infaisables.add(echantillon);
	// }
	//
	// }
	// return projets_infaisables;
	// }
	
	// private Echantillon obtenir_medoc_craftable() {
	// //regarder si un des médicaments est craftable
	// Echantillon _echantillon_a_faire = null;
	// int max_health = -1;
	// for (Echantillon echantillon : echantillons)
	// {
	// if (echantillon.nombre_molecules_A <0)
	// {
	// continue;
	// }
	// if (
	// (echantillon.nombre_molecules_A <= (nombre_molecules_A + world.nombre_molecules_A + expertiseA)) &&
	// (echantillon.nombre_molecules_B <= (nombre_molecules_B + world.nombre_molecules_B + expertiseB)) &&
	// (echantillon.nombre_molecules_C <= (nombre_molecules_C + world.nombre_molecules_C + expertiseC)) &&
	// (echantillon.nombre_molecules_D <= (nombre_molecules_D + world.nombre_molecules_D + expertiseD)) &&
	// (echantillon.nombre_molecules_E <= (nombre_molecules_E + world.nombre_molecules_E + expertiseE)) &&
	// echantillon.health >= max_health &&
	// (
	// Math.max( Math.max(0,echantillon.nombre_molecules_A - expertiseA),nombre_molecules_A) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_B - expertiseB),nombre_molecules_B) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_C - expertiseC),nombre_molecules_C) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_D - expertiseD),nombre_molecules_D) +
	// Math.max( Math.max(0,echantillon.nombre_molecules_E - expertiseE),nombre_molecules_E) <= 10
	//
	// )
	// )
	// {
	// _echantillon_a_faire = echantillon;
	// max_health = echantillon.health;
	// }
	//
	// }
	// return _echantillon_a_faire;
	// }
	
	private String envoyer_echantillon(Echantillon _echantillon) {
		return "CONNECT " + _echantillon.id;
	}

	private String stockerProjet(Echantillon _echantillon) {
		return "CONNECT " + _echantillon.id;
	}

	private Echantillon je_ne_suis_pas_assez_expert() {
		Echantillon echantillon = null;
		
		for (Echantillon _echantillon : echantillons)
		{
			if (
					(_echantillon.nombre_molecules_A > (world.molecules_totales_A + expertiseA)) || 
					(_echantillon.nombre_molecules_B > (world.molecules_totales_B + expertiseB)) || 
					(_echantillon.nombre_molecules_C > (world.molecules_totales_C + expertiseC)) || 
					(_echantillon.nombre_molecules_D > (world.molecules_totales_D + expertiseD)) || 
					(_echantillon.nombre_molecules_E > (world.molecules_totales_E + expertiseE))
				)
			{
				echantillon = _echantillon;
			}
		}		
		return echantillon;
	}

	private String analyserDiagnostique() {
		for (Echantillon _echantillon : echantillons)
		{
			if (_echantillon.nombre_molecules_A < 0)
			{
				return "CONNECT " + _echantillon.id;
			}
		}
        return "-1";
	}

	private boolean samples_a_analyser() {
		Boolean value = false;
		for (Echantillon _echantillon : echantillons)
		{
			if (_echantillon.nombre_molecules_A < 0)
			{
				value = true;
			}
		}
		return value;
	}

	private int nombre_echantillons_analyses() {
		int compteur = 0;
		for (Echantillon echantillon : echantillons)
		{
			if (echantillon.nombre_molecules_A >= 0)
			{
				compteur ++;
			}
		}
		return compteur;
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
		System.err.println("Molecules pour echantillon : " + echantillon.id);
		if (echantillon.nombre_molecules_A > (nombre_molecules_A + expertiseA) && world.nombre_molecules_A > 0)
		{
			return "CONNECT " + "A";
		}
		if (echantillon.nombre_molecules_B > (nombre_molecules_B + expertiseB) && world.nombre_molecules_B > 0)
		{
			return "CONNECT " + "B";
		}
		if (echantillon.nombre_molecules_C > (nombre_molecules_C + expertiseC) && world.nombre_molecules_C > 0)
		{
			return "CONNECT " + "C";
		}
		if (echantillon.nombre_molecules_D > (nombre_molecules_D + expertiseD) && world.nombre_molecules_D > 0)
		{
			return "CONNECT " + "D";
		}
		if (echantillon.nombre_molecules_E > (nombre_molecules_E + expertiseE) && world.nombre_molecules_E > 0)
		{
			return "CONNECT " + "E";
		}
		return "WAIT";
		//throw new UnsupportedOperationException("ON PENSE PAR ERREUR QU'ON A BESOIN DE MOLECULES");
	}
	
	private Boolean on_a_molecules_pour_medicament(Echantillon echantillon)
	{
		if (echantillon == null)
		{
			return false;
		}
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
	
	// private int analyserDiagnostique() {
	// int max_points = -1;
	// int meilleur_echantillon = -1;
	// for(Echantillon echantillon : echantillons)
	// {
	// if (echantillon.nombre_molecules_A < 0)
	// {
	// max_points = echantillon.health;
	// meilleur_echantillon = echantillon.id;
	// }
	// }
	// return meilleur_echantillon;
	// }
	
	public String connecterDiagnostic(int id)
	{
		return "CONNECT " + Integer.toString(id);
	}
	
	// public boolean peut_gerer(Echantillon _echantillon) {
	// if (
	// (
	// (_echantillon.nombre_molecules_A - expertiseA) +
	// (_echantillon.nombre_molecules_B - expertiseB) +
	// (_echantillon.nombre_molecules_C - expertiseC) +
	// (_echantillon.nombre_molecules_D - expertiseD) +
	// (_echantillon.nombre_molecules_E - expertiseE)
	// ) > 10)
	// {
	// return false;
	// }
	// return true;
	// }
}

class Player {

    public static void main(String args[]) {
        World myWorld = new World();
        Robot myRobot = new Robot();
        Robot otherRobot = new Robot();
        Scanner in = new Scanner(System.in);
        List<Echantillon> liste_projets_scientifiques = new ArrayList<Echantillon>();
        int projectCount = in.nextInt();
        for (int i = 0; i < projectCount; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
        	Echantillon echantillon = new Echantillon(a, b, c, d, e, -1, 1, 50, -1);
        	liste_projets_scientifiques.add(echantillon);
        }
        myWorld.liste_projets_scientifiques = liste_projets_scientifiques;
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
                	myRobot.echantillons.add(_echantillon);
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
        //System.err.println("Target : " + target);
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