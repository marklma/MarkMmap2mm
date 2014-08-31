/**
 * 
 */
package ga.marklma.mmap2mm.action;

/**
 * @author Mark.L.Ma
 *
 */
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class UnzipAction {

	private File file;
	private String jarDir;

	public UnzipAction(File jarDir, File file) {
		this.file = file;
		this.jarDir = jarDir.getPath() + File.separator;
	}

	public void convert() {
		ZipFile zipFile = null;
		ZipInputStream zipInputStream = null;
		try {
			// 实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
			zipFile = new ZipFile(file);
			zipInputStream = new ZipInputStream(new FileInputStream(file));
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipEntry zipEntry = null;
		File temp = null;
		try {
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String fileName = zipEntry.getName();
				if (fileName.equals("Document.xml")) {
					try {
						temp = File.createTempFile(java.util.UUID.randomUUID()
								.toString(), ".xml", file.getParentFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					int len = 0;
					OutputStream os = null;
					// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
					InputStream is = null;
					Process p = null;
					try {
						os = new FileOutputStream(temp);
						// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
						is = zipFile.getInputStream(zipEntry);
						while ((len = is.read()) != -1) {
							os.write(len);
						}
						System.out.println(jarDir
								+ "xsltproc_bin\\xsltproc.exe -o "
								+ file.getPath() + ".mm " + jarDir
								+ "mm2fm.xslt " + temp.getPath());
						p = Runtime.getRuntime().exec(
								jarDir + "xsltproc_bin\\xsltproc.exe -o "
										+ file.getPath() + ".mm " + jarDir
										+ "mm2fm.xslt " + temp.getPath());
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							os.close();
							is.close();
							p.waitFor();
							temp.deleteOnExit();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zipInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}