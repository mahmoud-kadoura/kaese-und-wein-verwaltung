package Kaese_und_wein_verwaltung;

import java.time.LocalDate;

public class SuperDuperMarkt {

	public static void main(String[] args) {

		Lager lager = new Lager();

		lager.produktHinzufuegen(new Kaese("Gouda", 40, LocalDate.now().plusDays(60), 4.99));

		lager.produktHinzufuegen(new Kaese("Emmentaler", 35, LocalDate.now().plusDays(80), 5.99));

		lager.produktHinzufuegen(new Wein("Merlot", 20, LocalDate.now().minusDays(10), 9.99));

		lager.produktHinzufuegen(new Wein("Riesling", 15, LocalDate.now().minusDays(5), 7.99));

		System.out.println("=================================");
		System.out.println("   SUPER DUPER MARKT SYSTEM");
		System.out.println("=================================");

		Benutzerbearbeiten benutzer = new Benutzerbearbeiten(lager);

		benutzer.login();
		System.out.println("\nProgramm beendet.");
	}
}