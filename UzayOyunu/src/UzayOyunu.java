import java.awt.Color;
import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
//Main Class
public class UzayOyunu extends JFrame {
	public UzayOyunu(String title) throws HeadlessException {// Title Yaratmak için
		super(title);
		ImageIcon logo = new ImageIcon(UzayOyunu.class.getResource("/img/icon.png"));
		setIconImage(logo.getImage());// icon resmini deðiþtirir
	}

	public static void main(String[] args) {
		UzayOyunu ekran = new UzayOyunu("Uzay Oyunu");
		ekran.setResizable(false);
		ekran.setFocusable(false);// frame deðil jpanele focuslanacak
		ekran.setSize(800, 600);
		ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		OyunPanel oyun = new OyunPanel();
		
		// Bunlarýn sýrasý önemli
		oyun.requestFocus();// Jpanelin bizim klavye iþlemlerini anlamasý için fokusu ona veriyoruz
		oyun.addKeyListener(oyun);
		oyun.setFocusable(true);// odaðý jframeden çekmiþtik artýk odaðý jpanele verdik
		oyun.setFocusTraversalKeysEnabled(false);// klavye iþlemlerinin jpanelinin anlmasý için gerekli
		ekran.add(oyun);
		ekran.setVisible(true);
	}
}