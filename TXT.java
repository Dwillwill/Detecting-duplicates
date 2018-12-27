import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
public class TXT {
	public static void main(String[] args) {
		try {
			
			//变量
			int[][] mbr = new int[16][32];
			int[][] vbr = new int[16][32];
			int[][] Txt = new int[16][32];
			int[][] Directory = new int[16][32];
			int numofTxt = 0;
			int numofDir = 0;
			int[][] FilenameSou = new int[15][5];
			int[][] FilenameSou2 = new int[15][5];
			
			int[][] samename = new int[150][5];
			int[][] DifferentFile = new int[150][32];
			int	numofsameFile=0;
			int numofDifferentFile=0;
			int[] haha = new int[90];
			int o=0;
			//读取镜像文件
			
			@SuppressWarnings("resource")
			RandomAccessFile rf = new RandomAccessFile("C:\\Users\\apple\\Desktop\\imageMax.dd", "r");
			
			//读取镜像文件的MBR并输出
			System.out.println("MBR：");
			for(int i=0;i<16;i++){
				for(int l=0;l<32;l++){
					mbr[i][l]=rf.read(); 
					System.out.printf("%02X ", mbr[i][l]);
					}
					System.out.println();
			}			
			System.out.println();
			System.out.println();

			//计算根目录的扇区
			
			int SecperClu = mbr[0][13];
			int sizeOfRes = mbr[0][15]*256+mbr[0][14];
			int numOfFat = mbr[0][16];
			int sizeOfeachFat = mbr[1][7]*256*256*256+mbr[1][6]*256*256+mbr[1][5]*256+mbr[1][4];
			int rootClu = mbr[1][15]*256*256*256+mbr[1][14]*256*256+mbr[1][13]*256+mbr[1][12];
			int rootContent = numOfFat*sizeOfeachFat+sizeOfRes+(rootClu-2)*SecperClu;
			
            //打印所计算的扇区	
			System.out.println("Root directory's sector:");
			System.out.println(rootContent);
			
			
			
			//利用seek跳到根目录的位置
			rf.seek((rootContent-1)*512 + 512);
			System.out.println();
			System.out.println();
			System.out.println();
			
			//显示根目录的VBR
			
			System.out.println("Root directory's VBR:");
			for(int i=0;i<16;i++){
				for(int l=0;l<32;l++){
					{
						vbr[i][l]=rf.read(); 
						System.out.printf("%02X ",vbr[i][l] );
					}
				}
				System.out.println();
			}
			
			
			System.out.println();
			System.out.println();
			System.out.println();
			
			
			/*1.从根目录的VBR中找出TXT文件和目录文件
			2.分别把每行16进制代码，即每个文件的信息，储存到相应的数组中*/
			
			for(int i=0;i<16;i++){
				if(vbr[i][8]==84&&vbr[i][9]==88&&vbr[i][10]==84){
					for(int t = 0; t < 32 ; t ++){
						Txt[numofTxt][t] = vbr[i][t];
					}
					numofTxt ++;
				}
				else if(vbr[i][8]==32&&vbr[i][9]==32&&vbr[i][10]==32&&vbr[i][11]==16&&vbr[i][0]!=229&&vbr[i][0]!=46){
				for(int t = 0; t < 32 ; t ++){
						Directory[numofDir][t] = vbr[i][t];
					}
					numofDir ++;
				}
			}
			
			for(int x = 0; x < numofDir ; x ++){
			int root = Directory[x][26];
			rf.seek((root-2)*SecperClu*512+(rootContent-1)*512 + 512);
				for(int i=0;i<16;i++){
					for(int l=0;l<32;l++){
						vbr[i][l]=rf.read(); 
						}
					if(vbr[i][8]==84&&vbr[i][9]==88&&vbr[i][10]==84){
						for(int t = 0; t < 32 ; t ++){
						Txt[numofTxt][t] = vbr[i][t];
						}
						numofTxt++;
					}
					else if(vbr[i][8]==32&&vbr[i][9]==32&&vbr[i][10]==32&&vbr[i][11]==16&&vbr[i][0]!=229&&vbr[i][0]!=46){
						for(int t = 0; t < 32 ; t ++){
							Directory[numofDir][t] = vbr[i][t];
						}
						numofDir ++;
					}
				}
			}
			
			
			//显示该磁盘镜像所有目录信息
			
			System.out.println("List of all the directories'names:");
			for(int i = 0 ; i < numofDir ; i ++){
				for(int j = 0; j < 5 ; j ++){
					System.out.printf("%c",Directory[i][j]);
				}
				
			}
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
			//显示磁盘镜像所有文本文件信息
			System.out.println("List of all the TXT files'names:");
			for(int i = 0 ; i < numofTxt ; i ++){
				for(int j = 0; j < 5 ; j ++){
					System.out.printf("%c",Txt[i][j]);
					
				}
				System.out.println(".txt");
			}
			
			
			System.out.println();
			System.out.println();
			

			//将文件的名字信息记录到FilenameSou二维数组中
			for(int x = 0; x < numofTxt ; x ++){
				FilenameSou[x] = new int[5];
				for(int t = 0; t < 5 ; t ++){
					FilenameSou[x][t] = Txt[x][t];
				}
				
			}
			
			//将文件的名字信息记录到FilenameSou2二维数组中
			for(int x = 0; x < numofTxt ; x ++){
				FilenameSou2[x] = new int[5];
				for(int t = 0; t < 5 ; t ++){
					FilenameSou2[x][t] = Txt[x][t];
				}
				
			}
			
			
			
			//寻找名字不相同的文件:
			//顺序寻找，只要找到与之名字相同的文件，将其转化成空格
			for(int i=0;i<numofTxt;i++){
				for(int y=i+1;y<numofTxt;y++){
					if(Arrays.equals(FilenameSou[i],FilenameSou[y])){
						for(int j=0 ;j<5; j++){			
							FilenameSou[y][j] = 32;	
							}
					}
				}
			}

			System.out.println();
			System.out.println();
			System.out.println();
			
			
			/*打印不同的文件：
			1.若该文件第一个字符为空格，则不读这一行文件
			2.记录不同文件的下标存入HAHA数组，以便后续操作*/
			System.out.println("List of different files'names");
			for(int i=0;i<numofTxt;i++){
				if(FilenameSou[i][0]!=32){
					
					haha[o] = i;
					o++;
					
					numofDifferentFile++;
					for(int y=0;y<5;y++){
					System.out.printf("%c",FilenameSou[i][y]);
					}
					
				System.out.println(".txt");
				}
			}
			
			
			System.out.println();
			System.out.println();
			
			
			////记录不同文件夹所有信息
			System.out.println("List of the information of each TXT files");
			for(int q = 0;q<numofDifferentFile;q++){
				DifferentFile[q] = new int[32];
				for(int m = 0;m<32;m++){
					DifferentFile[q][m] = Txt[haha[q]][m]; 
					System.out.printf("%02X ",DifferentFile[q][m]);
				}
				System.out.println();
			}
			
			
			

			/*找出哪些文件时被复制的：
			1.顺序查找，若找到与之相同名字的文本，则将其转化为空格，同时将此文件之后的与其名字相同的所有文件名字全部转化为空格；
			2.以此类推，程序实现即可找出复制过的文件；*/
			
			for(int i=0;i<numofTxt;i++){
				for(int y=i+1;y<numofTxt;y++){
					if(Arrays.equals(FilenameSou2[i],FilenameSou2[y])){
						samename[numofsameFile] = new int[5];
						for(int c = 0;c<5;c++){
							samename[numofsameFile][c] = FilenameSou2[i][c];
						}
						numofsameFile++;
					
						for(int v= y;v<numofTxt;v++){
							if(Arrays.equals(FilenameSou2[i],FilenameSou2[v]))
							{
								for(int l=0;l<5;l++){
									FilenameSou2[v][l]=32;
								}
							}
						}
					}
					
				}
			}
			
			
			/*打印不同的文件：
			若该文件第一个字符为空格，则不读这一行文件*/
			System.out.println("Duplicate files：");
			for(int i=0;i<numofsameFile;i++){
				if(samename[i][0]!=32){
					for(int y=0;y<5;y++){
					System.out.printf("%c",samename[i][y]);
					}
					System.out.println(".txt");
				}
			}
	
		//读出每个不重复的文本文件中的内容
		    for(int p = 0 ; p < numofDifferentFile ; p ++){
		    int Root = DifferentFile[p][26];
		    rf.seek(((Root-2)*8 + rootContent) * 512);
		    for(int i = 0 ; i < 16 ; i ++){
		    	for(int t = 0 ; t < 32 ; t ++){
		    		vbr[i][t] = rf.read();
		    		System.out.printf("%c", vbr[i][t]);
		    	}
		    	System.out.println();
		    }
		    
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    }
		    
		    
		    //调用CreatFile类和SetTxt类中的方法，在桌面创建文件夹并在文件夹中创建内容不同的文本
		    for(int t = 0 ; t < numofDifferentFile ; t ++){
		    	int[] name = new int[10];
		    	int[] content = new int[512];
		    	for(int i = 0 ; i < 7 ; i ++){
		    		name[i] = DifferentFile[t][i];
		    	}
		    	int Root = DifferentFile[t][26];
		    	rf.seek(((Root-2)*8 + rootContent) * 512);
		    	for(int i = 0 ; i < 512 ; i ++){
		    		content[i] = rf.read();
		    	}
		    	Creatfile makedir = new Creatfile();
		    	makedir.creatfile();
		    	Settxt setfile = new Settxt();
		    	setfile.rwFile(name, content);
		    
		    }
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}