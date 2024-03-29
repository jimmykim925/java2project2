package com.example.lib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

public class FileStuff {
	public static Boolean storeStringFile(Context context, String filename, String content, Boolean external){
		try{
			// create new file
			File file;
			// create file output
			FileOutputStream fos;
			if(external){
				// new external file directory
				file = new File(context.getExternalFilesDir(null), filename);
				fos = new FileOutputStream(file);
			} else {
				fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			// WRITE to save file
			fos.write(content.getBytes());
			fos.close();
		} catch (IOException e){
			Log.e("WRITE ERROR", filename);
		}
		
		
		return true;
	}
	
	public static Boolean storeObjectFile(Context context, String filename, Object content, Boolean external){
		try{
			// create new file
			File file;
			FileOutputStream fos;
			ObjectOutputStream oos;
			if(external){
				// new external file dir for object
				file = new File(context.getExternalFilesDir(null), filename);
				fos = new FileOutputStream(file);
			} else {
				fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			// new object output
			oos = new ObjectOutputStream(fos);
			
			// WRITE object to save
			oos.writeObject(content);
			oos.close();
			fos.close();
		} catch (IOException e){
			Log.e("WRITE ERROR", filename);
		}
		
		return true;
	}
	
	// READ string file from saved file
	public static String readStringFile(Context context, String filename, Boolean external){
		String content = "";
		try {
			// create file for input stream
			File file;
			FileInputStream fin;
			if(external){
				// get saved file directory
				file = new File(context.getExternalFilesDir(null), filename);
				fin = new FileInputStream(file);
			} else {
				file = new File(filename);
				fin = context.openFileInput(filename);
			}
			
			// create buffered input stream
			BufferedInputStream bin = new BufferedInputStream(fin);
			
			// set bytes and read int
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			// loop through bytes and append content
			while((bytesRead = bin.read(contentBytes)) != -1){
				content = new String(contentBytes,0,bytesRead);
				contentBuffer.append(content);
			}
			content = contentBuffer.toString();
			fin.close();
		} catch (FileNotFoundException e){
			Log.e("READ ERROR", "FILE NOT FOUND" + filename);
		} catch (IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		return content;
	}
	
	// READ object file that is saved file
	public static Object readObjectFile(Context context, String filename, Boolean external){
		Object content = new Object();
		try {
			// create file and input stream
			File file;
			FileInputStream fin;
			if(external){
				// get saved file
				file = new File(context.getExternalFilesDir(null), filename);
				fin = new FileInputStream(file);
			} else {
				file = new File(filename);
				fin = context.openFileInput(filename);
			}
			// create new object input stream
			ObjectInputStream ois = new ObjectInputStream(fin);
			
			try{
				// read the object
				content = (Object) ois.readObject();
			} catch (ClassNotFoundException e){
				Log.e("READ ERROR", "INVALID JAVA OBJECT FILE");
			}
			ois.close();
			
			fin.close();
		} catch (FileNotFoundException e){
			Log.e("READ ERROR", "FILE NOT FOUND" + filename);
			return null;
		} catch (IOException e){
			Log.e("READ ERROR", "I/O ERROR");
			
		}
		return content;
	}

}
