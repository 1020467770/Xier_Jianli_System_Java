package cn.sqh.domain.result;

import lombok.Data;

@Data
public class UploadFileResult {

    private String fileName;

    private String fileFormatName;

    private String fileDownloadUri;

    private String fileType;

    private long fileSize;

    public UploadFileResult(String fileName, String fileFormatName, String fileDownloadUri, String fileType, long fileSize) {
        this.fileName = fileName;
        this.fileFormatName = fileFormatName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
