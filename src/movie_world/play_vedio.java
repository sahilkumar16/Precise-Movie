
package movie_world;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class play_vedio {
    static String link="";
   
    
    

    public static void main(String[] args) {
        
        
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new JFrame("Youtube vedio");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(getBrowser(),BorderLayout.CENTER);
                frame.setSize(400,300);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            public  void run (){
                NativeInterface.close();
            }
        }));
        
    }
    
    public  static JPanel getBrowser(){
        JPanel wbPanel = new JPanel(new BorderLayout());
        JWebBrowser wb = new JWebBrowser();
        wbPanel.add(wb,BorderLayout.CENTER);
        wb.setBarsVisible(false);
        wb.navigate("https://www.youtube.com/watch?v=qySOXAJW12g");
        return wbPanel;
        
    }

    
}
