package don.petruchio.app9797;

public class Record {

    private String name;
    private String number;
    private String date;


    public Record()
    {

    }

    public Record(String name, String number, String date)
    {
        this.name = name;
        this.number = number;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getNumber() {
        return number;
    }
}
