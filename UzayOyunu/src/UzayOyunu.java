import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
//Main Class
public class UzayOyunu extends JFrame {
	public UzayOyunu(String title) throws HeadlessException {// Title Yaratmak i�in
		super(title);
		ImageIcon logo = new ImageIcon(UzayOyunu.class.getResource("/img/icon.png"));
		setIconImage(logo.getImage());// icon resmini de�i�tirir
	}

	public static void main(String[] args) {
		UzayOyunu ekran = new UzayOyunu("Uzay Oyunu");
		ekran.setResizable(false);
		ekran.setFocusable(false);// frame de�il jpanele focuslanacak
		ekran.setSize(800, 600);
		ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		OyunPanel oyun = new OyunPanel();
		
		// Bunlar�n s�ras� �nemli
		oyun.requestFocus();// Jpanelin bizim klavye i�lemlerini anlamas� i�in fokusu ona veriyoruz
		oyun.addKeyListener(oyun);
		oyun.setFocusable(true);// oda�� jframeden �ekmi�tik art�k oda�� jpanele verdik
		oyun.setFocusTraversalKeysEnabled(false);// klavye i�lemlerinin jpanelinin anlmas� i�in gerekli
		ekran.add(oyun);
		ekran.setVisible(true);
	}
}