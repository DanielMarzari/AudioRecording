package audiolistener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;

//import javaFlacEncoder.FLACFileWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class recordAudio {
    audioSettings as = new audioSettings();
    SourceDataLine audioLine;
    AudioInputStream audioInputStream;
    TargetDataLine recordLine;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int frameSizeInBytes = as.format.getFrameSize();
    int bufferLengthInFrames, bufferLengthInBytes;
    byte[] data;
    int numBytesRead;
        
    public void testCapture() throws Exception {
        if (!AudioSystem.isLineSupported(as.recordInfo)) {
            System.out.println("Line matching " + as.recordInfo + " not supported.");
            return;
        }
        try {
            recordLine = (TargetDataLine) AudioSystem.getLine(as.recordInfo);
            recordLine.open(as.format, recordLine.getBufferSize());
        } catch (Exception ex) { 
                System.out.println(ex.toString());
            return;
        }

       bufferLengthInFrames = recordLine.getBufferSize() / 8;
       bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
       data = new byte[bufferLengthInBytes];
       
        recordLine.start();
        System.out.println("Starting record");
        
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + (1000 * as.recordTime);
        while (System.currentTimeMillis() <= endTime) {
            if((numBytesRead = recordLine.read(data, 0, bufferLengthInBytes)) == -1) {
                break;
            }
            out.write(data, 0, numBytesRead);
        }
        
        System.out.println("Stopping record");
        recordLine.stop();
        recordLine.close();
        recordLine = null;
        
        try {
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        byte audioBytes[] = out.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        //stdAudio sa = new stdAudio();

        audioLine = (SourceDataLine) AudioSystem.getLine(as.playInfo);
        audioLine.open(as.format, audioBytes.length);
        audioLine.start();
        audioLine.write(audioBytes, 0, audioBytes.length);

        audioInputStream = new AudioInputStream(bais, as.format, audioBytes.length / frameSizeInBytes);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(as.path));
    }
}
