package ufc.cmu.promocity.backend.report;

import java.util.LinkedList;
import java.util.List;

public class ReportApplication {
	private List<Object> detalhes;

	public ReportApplication() {
		this.detalhes = new LinkedList<Object>();
	}
	
	public List<Object> getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(List<Object> detalhes) {
		this.detalhes = detalhes;
	}
	
	/**
	 * Adiciona um novo detalhe na lista de detalhes da aplicação
	 * @param value String ou Object que representa os dados do detalhe
	 */
	public void addDetalhe(Object value) {
		this.detalhes.add(value);
	}
}
