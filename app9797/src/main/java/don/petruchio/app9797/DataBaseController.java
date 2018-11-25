package don.petruchio.app9797;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class DataBaseController {

    public static String dbName = "NumberDB";
    public static String tableName = "NumberTable";
    private final SimpleDateFormat format1 = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");



    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ArrayList<Record>> result(@RequestBody PageData payload)
    {

        ArrayList result=null;
        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.connect("localhost", "SA", "QWerTY123", dbName);
            dbHelper.createTable(dbName, tableName);
            if (payload.getName()==null && payload.getNumber()==null ||
                    payload.getName().isEmpty() && payload.getNumber().isEmpty()) {
                result = dbHelper.selectRecords(dbName,tableName);
                //System.out.println(result);
            }
            else
            {
                int number;
                try{
                    number = Integer.parseInt(payload.getNumber());
                }
                 catch (NumberFormatException e)
                {
                    return new ResponseEntity<ArrayList<Record>>(result, HttpStatus.NOT_ACCEPTABLE);
                }
                dbHelper.insertRecord(dbName, tableName,
                        new Record(payload.getName(),
                                RomanNumber.toRoman(number),
                                format1.format(new Date())));
            }
            dbHelper.closeConnection();
        } catch (SQLException e) {
            return new ResponseEntity<ArrayList<Record>>(result, HttpStatus.NO_CONTENT);
        } catch (ClassNotFoundException e) {
            return new ResponseEntity<ArrayList<Record>>(result, HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<ArrayList<Record>>(result, HttpStatus.OK);
    }

}
