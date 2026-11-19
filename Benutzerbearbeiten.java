package Kaese_und_wein_verwaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Verwaltet Produkte, Statistiken, Entsorgungen und die Historie.
 */

public class Benutzerbearbeiten {

	private Lager lager;
	private ArrayList<Benutzer> benutzerListe;
	private Benutzer angemeldeterBenutzer;

	public Benutzerbearbeiten(Lager lager) {
		this.lager = lager;
		this.benutzerListe = new ArrayList<>();
	}

	Scanner scanner = new Scanner(System.in);

	private int loginMenue() {

		while (true) {

			System.out.println("""
					Bitte wählen Sie:
					1 - Registrierung
					2 - Anmeldung
					0 - Beenden
					""");

			if (scanner.hasNextInt()) {

				int wahl = scanner.nextInt();
				scanner.nextLine();

				if (wahl >= 0 && wahl <= 2) {
					return wahl;
				}
			} else {
				scanner.nextLine();
			}

			System.out.println("Ungültige Eingabe! Bitte 1, 2 oder 0 eingeben.");
		}
	}

	public void login() {

		boolean angemeldet = false;

		do {

			int wahl = loginMenue();

			if (wahl == 1) {
				registrierung();

			} else if (wahl == 2) {
				angemeldet = anmelden();

			} else if (wahl == 0) {
				System.out.println("Programm beenden");
				angemeldet = true;
			}

		} while (!angemeldet);
	}

	public boolean benutzernameExistiert(String benutzername) {
		for (Benutzer benutzer : benutzerListe) {
			if (benutzer.getBenutzername().equalsIgnoreCase(benutzername)) {
				return true;
			}
		}
		return false;
	}

	public void registrierung() {

		System.out.println("===== Registrierung =====");

		String name;

		do {
			System.out.print("Benutzername: ");
			name = scanner.nextLine().trim();

			if (!Benutzer.istGueltigerBenutzername(name)) {

				System.out.println(
						"Ungültiger Benutzername!\n" + "Der Benutzername muss mindestens einen Buchstaben enthalten.");

				continue;
			}

			if (benutzernameExistiert(name)) {

				System.out.println("Benutzername bereits vergeben.");

			}

		} while (!Benutzer.istGueltigerBenutzername(name) || benutzernameExistiert(name));

		System.out.println("""
				Passwort erstellen:
				- mindestens 6 Zeichen
				- mindestens eine Zahl
				- mindestens ein Buchstabe
				""");

		String passwort = scanner.nextLine();

		while (!Benutzer.istGueltigesPasswort(passwort)) {

			System.out.println("Ungültiges Passwort!");

			System.out.print("Neues Passwort eingeben: ");
			passwort = scanner.nextLine();
		}

		benutzerListe.add(new Benutzer(name, passwort));

		System.out.println("\nRegistrierung erfolgreich!");
		anmelden();
	}

	public boolean anmelden() {
		System.out.println("Herzlich Willkommen \n" + "Benutzname: ");
		String name = scanner.nextLine();
		System.out.println("Passwort: ");
		String pin = scanner.nextLine();

		Benutzer benutzer = loginpruefen(name, pin);

		if (benutzer != null) {
			angemeldeterBenutzer = benutzer;
			System.out.println("Login erfolgreich");
			MenueAnzeigen();
			return true;
		}

		System.out.println("Benutzername oder Passwort falsch");
		return false;

	}

	public Benutzer loginpruefen(String benutzername, String passwort) {

		for (Benutzer benutzer : benutzerListe) {

			if (benutzer.getBenutzername().equalsIgnoreCase(benutzername) && benutzer.getPasswort().equals(passwort)) {
				return benutzer;
			}
		}
		return null;
	}

	public void MenueAnzeigen() {

		int wahl;

		do {
			System.out.println("===== Hauptmenü =====");
			System.out.println("""
					1- Produkte hinzufügen
					2- Produkte löschen
					3- Produkte anzeigen
					4- Produkt suchen
					5- Entsorgung
					6- Statistik
					7- Historie anzeigen
					8- Simulieren
					0- Beenden
					""");
			wahl = liesMenueAuswahl(0, 8);

			switch (wahl) {

			case 1 -> produktHinzufuegen();
			case 2 -> produktEntfernen();
			case 3 -> lager.zeigeAlleProdukte();
			case 4 -> produktSuchen();
			case 5 -> lager.pruefeEntsorgung(LocalDate.now());
			case 6 -> statistik();
			case 7 -> lager.zeigeHistorie();
			case 8 -> simuliereTag(LocalDate.now());
			case 0 -> System.out.println("Programm wird beendet.");
			default -> System.out.println("Ungültige Eingabe.");
			}

		} while (wahl != 0);
	}

	public void produktHinzufuegen() {

		System.out.println("===== Neues Produkt hinzufügen =====");

		System.out.println("""
				Produktart wählen:
				1 - Käse
				2 - Wein
				""");

		int wahl = liesMenueAuswahl(1, 2);

		String bezeichnung = liesBezeichnung();

		int qualitaet = liesQualitaet();

		double grundPreis = liesPreis();

		if (wahl == 1) {

			int verfallstage = liesVerfallstage();

			lager.produktHinzufuegen(
					new Kaese(bezeichnung, qualitaet, LocalDate.now().plusDays(verfallstage), grundPreis));

			System.out.println("Käse erfolgreich hinzugefügt.");

		} else {

			lager.produktHinzufuegen(new Wein(bezeichnung, qualitaet, LocalDate.now(), grundPreis));

			System.out.println("Wein erfolgreich hinzugefügt.");
		}
	}

	public void produktEntfernen() {

		System.out.println("===== Produkt Entfernen =====");

		System.out.println("Geben Sie die Bezeichnung ein:");

		String bezeichnung = scanner.nextLine();

		lager.produktEntfernen(bezeichnung, angemeldeterBenutzer.getBenutzername());

	}

	public void statistik() {

		System.out.println("\n====================================");
		System.out.println("          LAGERSTATISTIK");
		System.out.println("====================================");

		System.out.printf("Produkte im Lager:          %d%n", lager.anzahlProdukte());

		System.out.printf("Entsorgte Produkte:         %d%n", lager.anzahlEntsorgteProdukte());

		System.out.printf("Käse im Lager:              %d%n", lager.anzahlKaese());

		System.out.printf("Wein im Lager:              %d%n", lager.anzahlWein());

		System.out.printf("Gesamtwert Lager:           %.2f €%n", lager.lagerGesamtwert());

		System.out.printf("Durchschnittliche Qualität: %.2f%n", lager.durchschnittlicheQualitaet());

		System.out.println("====================================");
	}

	public void produktSuchen() {

		boolean gefunden = false;

		System.out.println("Geben Sie die Bezeichnung ein: ");
		String bezeichnung = scanner.nextLine();

		for (Produkte produkt : lager.getLagerProdukteListe()) {
			if (produkt.getBezeichnung().equalsIgnoreCase(bezeichnung)) {
				System.out.println("Produkt " + produkt + " ist gefunden");
				produkt.zeigeDetails();
				gefunden = true;
				break;
			}
		}
		if (gefunden == false) {
			System.out.println("Das Produkt " + bezeichnung + " NICHT gefunden");
		}
	}

	public void simuliereTag(LocalDate aktuellerTag) {

		ArrayList<String> entsorgteNamen = new ArrayList<>();

		System.out.println("Wie viele Tage sollen simliert werden? ");

		int tage = liesMenueAuswahl(1, 365);

		for (int i = 1; i <= tage; i++) {

			aktuellerTag = aktuellerTag.plusDays(1);

			lager.setAktuellerTag(aktuellerTag);

			lager.naechsterTag(aktuellerTag);

			ArrayList<Produkte> entsorgte = lager.pruefeEntsorgung(aktuellerTag);

			for (Produkte p : entsorgte) {
				entsorgteNamen.add(p.getBezeichnung());
			}
		}

		System.out.println(tage + " Tage wurden simuliert.");
		System.out.println("Aktuelles Simulationsdatum: " + lager.getAktuellerTag());

		if (!entsorgteNamen.isEmpty()) {

			System.out.println("\nAutomatisch entsorgte Produkte:");

			for (String name : entsorgteNamen) {
				System.out.println("- " + name);
			}
		}
	}

	private String liesBezeichnung() {

		String bezeichnung;

		while (true) {

			System.out.print("Bezeichnung: ");
			bezeichnung = scanner.nextLine().trim();

			if (bezeichnung.isEmpty()) {
				System.out.println("Die Bezeichnung darf nicht leer sein.");
				continue;
			}

			if (bezeichnung.matches("\\d+")) {
				System.out.println("Die Bezeichnung darf nicht nur aus Zahlen bestehen.");
				continue;
			}

			return bezeichnung;
		}
	}

	private int liesMenueAuswahl(int min, int max) {

		while (true) {

			if (scanner.hasNextInt()) {

				int auswahl = scanner.nextInt();
				scanner.nextLine();

				if (auswahl >= min && auswahl <= max) {
					return auswahl;
				}
			} else {
				scanner.nextLine();
			}

			System.out.println("Ungültige Eingabe. Bitte eine Zahl zwischen " + min + " und " + max + " eingeben.");
		}
	}

	private int liesQualitaet() {

		while (true) {

			System.out.print("Qualität: ");

			if (scanner.hasNextInt()) {

				int qualitaet = scanner.nextInt();
				scanner.nextLine();

				if (qualitaet > 0) {
					return qualitaet;
				}
			} else {
				scanner.nextLine();
			}

			System.out.println("Bitte eine positive ganze Zahl eingeben.");
		}
	}

	private double liesPreis() {

		while (true) {

			System.out.print("Grundpreis: ");

			if (scanner.hasNextDouble()) {

				double preis = scanner.nextDouble();
				scanner.nextLine();

				if (preis > 0) {
					return preis;
				}
			} else {
				scanner.nextLine();
			}

			System.out.println("Bitte einen gültigen Preis größer als 0 eingeben.");
		}
	}

	private int liesVerfallstage() {

		while (true) {

			System.out.print("Verfallstage (50-100): ");

			if (scanner.hasNextInt()) {

				int tage = scanner.nextInt();
				scanner.nextLine();

				if (tage >= 50 && tage <= 100) {
					return tage;
				}
			} else {
				scanner.nextLine();
			}

			System.out.println("Bitte einen Wert zwischen 50 und 100 eingeben.");
		}
	}

}
