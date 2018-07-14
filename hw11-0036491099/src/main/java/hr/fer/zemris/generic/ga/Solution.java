package hr.fer.zemris.generic.ga;

import hr.fer.zemris.generic.ga.api.GASolution;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Arrays;

public class Solution extends GASolution<int[]> {

    private int[] values;

    public Solution(int[] values) {
        super.data = values;
        this.values = values;
    }

    @Override
    public GASolution<int[]> duplicate() {
        return new Solution(Arrays.copyOf(values, values.length));
    }

    public int[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i: values){
            sb.append(i+"\n");
        }
        return sb.toString();
    }

    public static void play() {
        try {
            File file = new File("src/test/java/dugeNoge.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
