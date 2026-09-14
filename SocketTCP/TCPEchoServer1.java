import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class RequestProcessing extends Thread{
    Socket channel; //socket cua kenh ao noi voi clien hien tai
    public RequestProcessing(Socket s) {
        channel = s; //nhan socket cua kenh ao noi voi client
    }
    public void run() {
        try{
            OutputStream os = channel.getOutputStream();
            InputStream is = channel.getInputStream();
            while(true) {
                int n = is.read();
                if(n==-1) break;
                os.write(n);
                //
            }
        }catch(IOException ie) {
            System.out.println("Request Processing Error: "+ ie);
        }
    }
}

public class TCPEchoServer1 {
    public final static int serverPort = 7;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(serverPort);
            System.out.println("Server da duoc tao");
            while(true){
                try {
                    Socket s = ss.accept();
                    RequestProcessing rp = new RequestProcessing(s);
                    rp.start();
                    //
                }catch(IOException ie1) {
                    System.out.println("Connection Error: " +ie1);
                }
            }
        }catch(IOException ie) {
            System.out.println("Server Creation Error: "+ie);
        }
    }
}