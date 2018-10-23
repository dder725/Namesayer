package GUI;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
/**
 * This open source code was modified for the NameSayer project.
 * Source link: https://github.com/tel3342311/JavaFX-Sound-Test
 * @author tel3342311
 *
 */
public class Recorder implements Runnable {
	final ProgressBar meter;

	Recorder(final ProgressBar meter) {
		this.meter = meter;
	}

	@Override
	public void run() {
		AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
		final int bufferByteSize = 2048;
		Line lineMic = null;
		try {
			lineMic = getLine();
		} catch (LineUnavailableException ex) {
			Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
		}
		TargetDataLine line = (TargetDataLine) lineMic;

		try {
			line.open(fmt, bufferByteSize);
		} catch (LineUnavailableException e) {
			System.err.println(e);
			return;
		}

		byte[] buf = new byte[bufferByteSize];
		float[] samples = new float[bufferByteSize / 2];

		float lastPeak = 0f;

		line.start();
		for (int b; (b = line.read(buf, 0, buf.length)) > -1;) {

			// Convert bytes to samples here
			for (int i = 0, s = 0; i < b;) {
				int sample = 0;

				sample |= buf[i++] & 0xFF; // (reverse these two lines
				sample |= buf[i++] << 8; // if the format is big endian)

				// normalize to range of +/-1.0f
				samples[s++] = sample / 32768f;
			}

			float rms = 0f;
			float peak = 0f;
			for (float sample : samples) {

				float abs = Math.abs(sample);
				if (abs > peak) {
					peak = abs;
				}

				rms += sample * sample;
			}

			rms = (float) Math.sqrt(rms / samples.length);

			if (lastPeak > peak) {
				peak = lastPeak * 0.875f;
			}

			lastPeak = peak;

			setMeterOnEDT(rms, peak);
		}
	}

	void setMeterOnEDT(final float rms, final float peak) {
		Platform.runLater(() -> {
			ObservableValue<Float> obsRms = new SimpleFloatProperty(Math.abs(rms)).asObject();
			meter.progressProperty().bind(obsRms);
		});
	}

	private TargetDataLine getLine() throws LineUnavailableException {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info info : mixerInfos) {
			Mixer m = AudioSystem.getMixer(info);
			Line.Info[] lineInfos = m.getSourceLineInfo();

			lineInfos = m.getTargetLineInfo();
			int i = 0;
			for (Line.Info lineInfo : lineInfos) {

				if (lineInfo.getLineClass().equals(TargetDataLine.class)) {
					Line line = m.getLine(lineInfo);
					if (i == 0) {
						return (TargetDataLine) AudioSystem.getLine(lineInfo);
					}
					i++;
				}
			}

		}
		return null;
	}
}
