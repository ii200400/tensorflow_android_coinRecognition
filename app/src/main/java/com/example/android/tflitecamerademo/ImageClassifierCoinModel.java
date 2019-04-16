package com.example.android.tflitecamerademo;

import android.app.Activity;

import java.io.IOException;

/**
 * Created by im on 2019-04-16.
 * copy other classifier like 'ImageClassifierFloatMovileNet'
 */

public class ImageClassifierCoinModel extends ImageClassifier {
    private float[][] labelProbArray = null;

    /**
     * Initializes an {@code ImageClassifierFloatMobileNet}.
     *
     * @param activity
     */
    ImageClassifierCoinModel(Activity activity) throws IOException {
        super(activity);
        labelProbArray = new float[1][getNumLabels()];
    }

    @Override
    protected String getModelPath() {
        return "coin_model.tflite";
    }

    @Override
    protected String getLabelPath() {
        return "labels_coinmodel.txt";
    }

    @Override
    protected int getImageSizeX() {
        return 128;
    }

    @Override
    protected int getImageSizeY() {
        return 128;
    }

    @Override
    protected int getNumBytesPerChannel() {
        return 4; // Float.SIZE / Byte.SIZE;
    }

    @Override
    protected void addPixelValue(int pixelValue) {
        imgData.putFloat(((pixelValue >> 16) & 0xFF) / 255.f);
        imgData.putFloat(((pixelValue >> 8) & 0xFF) / 255.f);
        imgData.putFloat((pixelValue & 0xFF) / 255.f);
    }

    @Override
    protected float getProbability(int labelIndex) {
        return labelProbArray[0][labelIndex];
    }

    @Override
    protected void setProbability(int labelIndex, Number value) {
        labelProbArray[0][labelIndex] = value.floatValue();
    }

    @Override
    protected float getNormalizedProbability(int labelIndex) {
        return labelProbArray[0][labelIndex];
    }

    @Override
    protected void runInference() {
        tflite.run(imgData, labelProbArray);
    }
}
