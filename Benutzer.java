package Kaese_und_wein_verwaltung;

public class Benutzer {

	private String benutzername;
	private String passwort;

	public Benutzer(String benutzername, String passwort) {
		this.benutzername = benutzername;
		this.passwort = passwort;
	}

	public Benutzer() {
	}

	public String getBenutzername() {
		return benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public void setPasswort(String passwort) {

		if (istGueltigesPasswort(passwort)) {
			this.passwort = passwort;
		} else {
			System.out.println("Passwort ungültig. Mindestens 6 Zeichen, " + "1 Buchstabe und 1 Zahl erforderlich.");
		}
	}

	// Prüft, ob der Benutzername gültig ist
	public static boolean istGueltigerBenutzername(String benutzername) {

		if (benutzername == null || benutzername.isBlank()) {
			return false;
		}
		if (benutzername.contains(" ")) {
			return false;
		}

		boolean hatBuchstabe = false;

		for (int i = 0; i < benutzername.length(); i++) {

			char zeichen = benutzername.charAt(i);

			if ((zeichen >= 'A' && zeichen <= 'Z') || (zeichen >= 'a' && zeichen <= 'z')) {

				hatBuchstabe = true;
			}
		}

		return hatBuchstabe;
	}

	// Prüft, ob das Passwort gültig ist
	public static boolean istGueltigesPasswort(String passwort) {
		if (passwort.length() < 6) {
			return false;
		}
		if (passwort.contains(" ")) {
			return false;
		}
		boolean hatZahl = false;
		boolean hatBuchstabe = false;

		for (char c : passwort.toCharArray()) {

			if (Character.isLetter(c)) {
				hatBuchstabe = true;
			}
			if (Character.isDigit(c)) {
				hatZahl = true;
			}
		}
		return hatBuchstabe && hatZahl;
	}

}
