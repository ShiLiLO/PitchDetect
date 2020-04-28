import java.util.ArrayList;
/**
 *  Klasa odpowiadająca rejestrowanej częstotliwości przez mikrofon. Zawiera funkcje convertFreqToNote(float freq), która umożliwia przekonwertowanie częstotliwości na wartość midi nuty.
 *  Rzutuje zarejestrowany dźwięk na dźwięk z zakresu od A3 do A5.
 */
public class Frequency {
    public ArrayList<Float> freq;
    public float currentFreq;
    String[]notes;
    public Integer noteNameCPN;
    Task task;

    public Frequency(){
        freq = new ArrayList<Float>();
        task = new Task();
        currentFreq = 0;
        noteNameCPN = 0;
    }

    /**
     *  Czyści listę.
     */
    public void clearTable(ArrayList<Float> f){
        f.clear();
    }

    /**
     *  Oblicza średnią z zarejestrowanych częstotliwości - dla zmniejszenia błędu.
     */
    public void meanOfFreq(ArrayList<Float> f){
        float frequency = 0;
        for(int i =0; i<f.size(); i++)
            frequency += f.get(i);
        frequency = frequency/f.size();
        currentFreq = frequency;
        convertFreqToNote(currentFreq);
    }

    /**
     *  Dodanie częstotliwości do tablicy częstotliwości.
     */
    public void addToTable( float freq){
        this.freq.add(freq);
    }

    /**
     *  Przekształca częstotliwość na nutę w formacie MIDI.
     */
    public void convertFreqToNote(float freq){
        int result =0;
        result = (int)Math.round(12*Math.log(freq/440)/Math.log(2))+57;
        if(result > 81 || result < 57)
            noteNameCPN = result%12+60;
        else
            noteNameCPN = result;
        System.out.println("CPN:" + noteNameCPN);
    }
}
