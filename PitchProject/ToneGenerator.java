import ddf.minim.*;
import ddf.minim.signals.TriangleWave;
/**
 *  Klasa odpowiadająca tonu wyjściowemu. Korzysta z biblioteki minim.
 */
class ToneGenerator
{
  Minim minim;
  AudioOutput out;
  TriangleWave wav;
  ToneGenerator(Minim m, float sampleRate) {
    this.minim = m;
    
    out = minim.getLineOut(Minim.MONO, 512, sampleRate);
    
    wav = new TriangleWave(100, (float) 0.5, out.sampleRate());
    out.addSignal(wav);
  }

  void Close() {
    out.close();
  }

  /**
   *  Ustawienie częstotliwości.
   */
  void SetFrequency(float f) {
     wav.setFreq(f);
  }

  /**
   *  Ustawienie amplitudy.
   */
  void SetLevel(float l) {
	 wav.setAmp(l);
  }
};
