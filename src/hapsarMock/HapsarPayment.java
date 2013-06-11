package hapsarMock;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class HapsarPayment {
	
	 private final static String QUEUE_NAME = "hapsar-queue";
	 
	 private final static String BROKER_HOST = "localhost";

	  public static void main(String[] args) throws java.io.IOException {
		  
		  if (args.length == 2) {
			  // Connection to the server:
			  ConnectionFactory factory = new ConnectionFactory();
			  factory.setHost(BROKER_HOST);
			  Connection connection = factory.newConnection();
			  
			  // Create a channel
			  Channel channel = connection.createChannel();
			  
			  // Declare a queue
			  channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			  // Publish a message to the queue
			  String message = "meldeZahlungseingang%" + args[0] + "%" + args[1];
			  channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			  System.out.println(" [x] Sent '" + message + "'");
			  channel.close();
			  connection.close();
		  } else {
			  System.out.println("Usage: java HapsarPayment <Rechnungsnummer> <Betrag>");
		  }
	  }
}
