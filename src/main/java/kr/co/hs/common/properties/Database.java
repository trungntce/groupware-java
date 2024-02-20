package kr.co.hs.common.properties;

@SuppressWarnings("SpellCheckingInspection")
public class Database {

	public final static String DRIVER = "org.mariadb.jdbc.Driver";
	public final static String DRIVER_REAL = "org.mariadb.jdbc.Driver";

// 오라클
	//public final static String DRIVER = "core.log.jdbc.driver.OracleDriver";
	//public final static String DRIVER_REAL = "oracle.jdbc.driver.OracleDriver";
	//public final static String URL_TEST= "jdbc:oracle:thin:@54.178.27.60:1521:PSORACLE";
	//public final static String URL_LOCAL_SE = "jdbc:oracle:thin:@54.178.27.60:1521:PSORACLE";
	//public final static String USER = "sample";
	//public final static String USER = "root";
	//public final static String PASSWORD = "sample123!@#";
	//public final static String PASSWORD = "1234";
	//public final static String DESCRIPTION = "SAMPLE";
	// 서버
//	 public final static String URL_TEST= "jdbc:mariadb://localhost:3306/hsgw";
	 public final static String URL_LOCAL_SE = "jdbc:mariadb://localhost:3307/hsgw?allowMultiQueries=true";
	 public final static String USER = "root";
	 public final static String PASSWORD = "root";
	 public final static String DESCRIPTION = "hsgw";
	// 로컬DB
//		public final static String URL_LOCAL_SE = "jdbc:mariadb://localhost:3307/hsgw";
//		public final static String USER = "root";
//		public final static String PASSWORD = "1234";
//		public final static String DESCRIPTION = "hsgw";
	// 대구DB
//	public final static String URL_LOCAL_SE = "jdbc:mariadb://101.101.209.212:3306/hsgw?allowMultiQueries=true";
//	public final static String USER = "root";
//	public final static String PASSWORD = "leesin2018!";
//	public final static String DESCRIPTION = "hsgw";

//	public final static String URL_LOCAL_SE = "jdbc:mariadb://192.168.1.254:3307/hsgw?allowMultiQueries=true";
//	public final static String USER = "root";
//	public final static String PASSWORD = "1234";
//	public final static String DESCRIPTION = "hsgw";


	public final static Integer LOGIN_TIME_OUT = 2000;
	public final static Integer INITIAL_POOL_SIZE = 4;
	public final static Integer MAX_POOL_SIZE = 30;
	public final static Integer MIN_POOL_SIZE = 4;
	public final static Integer ACQUIRE_INCREMENT = 1;
	public final static Integer ACQUIRE_RETRY_ATTEMPTS = 5;
	public final static Integer ACQUIRE_RETRY_DELAY = 1000;
	public final static Integer IDLE_CONNECTION_TEST_PERIOD = 60;
}
