package Kaese_und_wein_verwaltung;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistorienEintrag {

	private Produkte produkt;
	private String benutzername;
	private String aktion;
	private LocalDateTime zeitpunkt;

	public HistorienEintrag(Produkte produkt, String benutzername, String aktion, LocalDateTime zeitpunkt) {

		this.produkt = produkt;
		this.benutzername = benutzername;
		this.aktion = aktion;
		this.zeitpunkt = zeitpunkt;
	}

	public Produkte getProdukt() {
		return produkt;
	}

	public String getBenutzername() {
		return benutzername;
	}

	public String getAktion() {
		return aktion;
	}

	public LocalDateTime getZeitpunkt() {
		return zeitpunkt;
	}

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

	@Override
	public String toString() {

		String datum = zeitpunkt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		String uhrzeit = zeitpunkt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		return String.format("%-15s | %-12s | %-15s | %-12s | %-10s", 
				produkt.getBezeichnung(), 
				aktion, 
				benutzername,
				datum, uhrzeit);
	}
}