package com.cat.ca;
/** this class is to encapsulate the data of a cat fact into
 * a reusable and manageable object**/
public class CatFact {
    private String fact;
    private int length;

    public CatFact() {

    }
    public  String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "CatFact{" +
                "fact='" + fact + '\'' +
                ", length=" + length +
                '}';
    }
}
