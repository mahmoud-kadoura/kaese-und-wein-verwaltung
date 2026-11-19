package Kaese_und_wein_verwaltung;

import java.time.LocalDate;

/**
 * Abstrakte Basisklasse für alle Produkte im Lager.
 */
public abstract class Produkte {

	private String bezeichnung;
	private int qualitaet;
	private LocalDate stichtag;
	private double preis;

	Produkte(String bezeichnung, int qualitaet, LocalDate stichtag, double preis) {
		this.bezeichnung = bezeichnung;
		this.qualitaet = qualitaet;
		this.stichtag = stichtag;
		this.preis = preis;
	}

	Produkte() {
	}

	// Getter
	public String getBezeichnung() {
		return bezeichnung;
	}

	public int getQualitaet() {
		return qualitaet;
	}

	public LocalDate getStichtag() {
		return stichtag;
	}

	public double getPreis() {
		return preis;
	}

	// Setter
	public void setQualitaet(int qualitaet) {
		this.qualitaet = qualitaet;
	}

	public void zeigeDetails() {

		System.out.printf("%-15s | Qualität: %3d | Preis: %6.2f €%n", getBezeichnung(), getQualitaet(), getPreis());

	}

	public void zeigeStatus(LocalDate aktuellerTag) {

		if (mussEntsorgungWerden(aktuellerTag)) {
			System.out.println(getBezeichnung() + " muss entsorgt werden");
		} else {
			zeigeDetails();
		}
	}

	public abstract void naechsterTag(LocalDate aktuellerTag);

	public abstract boolean mussEntsorgungWerden(LocalDate aktuellerTag);

	@Override
	public String toString() {
		return getBezeichnung();
	}
}
