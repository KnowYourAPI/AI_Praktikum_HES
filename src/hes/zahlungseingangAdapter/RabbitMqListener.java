package hes.zahlungseingangAdapter;

import java.io.IOException;
import java.rmi.RemoteException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMqListener implements IMessageQueueListener{
	
	private Connection connection;
	
	private Channel channel;
	
	private QueueingConsumer consumer;
	
	private boolean running;
	
	private IZahlungseingangAdapter zahlungseingangsAdapter;
	
	public RabbitMqListener(String queueName, String brokerHost, IZahlungseingangAdapter zahlungseingangsAdapter) {
		this.running = true;
		this.zahlungseingangsAdapter = zahlungseingangsAdapter;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(brokerHost);
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare(queueName, false, false, false, null);
			
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException e) {
			System.err.println("Es ist ein Fehler bei der Verbindungsherstellung zum Hapsar-System aufgetreten.");
		}
	}
	
	@Override
	public void run() {
		while (running) {
			//consumer.nextDelivery() blocks until a message has been delivered
			QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println(" [x] Received '" + message + "'");
				
				String[] messageAry = message.split("%");
				if (messageAry[0].equalsIgnoreCase("meldeZahlungseingang")) {
					int rechnungId = Integer.parseInt(messageAry[1]);
					float betrag = Float.parseFloat(messageAry[2]);
					zahlungseingangsAdapter.meldeZahlungsEingang(rechnungId, betrag);
				} else {
					System.err.println("Es wurde eine fehlerhafte Nachricht empfangen: " + message);
				}
			} catch (ShutdownSignalException e) {
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		running = false;
		try {
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
