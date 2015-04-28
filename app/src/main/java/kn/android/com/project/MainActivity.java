package kn.android.com.project;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class MainActivity extends Activity {

    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, stop, play;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);

        stop.setEnabled(false);
        play.setEnabled(false);
        //http://stackoverflow.com/questions/20934924/audio-capture-without-overwriting-the-same-file-in-eclipse
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();

        //test comment
        Long tsLong = System.currentTimeMillis() / 1000;

        outputFile += "/app/appRecording_" + tsLong.toString() + ".wav";
        //end here

        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath();


        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setAudioChannels(1);
        myAudioRecorder.setAudioSamplingRate(8000);
        myAudioRecorder.setAudioEncodingBitRate(16000);
        myAudioRecorder.setOutputFile(outputFile);

    }

    //method to start record
    public void start(View view) {
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();


    }

    public void stop(View view) {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }

    public void readToFile(View view) {
    /*
    Title Java File: Reading and Writing Files in Java
    Author John Purcell
    Site name caveofprogramming.com
    Date 2015
    Availability https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html (Accessed 28 April 2015)
     */
      String fileName = outputFile;

        try {
            byte[] buffer = new byte[1000];
            FileInputStream inputStream = new FileInputStream(fileName);
            int total = 0;
            int nRead = 0;
            while ((nRead = inputStream.read(buffer)) !=-1){
                System.out.println(new String(buffer));
                total += nRead;
            }
            inputStream.close();
            Toast.makeText(getApplicationContext(), String.format("Read %d bytes", total), Toast.LENGTH_LONG).show();
           // System.out.println("Read " + total + " bytes");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
        public void writeToFile (View view) {
            String file_path1 = Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName1 = file_path1 + "/test4.txt";
            try {
                String bytes = outputFile;
                byte[] buffer = bytes.getBytes();
                FileOutputStream outputStream = new FileOutputStream(fileName1);
                outputStream.write(buffer);
                outputStream.close();
                Toast.makeText(getApplicationContext(), "Wrote " + buffer.length + " bytes", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Toast.makeText(getApplicationContext(), "Data been written to file", Toast.LENGTH_LONG).show();
        }

    }
