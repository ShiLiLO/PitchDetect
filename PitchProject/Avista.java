import processing.core.PApplet;
import ddf.minim.*;

import java.util.ArrayList;
/**
 *  Główna klasa programu. Korzysta z biblioteki minim oraz Processing. Dziedziczy po klasie PApplet. Zawiera funkcje setup(), draw() oraz close().
 */
public class Avista extends PApplet {

    public static void main(String[] args){
        PApplet.main("Avista", args);
    }

    GetPitchUsingFFT PD;
    ToneGenerator TG;
    AudioSource AS;
    Minim minim;
    GUI gui;
    Frequency freq;
    Task task;
    ArrayList<Float> fr= new ArrayList<Float>();

    long last_t = -1;
    float avg_level = 0;
    float last_level = 0;
    float begin_playing_time = -1;

    public static boolean startDrawing = false;

    public void setup()
    {
        minim = new Minim(this);
        //minim.debugOn();
        task = new Task();
        AS = new AudioSource(minim);
        AS.OpenMicrophone();
        PD = new GetPitchUsingFFT();
        PD.configureFFT(2048, AS.GetSampleRate());
        PD.setSampleRate(AS.GetSampleRate());
        AS.SetListener(PD);
        TG = new ToneGenerator (minim, AS.GetSampleRate());
        freq = new Frequency();
        gui = new GUI(this);
        rectMode(CORNERS);
        background(255,255,255);
        fill(0);
        stroke(255);
    }

    public void settings() {
        size(600, 500, "processing.opengl.PGraphics2D");
    }

    /**
     *  Rysowanie zarejestrowanych częstotliwości w czasie. Dla celów testowych.
     */
    public void draw(){
        if(startDrawing == true) {
            if (begin_playing_time == -1)
                begin_playing_time = millis();

            float f = 0;
            float level = AS.GetLevel();
            float h;
            long t = PD.getTime();
            if (t == last_t) return;
            last_t = t;
            int xpos = (int) t % width;
            if (xpos >= width - 1) {
                rect(0, 0, width, height);
            }

            f = PD.getFrequency();
            if(f != 0) {
                TG.SetFrequency(f);
                TG.SetLevel((float) (level * 10.0));

                stroke((float) (level * 255.0 * 10.0));
                h = f * 1.021678f;
                freq.addToTable(h);
                System.out.printf(h + "FREQ" + "%n");

                line(xpos, height, xpos, height - f / 5);

                avg_level = level;
                last_level = f;
            } else {
                if (!freq.freq.isEmpty()) {
                    freq.meanOfFreq(freq.freq);
                    task.checkNote(freq.noteNameCPN);
                    freq.clearTable(freq.freq);
                }
            }
        }
    }

    public void stop()
    {
        TG.Close();
        AS.Close();
        minim.stop();
        super.stop();
    }

}
