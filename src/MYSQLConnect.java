import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.sql.*;


public class MYSQLConnect {
    public String Login;
    public String MotDePasse;
    private boolean Logged = false;
    static Connection connection = null;
    static Statement stmt = null;

    MYSQLConnect() throws ClassNotFoundException, SQLException {
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName); // here is the ClassNotFoundException

        String serverName = "127.0.0.1";
        String mydatabase = "kafka";
        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);

    }

    public static void insert( long offset, Date date, String topic,long partition) throws ClassNotFoundException, SQLException {
        // the mysql insert statement
        Timestamp ts=new Timestamp(date.getTime());
//        Instant instant = date.toInstant();

//        String query = " insert into kafka_table (offsetTime,consumer,offset, topic, parti)"
//                + " values (FROM_UNIXTIME(?*0.001),?,?,?,?)";
        String query = " insert into kafka_table (offsetTime,offset, topic, parti)"
                + " values (?,?,?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setBigDecimal(2, BigDecimal.valueOf(offset));
        preparedStmt.setTimestamp(1, ts);
        preparedStmt.setBigDecimal(4, BigDecimal.valueOf(partition));
        preparedStmt.setString(3, topic);
//        preparedStmt.setObject(1, instant.toEpochMilli());

        // execute the preparedstatement
        System.out.println("prepared statement "+preparedStmt.toString());
        preparedStmt.execute();
        System.out.println("inserted!!!");
    }


//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        StartBdd();
//    }

//    public static void insert(String consumer, long offset, Date date, long partition, String topic) throws ClassNotFoundException, SQLException {
//        // the mysql insert statement
//        Timestamp ts=new Timestamp(date.getTime());
//        String query = " insert into kafka_table (consumer,offset,offsetTime,partition,topic)"
//                + " values (?,?,?,?,?)";
//
//        // create the mysql insert preparedstatement
//        PreparedStatement preparedStmt = connection.prepareStatement(query);
//        preparedStmt.setString(1, consumer);
//        preparedStmt.setLong(2, offset);
//        preparedStmt.setTimestamp(3, ts);
//        preparedStmt.setLong(4, partition);
//        preparedStmt.setString(5, topic);
//
//        // execute the preparedstatement
////        preparedStmt.execute();
//
//
//
//        //Create statement
//        stmt = connection.createStatement();
//
////        //Insert first reocod
////        String record1=" insert into kafka_table (consumer,offset,offsetTime,partition,topic)"
////                + " values ("+consumer+","+offset+","+ts+","+partition+","+topic+")";
////        stmt.executeUpdate(record1);
//
//        String record2 ="INSERT  into kafka_table (consumer,offset,offsetTime,partition,topic) "
//                + "VALUES ("+consumer+","+offset+","+ts+","+partition+","+topic+")";
////        stmt.executeUpdate(record2);
//        String record3="INSERT  into kafka_table (consumer) "
//                + "VALUES (877gg667)";
//        stmt.executeUpdate(record3);
//        System.out.println("inserted!!!");
//
//
//
////        connection.close();
//
//    }
}