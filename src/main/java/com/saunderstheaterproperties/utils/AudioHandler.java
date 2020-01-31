package com.saunderstheaterproperties.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioHandler {

	private static void playClip(String filename) {
		try (InputStream in = new DummyClass().getClass().getResourceAsStream(filename)) {
			InputStream bufferedIn = new BufferedInputStream(in);
			try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param path
	 */
	public static void playAudioFile(String path) {
		playClip(path);
	}

}
