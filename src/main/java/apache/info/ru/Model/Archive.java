package apache.info.ru.Model;

import java.util.Date;

public class Archive {
    private int id;
    private String text;
    private Date date;

    public Archive(String text, Date date){
        this.text = text;
        this.date = date;
    }

    public Archive() {

    }

    public int getId(){ return id; }
    public void setId(int id) {this.id = id;}

    public String getText(){ return text; }
    public void setText(String text) {this.text = text;}

    public Date getDate(){ return date; }
    public void setDate(Date date) {this.date = date;}
}
