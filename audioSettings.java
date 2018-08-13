/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audiolistener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Daniel
 */
public class audioSettings {
    float sampleRate = 44100;
    int sampleSizeInBits = 16;
    int channels = 2;
    int frameSize = (sampleSizeInBits/8)*channels;
    float frameRate = sampleRate;
    boolean bigEndian = false;
    int recordTime = 3;
    AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, sampleSizeInBits, channels, frameSize , frameRate, bigEndian);
    DataLine.Info recordInfo = new DataLine.Info(TargetDataLine.class, format);
    DataLine.Info playInfo = new DataLine.Info(SourceDataLine.class, format);
    String path = "C:\\tmp\\test.wav";
}
