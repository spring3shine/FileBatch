import utils.FileUtil;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        FileUtil fileUtil = new FileUtil();
//        fileUtil.recode("C:\\Users\\admin\\Desktop\\fff","txt","windows-1252","utf-8");
//        fileUtil.renameTo("C:\\Users\\admin\\Desktop\\fff","C:\\Users\\admin\\Desktop\\fff2");
        fileUtil.mergeTxt("C:\\Users\\admin\\Desktop\\fff2","C:\\Users\\admin\\Desktop\\fff2\\mergeTxt.txt");
    }
}
