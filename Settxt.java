

import java.io.*;
public class Settxt {
	
	String filecontent = "";
	
	String rename = "";
	
	public Settxt(){
		
	}
	
	public void rwFile(int[] name, int[] content){
        FileWriter fw = null;
        try {
        	
        	for(int i = 0 ; i < 7 ; i ++){
            	rename = rename + (char)name[i];
            }
        	
            fw = new FileWriter("C:\\Users\\apple\\Desktop\\App\\" + rename +".txt", true);
            for(int i = 0 ; i < 512 ; i ++){
            	filecontent = filecontent + (char)content[i];
            }
            
            
            
                fw.write(filecontent);//这里向文件中输入结果123
                fw.flush();
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
   
}
	
	


