package info.accolade.trip_master.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Uploader extends AsyncTask<Void, Void, Void>{
	String Result,path,file,urlstring;
	Activity activity;
	
	public Uploader(String path, String fi, FragmentActivity activity) {
		this.path=path;
		this.activity=activity;
		this.file=fi;
/*		Log.e("inside", "uploader");
		Log.e("file", ":"+file);
		Log.e("path", ":"+path);
*/	
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Void doInBackground(Void... params) {

		String fileName = path;
		Log.e("file", ":"+fileName);
		HttpURLConnection conn = null;
		DataOutputStream dos = null;  
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(fileName); 


		try { 


			FileInputStream fileInputStream = new FileInputStream(sourceFile);
//			URL url = new URL("http://192.168.1.35/test/FileUpload.php");
			URL url = new URL(Constants.URL_FileUpload);
			Log.e("upload php", "image1");

			conn = (HttpURLConnection) url.openConnection(); 
			conn.setDoInput(true);    
			conn.setDoOutput(true);           
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			dos = new DataOutputStream(conn.getOutputStream());




			//file content
			
			dos.writeBytes(twoHyphens + boundary + lineEnd); 
			dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename="
					+file+ "" + lineEnd);

			dos.writeBytes(lineEnd);


			// create a buffer of maximum size
			bytesAvailable = fileInputStream.available();

			maxBufferSize = 1024;
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0)
			{
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable,maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0,bufferSize);
			}
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			fileInputStream.close();
			dos.flush();
			InputStream is = conn.getInputStream();

			Log.e("is",":"+is);
			int ch;

			StringBuffer b =new StringBuffer();
			while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
			Result=b.toString();
			Log.e("Response",":"+Result);
			dos.close();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		
		Log.e("upload php", "image2");

	}
}