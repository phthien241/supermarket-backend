package com.bestbuy.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;


@Component
public class AzureStorageUtil {
    @Value("${AZURE_STORAGE_SAS_TOKEN}")
    private String sasToken;

    public String getSasToken() {
        return sasToken;
    }

    @Value("${AZURE_STORAGE_CONNECTION_STRING}")
    private String connectionString;

    public String getFileNameFromURL(String urlString) {
        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            String pathname = url.getPath();
            String[] segment = pathname.split("/");
            return segment[segment.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String uploadFileToAzureBlob(String productName, String oldImageName, InputStream stream, long fileSize) {
        try {
            String containerName = "products";
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            containerClient.createIfNotExists();
            String dateTimeStamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()).replace(":", "-");
            String formattedFileName = productName.toLowerCase().replace("\\s+", "-") + "-" + dateTimeStamp + ".jpg";

            BlobClient blockBlobClient = containerClient.getBlobClient(formattedFileName);
            blockBlobClient.upload(stream, fileSize, true); 

            if (oldImageName != null && !oldImageName.isEmpty()) {
                BlobClient oldBlobClient = containerClient.getBlobClient(oldImageName);
                oldBlobClient.deleteIfExists();
            }
            return blockBlobClient.getBlobUrl();
        } catch (Exception e) {
            System.err.println("Error uploading file: " + e.getMessage());
            throw new RuntimeException("Error uploading file to Azure Blob Storage", e);
        }
    }
}
