package me.project.training_arc.service;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;


    /** check if bucket exists
     * @param bucketName {@link String} object
     * @return boolean - true if bucket exists
     * @throws ErrorResponseException thrown to indicate S3 service returned an error response.
     * @throws InsufficientDataException thrown to indicate not enough data available in InputStream.
     * @throws InternalException thrown to indicate internal library error.
     * @throws InvalidKeyException thrown to indicate missing of HMAC SHA-256 library.
     * @throws InvalidResponseException thrown to indicate S3 service returned invalid or no error
     *     response.
     * @throws IOException thrown to indicate I/O error on S3 operation.
     * @throws NoSuchAlgorithmException thrown to indicate missing of MD5 or SHA-256 digest library.
     * @throws XmlParserException thrown to indicate XML parsing error.
     */
    public boolean bucketExists(String bucketName) throws ServerException,
            InsufficientDataException,
            ErrorResponseException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {

        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * creates bucket
     *
     * @param bucketName {@link String} object
     * @throws ErrorResponseException    thrown to indicate S3 service returned an error response.
     * @throws InsufficientDataException thrown to indicate not enough data available in InputStream.
     * @throws InternalException         thrown to indicate internal library error.
     * @throws InvalidKeyException       thrown to indicate missing of HMAC SHA-256 library.
     * @throws InvalidResponseException  thrown to indicate S3 service returned invalid or no error
     *                                   response.
     * @throws IOException               thrown to indicate I/O error on S3 operation.
     * @throws NoSuchAlgorithmException  thrown to indicate missing of MD5 or SHA-256 digest library.
     * @throws XmlParserException        thrown to indicate XML parsing error.
     */
    public void createBucket(String bucketName) throws ServerException,
            InsufficientDataException,
            ErrorResponseException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }


    /**
     *
     * @param bucketName {@link String} object
     * @param file {@link MultipartFile} object
     * @return String - name of the file in minio which was uploaded
     * @throws ErrorResponseException thrown to indicate S3 service returned an error response.
     * @throws InsufficientDataException thrown to indicate not enough data available in InputStream.
     * @throws InternalException thrown to indicate internal library error.
     * @throws InvalidKeyException thrown to indicate missing of HMAC SHA-256 library.
     * @throws InvalidResponseException thrown to indicate S3 service returned invalid or no error response.
     * @throws IOException thrown to indicate I/O error on S3 operation.
     * @throws NoSuchAlgorithmException thrown to indicate missing of MD5 or SHA-256 digest library.
     * @throws XmlParserException thrown to indicate XML parsing error.
     * @throws ServerException thrown to indicate that S3 service returning HTTP server error.
     */
    public String uploadFileToMinio(String bucketName, MultipartFile file) throws IOException,
            ServerException,
            InsufficientDataException,
            ErrorResponseException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {

        if (file.isEmpty()) throw new RuntimeException("file is empty");
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()))
            createBucket(bucketName);
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        return file.getOriginalFilename();

    }

    /**
     * download file from minio
     * @param bucketName {@link String}
     * @param objectName {@link String}
     * @return InputStream
     */
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (MinioException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found in Minio", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error downloading file from Minio", e);
        }
    }



    public boolean deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            return false;
        }
        return true;
    }
}
