package kn.android.com.project;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class MainActivity extends Activity {

    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start,stop,play;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.button1);
        stop = (Button)findViewById(R.id.button2);
        play = (Button)findViewById(R.id.button3);

        stop.setEnabled(false);
        play.setEnabled(false);
        //http://stackoverflow.com/questions/20934924/audio-capture-without-overwriting-the-same-file-in-eclipse
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();

        //test comment
        Long tsLong = System.currentTimeMillis()/1000;

        outputFile += "/app/appRecording_"+tsLong.toString()+".wav";
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
    public void start(View view){
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

    public void stop(View view){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder  = null;
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
            SecurityException, IllegalStateException, IOException{

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }

  public void write (View view) {
  /*   String file_path1 = Environment.getExternalStorageDirectory().getAbsolutePath();
      File file = new File(file_path1 + "/test1.txt");


      try {
          DataInputStream dataInputStream = new DataInputStream(new FileInputStream(outputFile));
          DataInputStream dis = null;
          dis = new DataInputStream(new FileInputStream (outputFile));
          dis.read(outputFile.getBytes());
          if (dis !=null) {
              dis.close();
         }


      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }

      catch (IOException e) {
          e.printStackTrace();
      }

      DataOutputStream dos = null;
      try {
          dos = new DataOutputStream( new FileOutputStream (file) );
          dos.write(outputFile.getBytes());
      } catch (FileNotFoundException e) {
          System.out.println("File not found" + e);
      }

      catch (IOException ioe) {
          System.out.println("Exception while writing file " + ioe);
      } finally {
          try {
              if (dos != null) {
                  dos.close();
              }

          } catch (IOException ioe) {
              System.out.println("Error while closing stream " + ioe);
          }
      }*/
     /* String file_path1 = Environment.getExternalStorageDirectory().getAbsolutePath();
      File file = new File(file_path1 + "/bintest1.txt");
      try {
          FileOutputStream  fileOs = new FileOutputStream(file);
          ObjectOutputStream os = new ObjectOutputStream(fileOs);
          os.write(Integer.parseInt(outputFile));
          os.writeDouble(3.1415);
          os.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }*/


      Toast.makeText(getApplicationContext(), "Data been written to file", Toast.LENGTH_LONG).show();
  }
}