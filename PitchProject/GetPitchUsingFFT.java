import ddf.minim.AudioListener;
import ddf.minim.analysis.FFT;

/**
 *  Klasa odpowiadająca za znalezienie głównej częstotliwości dźwięku. Korzysta z biblioteki minim.
 */
class GetPitchUsingFFT implements AudioListener {
  float sample_rate = 0;
  float current_frequency = 0;
  long t;
  
  FFT fft;

  /**
   *  Skonfigurowanie FFT, dobranie okna czasowego, ustawienie częstotliwości próbkowania.
   */
  void configureFFT (int bufferSize, float s) {
       fft = new FFT(bufferSize, s);
       fft.window(FFT.HAMMING);
       setSampleRate(s);
  }
  
  synchronized void storeFrequency(float f) {
    current_frequency = f;
  }
  
  synchronized float getFrequency() {
    return current_frequency;
  }
  
  void setSampleRate(float s) {
     sample_rate = s;
     t = 0;
  }
  
  public synchronized void samples(float[] samp) {
    calculateFFT(samp);
  }
  
  public synchronized void samples(float[] sampL, float[] sampR) {
    calculateFFT(sampL);
  }
  
  synchronized long getTime() {
    return t;
  }

  /**
   *  Wyszukiwanie głównej częstotliwości dźwięku. Podzielenie sygnału na mniejsze części, wyszukanie pasma z największą częstotliwością,
   *  obliczenie częstotliwości głównej.
   */
  void calculateFFT (float []audio) {
    t++;
    float highest = 0;
    int highest_bin = 0;
    fft.forward(audio);
    int max_bin =  fft.freqToIndex(10000.0f);
    for (int n = 0; n < max_bin; n++) {
       if (fft.getBand(n) > highest) {
         highest = fft.getBand(n);
         highest_bin = n;
       }
    }

    float freq = highest_bin * sample_rate / audio.length;
    storeFrequency(freq);

  }
};
