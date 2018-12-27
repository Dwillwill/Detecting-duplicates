import java.io.File;

public class Creatfile {
	public Creatfile(){
	}
	
	public void creatfile(){
  File file = new File("C:\\Users\\apple\\Desktop\\App");
  file.mkdirs();
 }
}