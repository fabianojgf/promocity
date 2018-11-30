package ufc.cmu.promocity.backend.service.exceptions;

/**
 * Classe de Excecao de Livro
 * @author armandosoaressousa
 *
 */
public class BookNotFoundException extends RuntimeException {

	/**
	 * Livro nao existe
	 * @param message
	 */
	public BookNotFoundException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8069763198347163909L;

}