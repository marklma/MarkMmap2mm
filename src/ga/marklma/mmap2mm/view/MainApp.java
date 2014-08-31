/**
 * 
 */
package ga.marklma.mmap2mm.view;

/**
 * @author Mark.L.Ma
 *
 */
import ga.marklma.mmap2mm.action.UnzipAction;

import java.io.*;

public class MainApp {

	/**
	 * @param args[0] Path to 把mmap拖到我身上然后去找mm文件.bat file
	 * @param args[1] Path to *.mmap file
	 */
	public static void main(String args[]) {
		// args[0] = "D:\\test\\test.mmap";
		File batDir = new File(args[0]);// 压缩文件
		File file = new File(args[1]);// 压缩文件
		if (file.isFile() && file.canRead()) {
			UnzipAction uAction = new UnzipAction(batDir,file);
			uAction.convert();
		} else {
			System.out.println(file.getPath() + "：无此文件或文件不可读！");
		}
	}
}