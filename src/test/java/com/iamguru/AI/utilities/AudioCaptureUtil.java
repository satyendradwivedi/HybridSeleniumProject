package com.iamguru.AI.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioCaptureUtil {

    private static TargetDataLine microphone;
    private static ExecutorService executor;
    private static Future<?> recordingTask;

    /**
     * Starts audio recording in a background thread.
     * Call this BEFORE clicking the Twin call button.
     * @param outputFilePath e.g. "src/test/resources/audio/twin_output.wav"
     */
    public static void startRecording(String outputFilePath) {
        executor = Executors.newSingleThreadExecutor();

        recordingTask = executor.submit(() -> {
            try {
                AudioFormat format = new AudioFormat(
                    44100f,  // sample rate
                    16,      // bit depth
                    1,       // channels (mono)
                    true,    // signed
                    false    // little-endian
                );

                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("[AudioCapture] Microphone line NOT supported on this machine.");
                    return;
                }

                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();

                System.out.println("[AudioCapture] Recording started → " + outputFilePath);

                // Create output directory if missing
                File outputFile = new File(outputFilePath);
                outputFile.getParentFile().mkdirs();

                AudioInputStream audioStream = new AudioInputStream(microphone);
                AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, outputFile);

            } catch (LineUnavailableException | IOException e) {
                System.err.println("[AudioCapture] Error: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Stops the audio recording.
     * Call this AFTER your Twin conversation completes.
     */
    public static void stopRecording() {
        if (microphone != null && microphone.isOpen()) {
            microphone.stop();
            microphone.close();
            System.out.println("[AudioCapture] Recording stopped successfully.");
        }
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    /**
     * Returns true if recording is currently active.
     */
    public static boolean isRecording() {
        return microphone != null && microphone.isOpen();
    }
}