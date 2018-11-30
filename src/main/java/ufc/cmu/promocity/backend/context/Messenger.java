package ufc.cmu.promocity.backend.context;

import ufc.cmu.promocity.backend.model.User;

/**
 * Classe que envia uma mensagem para o usuario
 * @author armandosoaressousa
 *
 */
public class Messenger {
	public void send(User user, Object content) {
		System.out.println("Mensagem enviada" + user.getUsername());
	}

	/**
	 * Send a message to user
	 * @param user user
	 * @param coupon Coupon
	 * @param content Content of message
	 */
	public void sendPromotion(String userId, Object coupon, Object content) {
		System.out.println("Mensagem enviada" + userId);
	}

}
