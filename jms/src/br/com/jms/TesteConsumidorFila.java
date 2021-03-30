package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		
		Destination fila = (Destination) initialContext.lookup("financeiro");
		
		MessageConsumer messageConsumer = session.createConsumer(fila);
		
		messageConsumer.setMessageListener(message -> {
			TextMessage textMessage = (TextMessage) message;
			try {
				//message.acknowledge();
				System.out.println(textMessage.getText());
				session.rollback();
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
