package db.mysql;

public class MySQLDBUtil {
	private static final String HOSTNAME = "localhost";
	private static final String PORT_NUM = "8889"; // change it to your mysql port number
	public static final String DB_NAME = "laiproject";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	public static final String URL = "jdbc:mysql://"
			+ HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
			+ "?user=" + USERNAME + "&password=" + PASSWORD
			+ "&autoReconnect=true&serverTimezone=UTC";

}
