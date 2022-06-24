import java.util.Date;

public class Odleglosci {
    int ilosc_kolumn=10;
    int SateliteNumber;
    String Date;
    double ha, hp,a,e,I,PerigeeArgument,Raan,MeanAnomaly;

    public Odleglosci(String dane_wejsciowe){
        String[] elementy = new String[ilosc_kolumn];
        int x=0;
        int i=0;
        String[] dane = dane_wejsciowe.split(" ");
        while(i!=ilosc_kolumn)
        {
            if(!dane[x].equals(""))
            {
                elementy[i]=dane[x];
                i++;
            }
            x++;
        }
        SateliteNumber = Integer.parseInt(elementy[0]);
        Date = elementy[1];
        ha = Double.parseDouble(elementy[2]);
        hp = Double.parseDouble(elementy[3]);
        a = Double.parseDouble(elementy[4]);
        e = Double.parseDouble(elementy[5]);
        I = Double.parseDouble(elementy[6]);
        PerigeeArgument = Double.parseDouble(elementy[7]);
        Raan = Double.parseDouble(elementy[8]);
        MeanAnomaly = Double.parseDouble(elementy[9]);


    }

    public void show()
    {
        System.out.println(
                "SateliteNumber: "+SateliteNumber+"\n" +
                        "Date: "+Date+"\n"+
                        "ha: "+ha+"\n"+
                        "hp: "+hp+"\n"+
                        "a: "+a+"\n"+
                        "e: "+e+"\n"+
                        "I: "+I+"\n"+
                        "PerigeeArgument: "+PerigeeArgument+"\n"+
                        "Raan: "+Raan+"\n"+
                        "MeanAnomaly: "+MeanAnomaly+"\n"
        );
    }
}