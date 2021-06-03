package com.hc.ipmdroid20.api.server;

import android.view.View;

import com.hc.ipmdroid20.MainActivity;
import com.hc.ipmdroid20.api.models.Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Credentials {
    private static final String CREDENTIALS_FILE_NAME = "credentials.txt";

    public Credentials() {}

    public void saveServers(ArrayList<Server> servers) throws IOException {
        View view = MainActivity.getView();
        FileOutputStream fos = null;
        try {
            fos = view.getContext().openFileOutput(CREDENTIALS_FILE_NAME, view.getContext().MODE_PRIVATE);
            String toWrite = "";
            fos.write(toWrite.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public void restoreServers() throws IOException {
        View view = MainActivity.getView();
        FileInputStream fis = null;
        String text = "";

        try {
            fis = view.getContext().openFileInput(CREDENTIALS_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            text = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        String[] c = text.split("");

        if (c.length != 0) {
            // Do something
        }
    }
}
