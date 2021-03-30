package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.caelum.modelo.Pedido;

public class TesteConsumidorTopicoComercial {

	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		
		((ActiveMQConnectionFactory) factory).setTrustAllPackages(true);
		
		Connection connection = factory.createConnection();
		connection.setClientID("comercial");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topico = (Topic) initialContext.lookup("loja");
		
		MessageConsumer messageConsumer = session.createDurableSubscriber(topico, "assinatura");
		
		/**
		 * Criando listener constante usando clambda
		 */
		messageConsumer.setMessageListener(message -> {
			ObjectMessage objectMessage = (ObjectMessage) message;
			try {
				Pedido pedido = (Pedido) objectMessage.getObject();
				System.out.println(pedido);
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
