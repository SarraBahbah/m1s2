package ftp.command;

import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import ftp.FTPDatabase;
import ftp.FTPMessageSender;
import ftp.configuration.FTPClientConfiguration;

/**
 * Class representing the PASV command
 */
public class FTPPasvCommand extends FTPMessageSender implements FTPCommand {

	/**
	 * constructs a PASV command
	 * 
	 * @param database
	 *            the database
	 */
	public FTPPasvCommand(FTPDatabase database) {
		super(database);
	}

	@Override
	public boolean accept(String command) {
		return command.equals("PASV");
	}

	@Override
	public void execute(String argument,
			FTPClientConfiguration clientConfiguration) {
		if (!clientConfiguration.isConnected()) {
			sendCommand(clientConfiguration.getConnection(), 530);
			return;
		}
		final Object[] answerTokens = new String[6];
		final String ipAddress = getDatabase().getHostAddress();
		final StringTokenizer tokenizer = new StringTokenizer(ipAddress, ".");
		for (int i = 0; i < 4; i++) {
			answerTokens[i] = tokenizer.nextToken();
		}
		int port = 21;
		answerTokens[4] = (port / 256) + "";
		answerTokens[5] = Integer.toHexString(port % 256);
		sendCommand(clientConfiguration.getConnection(),
				227, answerTokens);
		try {
			final Socket dataSocket = new Socket(
					InetAddress.getByName(ipAddress), port);
			clientConfiguration.setDataSocket(dataSocket);
		} catch (Exception e) {

		}
	}

}
