import ddf.minim.*;
/**
 *  Klasa odpowiadająca za ustawienie elementów potrzebnych do rejestrowania dźwięku. Ustawia sampleRate mikrofonu na 441kHz. Korzysta z biblioteki minim.
 */
class AudioSource
{
  Minim minim;
  AudioInput microphone;


  AudioSource(Minim m) {
    this.minim = m;
}

    void Close() {
        microphone.close();
    }

    void SetListener(AudioListener listener) {
        microphone.addListener (listener);
    }

    void OpenMicrophone () {
        microphone = minim.getLineIn(Minim.MONO, 2048, 44100);
    }

    /**
     *  Ustawia częstotliwość próbkowania dla mikrofonu.
     */
    float GetSampleRate() {
        return microphone.sampleRate();
    }

    /**
     *  Ustawia amplitudę.
     */
    float GetLevel() {
        return microphone.mix.level();
    }
};
