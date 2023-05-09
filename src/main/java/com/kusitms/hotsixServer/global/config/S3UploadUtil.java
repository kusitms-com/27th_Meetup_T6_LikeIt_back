package com.kusitms.hotsixServer.global.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kusitms.hotsixServer.global.error.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static com.kusitms.hotsixServer.global.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class S3UploadUtil {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷

    // S3 파일 업로드
    public String upload(MultipartFile multipartFile, String dirName) {
        try {
            File convertFile = convert(multipartFile)
                    .orElseThrow(() -> new BaseException(CONVERT_FILE_ERROR));// 파일 변환 에러

            // S3에 저장할 파일명 설정
            String fileName = dirName + "/" + UUID.randomUUID() + "_" + convertFile.getName();

            // S3에 파일 업로드
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

            // 로컬 파일 삭제
            convertFile.delete();
            return uploadImageUrl;

        } catch (IOException e) {
            throw new BaseException(UPLOAD_IMAGE_ERROR);
        }
    }

    // S3 파일 삭제
    public void delete(String path) {
        amazonS3Client.deleteObject(bucket, path);
    }

    // 파일 convert 후 로컬에 업로드
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

}