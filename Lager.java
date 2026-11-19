package Kaese_und_wein_verwaltung;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Lager {

	private ArrayList<Produkte> produkte;
	private ArrayList<Produkte> entsorgteProdukte;
	private ArrayList<HistorienEintrag> historieListe;
	private LocalDate aktuellerTag = LocalDate.now();

	public Lager() {
		produkte = new ArrayList<>();
		entsorgteProdukte = new ArrayList<>();
		historieListe = new ArrayList<>();
	}

	public ArrayList<Produkte> getLagerProdukteListe() {
		return produkte;
	}

	public ArrayList<Produkte> getEntsorgteProdukte() {
		return entsorgteProdukte;
	}

	public LocalDate getAktuellerTag() {
		return aktuellerTag;
	}

	public void setAktuellerTag(LocalDate aktuellerTag) {
		this.aktuellerTag = aktuellerTag;
	}

	public void naechsterTag(LocalDate aktuellerTag) {

		for (Produkte produkt : produkte) {
			produkt.naechsterTag(aktuellerTag);
		}
		pruefeEntsorgung(aktuellerTag);
	}

	public void produktHinzufuegen(Produkte produkt) {
		produkte.add(produkt);
	}

	public void produktEntfernen(String bezeichnung, String benutzername) {

		boolean gefunden = false;

		Iterator<Produkte> iterator = produkte.iterator();

		while (iterator.hasNext()) {

			Produkte produkt = iterator.next();

			if (produkt.getBezeichnung().equalsIgnoreCase(bezeichnung)) {
				entsorgteProdukte.add(produkt);
				iterator.remove();

				historieListe.add(new HistorienEintrag(produkt, benutzername, "GELÖSCHT", LocalDateTime.now()));
				System.out.println("Produkt " + bezeichnung + " entfernt");
				gefunden = true;
				return;
			}
		}
		if (!gefunden) {
			System.out.println("Produkt " + bezeichnung + " NICHT gefunden");
		}
	}

	public void zeigeHistorie() {

		if (historieListe.isEmpty()) {
			System.out.println("=== Historie ===");
			System.out.println("Keine Einträge vorhanden.");
			return;
		}

		System.out.println("=== Historie ===");
		linie();
		System.out.println("Produkt         | Status       | Benutzer       | Datum        | Uhrzeit");
		linie();

		for (HistorienEintrag eintrag : historieListe) {
			System.out.println(eintrag);
		}
		linie();
	}

	public ArrayList<Produkte> pruefeEntsorgung(LocalDate aktuellerTag) {
		ArrayList<Produkte> neueEntsorgungen = new ArrayList<>();

		for (Produkte p : produkte) {
			if (p.mussEntsorgungWerden(aktuellerTag)) {
				neueEntsorgungen.add(p);
			}
		}

		for (Produkte p : neueEntsorgungen) {
			entsorgteProdukte.add(p);
			historieListe.add(
					new HistorienEintrag(p, "SYSTEM", "ENTSORGT", LocalDateTime.of(aktuellerTag, LocalTime.of(0, 0))));
		}
		produkte.removeAll(neueEntsorgungen);
		return neueEntsorgungen;
	}

	public void zeigeEntsorgteProdukte() {

		if (entsorgteProdukte.isEmpty()) {
			System.out.println("=== Entsorgte Produkte ===");
			System.out.println("Keine entsorgten Produkte vorhanden.");
			return;
		}

		System.out.println("=== Entsorgte Produkte ===");

		for (Produkte entsorgteProdukt : entsorgteProdukte) {
			entsorgteProdukt.zeigeDetails();
		}
	}

	public void zeigeAlleProdukte() {
		if (produkte.isEmpty()) {
			System.out.println("Keine Produkte im Lager vorhanden.");
			return;
		}
		for (Produkte produkt : produkte) {
			produkt.zeigeDetails();
		}
	}

	public void zeigKaese() {
		for (Produkte produkt : produkte) {
			if (produkt instanceof Kaese) {
				produkt.zeigeDetails();
			}
		}

	}

	public void zeigWein() {
		for (Produkte produkt : produkte) {
			if (produkt instanceof Wein) {
				produkt.zeigeDetails();
			}
		}
	}

	public int anzahlEntsorgteProdukte() {
		int anzahl = 0;
		Iterator<Produkte> iterator = entsorgteProdukte.iterator();

		while (iterator.hasNext()) {
			iterator.next();
			anzahl++;
		}
		return anzahl; // oder kann auch return entsorgteProdukte.size();
	}

	public int anzahlProdukte() {
		return produkte.size();
	}

	public int anzahlKaese() {
		int anzahl = 0;
		Iterator<Produkte> iterator = produkte.iterator();

		while (iterator.hasNext()) {
			Produkte produkt = iterator.next();
			if (produkt instanceof Kaese) {
				anzahl++;
			}

		}
		return anzahl;
	}

	public int anzahlWein() {
		int anzahl = 0;

		Iterator<Produkte> iterator = produkte.iterator();

		while (iterator.hasNext()) {
			Produkte produkt = iterator.next();
			if (produkt instanceof Wein) {
				anzahl++;
			}
		}
		return anzahl;
	}

	public double lagerGesamtwert() {
		double gesamtwert = 0;

		for (Produkte produkt : produkte) {
			gesamtwert += produkt.getPreis();
		}
		return gesamtwert;
	}

	public double durchschnittlicheQualitaet() {

		int qualitaetSumme = 0;

		if (produkte.isEmpty())
			return 0;

		for (Produkte produkt : produkte) {
			qualitaetSumme += produkt.getQualitaet();
		}

		double durchschnitt = (double) qualitaetSumme / produkte.size();

		return durchschnitt;
	}

	private void linie() {
		System.out.println("=".repeat(80));
	}
}
