package Kaese_und_wein_verwaltung;

import java.time.LocalDate;

/**
 * Repräsentiert Käse im Lager mit spezieller Qualitäts- und Preisberechnung.
 */

public class Kaese extends Produkte {

	public Kaese(String bezeichnung, int qualitaet, LocalDate stichtag, double preis) {
		super(bezeichnung, qualitaet, stichtag, preis);
	}

	@Override
	public void setQualitaet(int qualitaet) {
		if (qualitaet >= 30) {
			super.setQualitaet(qualitaet);
		}
	}

	@Override
	public void naechsterTag(LocalDate aktuellerTag) {
		this.setQualitaet(this.getQualitaet() - 1);
	}

	@Override
	public boolean mussEntsorgungWerden(LocalDate aktuellerTag) {
		return this.getQualitaet() <= 30 || !aktuellerTag.isBefore(getStichtag());

	}

	@Override
	public double getPreis() {
		return super.getPreis() + 0.1 * getQualitaet();
	}

}
