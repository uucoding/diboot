package com.diboot.components.file.zip;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;

/***
 * 文件压缩操作类
 * @author Mazc@dibo.ltd
 */
public class ZipHelper {
	private static final Logger logger = LoggerFactory.getLogger(ZipHelper.class);

	/***
	 * 将文件夹下的全部文件压缩到zip
	 * @param sourceFolder
	 * @param targetZipFile
	 */
	public static boolean toZip(File sourceFolder, File targetZipFile) {
		OutputStream archiveStream = null;
		ArchiveOutputStream archive = null;
		try{
			archiveStream = new FileOutputStream(targetZipFile);
			archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream);
			// 获取全部文件集合
			Collection<File> fileList = FileUtils.listFiles(sourceFolder, null, true);
			// 压缩到zip
			for (File file : fileList) {
				String entryName = getEntryName(sourceFolder, file);
				ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
				archive.putArchiveEntry(entry);
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
				IOUtils.copy(input, archive);
				input.close();
				archive.closeArchiveEntry();
			}
			return true;
		}
		catch (Exception e){
			logger.error("压缩成zip文件异常", e);
			return false;
		}
		finally {
			//关闭文件流
			if(archive != null || archiveStream != null){
				try{
					archive.finish();
					archiveStream.close();
				}
				catch (Exception e){
					logger.warn("关闭文件流异常", e);
				}
			}
		}
	}

	/***
	 * 获取文件相对路径名称
	 * @param source
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static String getEntryName(File source, File file) throws IOException {
		int index = source.getAbsolutePath().length() + 1;
		String path = file.getCanonicalPath();
		return path.substring(index);
	}

}
