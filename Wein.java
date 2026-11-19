package Kaese_und_wein_verwaltung;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Repräsentiert Wein im Lager, dessen Qualität mit der Zeit reift.
 */

public class Wein extends Produkte {

	private int startQualitaet;
	private double regalPreis;

	public Wein(String bezeichnung, int qualitaet, LocalDate stichtag, double preis) {

		super(bezeichnung, qualitaet, stichtag, preis);
		this.startQualitaet = qualitaet;
		this.regalPreis = preis + 0.1 * qualitaet;
	}

	@Override
	public void setQualitaet(int qualitaet) {
		if (qualitaet > 0 && qualitaet <= 50) {
			super.setQualitaet(qualitaet);
		}
	}

	@Override
	public double getPreis() {
		return regalPreis;
	}

	@Override
	public void naechsterTag(LocalDate aktuellerTag) {

		long tageSeitStichtag = ChronoUnit.DAYS.between(this.getStichtag(), aktuellerTag);

		if (tageSeitStichtag >= 10) {

			int neueQualitaet = startQualitaet + (int) (tageSeitStichtag / 10);

			if (neueQualitaet > 50) {
				neueQualitaet = 50;
			}

			this.setQualitaet(neueQualitaet);
		}
	}

	@Override
	public boolean mussEntsorgungWerden(LocalDate aktuellerTag) {
		return false;
	}

}
