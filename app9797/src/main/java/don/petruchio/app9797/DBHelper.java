package don.petruchio.app9797;

import java.sql.*;
import java.util.ArrayList;

public class DBHelper {

    private Connection connection;


    public DBHelper()
    {

    }

    public boolean connect(String serverName, String login,String password,String dbName) throws SQLException, ClassNotFoundException
    {
        String connectionUrl = "jdbc:sqlserver://%1$s;user=%2$s;password=%3$s;";
        String connectionString = String.format(connectionUrl, serverName,login, password);

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(connectionString);
        Statement statement = connection.createStatement();
        statement.execute("iF NOT EXISTS" +
                "   (  SELECT [name]" +
                "      FROM sys.databases" +
                "      WHERE [name] ='"+ dbName+"' " +
                "   )" +
                "   CREATE DATABASE "+ dbName);
        if (statement != null)
            try { statement.close(); }
            catch (SQLException ignore) { return false; }
        else
            return false;
        return true;
    }


    public boolean createTable(String dbName,String tableName) throws SQLException
    {
        Statement statement = connection.createStatement();
        statement.execute("iF NOT EXISTS" +
                "   (  SELECT [table_name]" +
                "      FROM "+dbName+".information_schema.tables" +
                "      WHERE [table_name] ='"+ tableName +"' "+
                "   )" +
                "   CREATE TABLE "+dbName+".dbo."+ tableName+
                "(id int identity(1,1) primary key," +
                "   name varchar (100)," +
                "   number varchar (100)," +
                "   date varchar (100));");
        if (statement != null)
            try { statement.close(); }
            catch (SQLException ignore) { }
            else
                return false;
        return true;
    }

    public boolean insertRecord(String dbName,String tableName,Record record) throws SQLException
    {
        Statement statement = connection.createStatement();
        statement.execute("insert into "+dbName+".dbo."+tableName+"(name,number,date) " +
                "values('"+record.getName()+"','"+record.getNumber()+"','"+record.getDate()+"')");
        if (statement != null)
            try { statement.close(); }
            catch (SQLException ignore) { }
            else return false;
            return true;
    }


    public ArrayList<Record> selectRecords(String dbName, String tableName) throws SQLException
    {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from "+dbName+".dbo."+tableName);
        ArrayList<Record> records = new ArrayList<Record>();
        while (rs.next()) {
            records.add(new Record(rs.getString("name"),
                    rs.getString("number"),
                    rs.getString("date")));
        }
        if (rs != null)
            try { rs.close(); }
            catch (SQLException ignore) { }
        if (statement != null)
            try { statement.close(); }
            catch (SQLException ignore) { }
        return records;
    }

    public void dropTable(String dbName,String tableName) throws SQLException
    {
        Statement statement = connection.createStatement();
        statement.execute("IF OBJECT_ID("+dbName+"'dbo."+tableName+"', 'U') IS NOT NULL \n" +
                "  DROP TABLE dbo."+tableName+"; ");
        if (statement != null)
            try { statement.close(); }
            catch (SQLException ignore) { }

    }


    public void closeConnection() throws SQLException
    {
        if (connection != null) connection.close();
    }

}
