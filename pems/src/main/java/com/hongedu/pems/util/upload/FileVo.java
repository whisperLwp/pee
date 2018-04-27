package com.hongedu.pems.util.upload;


/**
 * 上传附件
 * @author 王攀登
 * @data 2017年8月20日 10:48:07
 *
 */
public class FileVo {
	private Long fid;  
	private String fileName; 
	private String fileSize;
	private String url;

	
	
	public FileVo() {
		super();
	}

	public FileVo(Long fid, String fileName, String fileSize, String url) {
		super();
		this.fid = fid;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.url = url;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
