package com.hc.ipmdroid20.api.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hc.ipmdroid20.App;
import com.hc.ipmdroid20.api.models.Server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Credentials.
 */
public class Credentials {
    private static final String CREDENTIALS_FILE_NAME = "credentials.json";

    public Credentials() {}

    /**
     * Save the current servers in the json file.
     * @param servers
     */
    public static void saveServers(ArrayList<Server> servers) {
        try {
            FileOutputStream fos = null;
            try {
                fos = App.getContext().openFileOutput(CREDENTIALS_FILE_NAME, App.getContext().MODE_PRIVATE);
                String toWrite = new Gson().toJson(servers);
                fos.write(toWrite.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restore the servers from the json file.
     */
    public static void restoreServers() {
        try {
            FileInputStream fis = null;
            String text = "";

            try {
                fis = App.getContext().openFileInput(CREDENTIALS_FILE_NAME);
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

            // Load servers.
            ArrayList<Server> servers = new Gson().fromJson(text, new TypeToken<ArrayList<Server>>(){}.getType());
            ServerManager.Instance().loadServers(servers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
