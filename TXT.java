import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
public class TXT {
	public static void main(String[] args) {
		try {
			
			//����
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
			//��ȡ�����ļ�
			
			@SuppressWarnings("resource")
			RandomAccessFile rf = new RandomAccessFile("C:\\Users\\apple\\Desktop\\imageMax.dd", "r");
			
			//��ȡ�����ļ���MBR�����
			System.out.println("MBR��");
			for(int i=0;i<16;i++){
				for(int l=0;l<32;l++){
					mbr[i][l]=rf.read(); 
					System.out.printf("%02X ", mbr[i][l]);
					}
					System.out.println();
			}			
			System.out.println();
			System.out.println();

			//�����Ŀ¼������
			
			int SecperClu = mbr[0][13];
			int sizeOfRes = mbr[0][15]*256+mbr[0][14];
			int numOfFat = mbr[0][16];
			int sizeOfeachFat = mbr[1][7]*256*256*256+mbr[1][6]*256*256+mbr[1][5]*256+mbr[1][4];
			int rootClu = mbr[1][15]*256*256*256+mbr[1][14]*256*256+mbr[1][13]*256+mbr[1][12];
			int rootContent = numOfFat*sizeOfeachFat+sizeOfRes+(rootClu-2)*SecperClu;
			
            //��ӡ�����������	
			System.out.println("Root directory's sector:");
			System.out.println(rootContent);
			
			
			
			//����seek������Ŀ¼��λ��
			rf.seek((rootContent-1)*512 + 512);
			System.out.println();
			System.out.println();
			System.out.println();
			
			//��ʾ��Ŀ¼��VBR
			
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
			
			
			/*1.�Ӹ�Ŀ¼��VBR���ҳ�TXT�ļ���Ŀ¼�ļ�
			2.�ֱ��ÿ��16���ƴ��룬��ÿ���ļ�����Ϣ�����浽��Ӧ��������*/
			
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
			
			
			//��ʾ�ô��̾�������Ŀ¼��Ϣ
			
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
			
			//��ʾ���̾��������ı��ļ���Ϣ
			System.out.println("List of all the TXT files'names:");
			for(int i = 0 ; i < numofTxt ; i ++){
				for(int j = 0; j < 5 ; j ++){
					System.out.printf("%c",Txt[i][j]);
					
				}
				System.out.println(".txt");
			}
			
			
			System.out.println();
			System.out.println();
			

			//���ļ���������Ϣ��¼��FilenameSou��ά������
			for(int x = 0; x < numofTxt ; x ++){
				FilenameSou[x] = new int[5];
				for(int t = 0; t < 5 ; t ++){
					FilenameSou[x][t] = Txt[x][t];
				}
				
			}
			
			//���ļ���������Ϣ��¼��FilenameSou2��ά������
			for(int x = 0; x < numofTxt ; x ++){
				FilenameSou2[x] = new int[5];
				for(int t = 0; t < 5 ; t ++){
					FilenameSou2[x][t] = Txt[x][t];
				}
				
			}
			
			
			
			//Ѱ�����ֲ���ͬ���ļ�:
			//˳��Ѱ�ң�ֻҪ�ҵ���֮������ͬ���ļ�������ת���ɿո�
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
			
			
			/*��ӡ��ͬ���ļ���
			1.�����ļ���һ���ַ�Ϊ�ո��򲻶���һ���ļ�
			2.��¼��ͬ�ļ����±����HAHA���飬�Ա��������*/
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
			
			
			////��¼��ͬ�ļ���������Ϣ
			System.out.println("List of the information of each TXT files");
			for(int q = 0;q<numofDifferentFile;q++){
				DifferentFile[q] = new int[32];
				for(int m = 0;m<32;m++){
					DifferentFile[q][m] = Txt[haha[q]][m]; 
					System.out.printf("%02X ",DifferentFile[q][m]);
				}
				System.out.println();
			}
			
			
			

			/*�ҳ���Щ�ļ�ʱ�����Ƶģ�
			1.˳����ң����ҵ���֮��ͬ���ֵ��ı�������ת��Ϊ�ո�ͬʱ�����ļ�֮�������������ͬ�������ļ�����ȫ��ת��Ϊ�ո�
			2.�Դ����ƣ�����ʵ�ּ����ҳ����ƹ����ļ���*/
			
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
			
			
			/*��ӡ��ͬ���ļ���
			�����ļ���һ���ַ�Ϊ�ո��򲻶���һ���ļ�*/
			System.out.println("Duplicate files��");
			for(int i=0;i<numofsameFile;i++){
				if(samename[i][0]!=32){
					for(int y=0;y<5;y++){
					System.out.printf("%c",samename[i][y]);
					}
					System.out.println(".txt");
				}
			}
	
		//����ÿ�����ظ����ı��ļ��е�����
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
		    
		    
		    //����CreatFile���SetTxt���еķ����������洴���ļ��в����ļ����д������ݲ�ͬ���ı�
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