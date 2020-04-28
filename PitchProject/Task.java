import java.util.ArrayList;
import java.util.Map;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.util.View;
import javax.swing.*;

import static jm.constants.Durations.*;
import static jm.constants.Scales.*;
/**
 *  Klasa odpowiadająca wygenerowanemu zadaniu przez użytkownika. Zawiera funkcję checkNote(Integer note), w której jest sprawdzana poprawność zaśpiewanej nuty oraz nuta jest wyświetlana.
 *  Za każdą poprawną nutą zaśpiewaną przez użytkownika generuje się nowe okno z zaktualizowanym ciągiem nutowym. Korzysta z biblioteki jMusic.
 */
public class Task {
    ArrayList<Integer> task;
    Phrase p;
    Map<String, Integer> intervalToInt = Map.of("2",2,"3>",3,"3",4,"4",5,"4<",6,"5",7,"6>",8,"6",9, "7",10,"7<",11);
    int currentNote;
    Phrase answer;

    public Task(){
        task = new ArrayList<Integer>();
        p = new Phrase();
        currentNote = 0;
        answer = new Phrase();
    }

    public void display(Phrase p){
        runTask(p);
    }

    /**
     *  Powtórzenie zadania od początku.
     */
    void repeatNote(int n){
        currentNote = 0;
        answer = new Phrase();
    }

    /**
     *  Stworzenie zadania atonalnego.
     */
    Phrase createRandomTask(){
        clearTable(task);
        Phrase phr = new Phrase();
        Note n;
        for (int i = 0; i < 16; i++) {
            int rand = (int) (Math.random() * 10);
            n = new Note((int) (60 + rand), CROTCHET);
            addNotesToTask(n);
            phr.addNote(n);
        }
        p = phr;
        display(p);
      return phr;
    }

    /**
     *  Stworzenie własnego zadania. Należy podać ciąg nut w formacie MIDI, bez spacji. W przypadku nie wpisania powstanie pusta pięciolinia.
     */
    Phrase createCustomTask(String notes){
        Integer midi;
        clearTable(task);
        Phrase phr = new Phrase();
        Note n;
        if(notes != null) {
            for (int i = 0; i < notes.length(); i += 2) {
                midi = Integer.valueOf(notes.substring(0 + i, 2 + i));
                n = new Note((int) midi, CROTCHET);
                addNotesToTask(n);
                phr.addNote(n);
            }
        }
        display(phr);
        return phr;
    }

    /**
     *  Stworzenie zadania opartego na skali. W Przypadku nie podania skali stworzy ciąg w skali durowej.
     */
    Phrase createTaskBasedOnScale(String scale){
        clearTable(task);
        Phrase phr = new Phrase();
        Note n;
        int[] s = MAJOR_SCALE;
        if(scale.equals("MINOR_SCALE"))
            s = MINOR_SCALE;
        else if(scale.equals("MAJOR_SCALE"))
            s = MAJOR_SCALE;
        else if(scale.equals("DORIAN_SCALE"))
            s = DORIAN_SCALE;
        else if (scale.equals("BLUES_SCALE"))
            s = BLUES_SCALE;
        else if (scale.equals("LYDIAN_SCALE"))
            s = LYDIAN_SCALE;

        for (int i = 0; i < 16; i++) {
            int rand = (int) (Math.random() * 10);
            n = new Note((int) (60 + rand), CROTCHET);
            if (n.isScale(s) == true) {
                addNotesToTask(n);
                phr.addNote(n);
            }
        }
        p = phr;
        display(p);
        return phr;
    }

    /**
     *  Stworzenie zadania opartego na interwale. W przypadku nie podania interwału stworzy ciąg oparty na interwale sekundy małej.
     */
    Phrase createTaskBasedOnInterval(String interval){
        clearTable(task);
        Phrase phr = new Phrase();
        Note n, n1;
        int pitch = 60;
        int numericInterval = 2;
        if(!interval.isEmpty())
            numericInterval = this.intervalToInt.get(interval);
        System.out.println(interval+numericInterval);
        for (int i = 0; i < 4; i++) {
            int rand = (int) (Math.random() * 10);
            pitch = pitch + numericInterval;
            n1 = new Note((int) (pitch), CROTCHET);
            addNotesToTask(n1);
            phr.addNote(n1);
            pitch = pitch - rand;
            n = new Note((int) (pitch), CROTCHET);
            addNotesToTask(n);
            phr.addNote(n);
        }
        p = phr;
        display(p);
        return phr;
    }

    /**
     *  Wyświetlenie w formie nut zapisanej frazy.
     */
    void runTask(Phrase phrase){
        View.notate(phrase);
    }

    /**
     *  Dodanie nuty w formacie MIDI do listy.
     */
    ArrayList<Integer>addNotesToTask(Note note){
        this.task.add(note.getPitch());
        return this.task;
    }

    /**
     *  Czyszczenie listy.
     */
    public void clearTable(ArrayList<Integer> notes){
        notes.clear();
    }

    void goBackToNote(int index){
        currentNote = index;
    }

    /**
     *  Porównanie zarejestrowanej nuty z obecnie sprawdzaną w ciągu wygenerowanym. W przypadku błędu wyświetla się okno, które pyta
     *  użytkownika czy kontynuować zadanie.
     */
    public void checkNote(Integer note){
        System.out.println("note:"+note);
        System.out.println("current"+task.get(currentNote));
        if(task.get(currentNote)%12 == note%12){
            System.out.println("dobrze");
            currentNote++;
            answer.addNote(note,Q);
            display(answer);
        } else {
            System.out.println("zle");
            int option = JOptionPane.showConfirmDialog(null,
                    "Błędna odpowiedź! Chcesz kontunować?","Błąd!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if(option == JOptionPane.YES_OPTION) {
                currentNote++;
                answer.addNote(note,Q);
                display(answer);
            }
        }
    }

}
