package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoEstoqueSelector {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topico = (Topic) initialContext.lookup("loja");
		
		MessageConsumer messageConsumer = session.createDurableSubscriber(topico, "assinatura-selector", "ebook is null OR ebook = false", false);
		
		/**
		 * Criando listener constante usando clambda
		 */
		messageConsumer.setMessageListener(message -> {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println(textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
			
		});

		new Scanner(System.in).nextLine();
		
		messageConsumer.close();
		session.close();
		connection.close();
		initialContext.close();
		
	}

}
