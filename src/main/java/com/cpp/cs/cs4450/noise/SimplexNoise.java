/***************************************************************
 * file: SimplexNoise.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Used to generate game's terrain
 *
 ****************************************************************/
package com.cpp.cs.cs4450.noise;

import java.util.Random;

public class SimplexNoise {

    SimplexNoiseOctave[] octaves;
    double[] frequencies;
    double[] amplitudes;

    //int largestFeature;
    double persistence;
    int seed;

    public SimplexNoise(int largestFeature, double persistence, int seed) {
        //this.largestFeature = largestFeature;
        this.persistence = persistence;
        this.seed = seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves = (int) Math.ceil(Math.log10(largestFeature) / Math.log10(2));

        octaves = new SimplexNoiseOctave[numberOfOctaves];
        frequencies = new double[numberOfOctaves];
        amplitudes = new double[numberOfOctaves];

        Random rnd = new Random(seed);

        for (int i = 0; i < numberOfOctaves; i++) {
            octaves[i] = new SimplexNoiseOctave(rnd.nextInt());

            frequencies[i] = Math.pow(2, i);
            amplitudes[i] = Math.pow(persistence, octaves.length - i);
        }
    }

    public double getNoise(int x, int y) {

        double result = 0;

        for (int i = 0; i < octaves.length; i++) {
            //double frequency = Math.pow(2,i);
            //double amplitude = Math.pow(persistence,octaves.length-i);

            result += octaves[i].noise(x / frequencies[i], y / frequencies[i]) * amplitudes[i];
        }
        return result;
    }

    public double getNoise(int x, int y, int z) {

        double result = 0;

        for (int i = 0; i < octaves.length; i++) {
            double frequency = Math.pow(2, i);
            double amplitude = Math.pow(persistence, octaves.length - i);

            result +=octaves[i].noise(x / frequency, y / frequency, z / frequency) * amplitude;
        }
        return result;
    }

}
