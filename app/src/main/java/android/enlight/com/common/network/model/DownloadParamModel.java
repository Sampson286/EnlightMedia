package android.enlight.com.common.network.model;
/**
 * 文件下载对象
 * @author zyc
 *
 */
public class DownloadParamModel {

	private String filePath;
	private String fileName;
	private long totalLength = 0;
	private long currentLength = 0;
	private long targetLength = 0;
	private DownloadCallback downloadCallback;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(long totalLength) {
		this.totalLength = totalLength;
	}

	public long getCurrentLength() {
		return currentLength;
	}

	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}

	public long getTargetLength() {
		return targetLength;
	}

	public void setTargetLength(long targetLength) {
		this.targetLength = targetLength;
	}

	public DownloadCallback getDownloadCallback() {
		return downloadCallback;
	}

	public void setDownloadCallback(DownloadCallback downloadCallback) {
		this.downloadCallback = downloadCallback;
	}

}
