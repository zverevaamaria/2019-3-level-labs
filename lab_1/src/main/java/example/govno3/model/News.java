package example.govno3.model;

import java.util.Date;

public class News {
        public int counter;
        public String tittle;

        public News() {

        }

        public News(int counter, String tittle) {
            this.counter = counter;
            this.tittle = tittle;
        }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getTittle() {
        return tittle;
    }

    public int getCounter() {
        return counter;
    }
}

