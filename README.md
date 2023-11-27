# tus-java-client [![Tests](https://github.com/tus/tus-java-client/actions/workflows/tests.yml/badge.svg?branch=main)](https://github.com/tus/tus-java-client/actions/workflows/tests.yml)

> **tus** is a protocol based on HTTP for *resumable file uploads*. Resumable
> means that an upload can be interrupted at any moment and can be resumed without
> re-uploading the previous data again. An interruption may happen willingly, if
> the user wants to pause, or by accident in case of a network issue or server
> outage.

**tus-java-client** is a library for uploading files using the *tus* protocol to any remote server supporting it.

This library is also compatible with the Android platform and can be used without any modifications using the API. The [tus-android-client](https://github.com/tus/tus-android-client) provides additional classes which can be used in addition the Java library.

## Usage

```java
package io.tus.java.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



public class SunatTusClientMain {
    public static void main(String[] args){
        try {
            SslUtil.setSSLContext();
            final TusClient client = new TusClient();
            String HOST_PUBLICA = "https://api-sire.sunat.gob.pe/v1/contribuyente/migeigv/";
            String END_POINT_PROPUESTA = "libros/rvierce/receptorpreliminar/web/preliminar/upload";
            System.setProperty("http.strictPostRedirect", "true");
            client.setUploadCreationURL(new URL(HOST_PUBLICA + END_POINT_PROPUESTA));
            client.enableResuming(new TusURLMemoryStore());
            Map<String, String> headers = new HashMap<>();
            String tokenSunat="eyJraWQiOiJhcGkuc3VuYXQuZ29iLnBlLmtpZDAwMSIsInR5cCI6IkpXVCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyMDU2MzQ3MjY3OCIsImF1ZCI6Ilt7XCJhcGlcIjpcImh0dHBzOlwvXC9hcGktY3BlLnN1bmF0LmdvYi5wZVwiLFwicmVjdXJzb1wiOlt7XCJpZFwiOlwiXC92MVwvY29udHJpYnV5ZW50ZVwvcmVwb3ZlbnRhc1wiLFwiaW5kaWNhZG9yXCI6XCIxXCIsXCJndFwiOlwiMTAwMTAwXCJ9XX0se1wiYXBpXCI6XCJodHRwczpcL1wvYXBpLXNpcmUuc3VuYXQuZ29iLnBlXCIsXCJyZWN1cnNvXCI6W3tcImlkXCI6XCJcL3YxXC9jb250cmlidXllbnRlXC9taWdlaWd2XCIsXCJpbmRpY2Fkb3JcIjpcIjFcIixcImd0XCI6XCIxMDAxMDBcIn1dfV0iLCJ1c2VyZGF0YSI6eyJudW1SVUMiOiIyMDU2MzQ3MjY3OCIsInRpY2tldCI6IjExODUxMDM2MjQ3NzgiLCJucm9SZWdpc3RybyI6IiIsImFwZU1hdGVybm8iOiIiLCJsb2dpbiI6IjIwNTYzNDcyNjc4TU9EREFUT1MiLCJub21icmVDb21wbGV0byI6IkZFUEFNIElNUE9SVCBTLkEuQy4iLCJub21icmVzIjoiRkVQQU0gSU1QT1JUIFMuQS5DLiIsImNvZERlcGVuZCI6IjAwMjMiLCJjb2RUT3BlQ29tZXIiOiIiLCJjb2RDYXRlIjoiIiwibml2ZWxVTyI6MCwiY29kVU8iOiIiLCJjb3JyZW8iOiIiLCJ1c3VhcmlvU09MIjoiTU9EREFUT1MiLCJpZCI6IiIsImRlc1VPIjoiIiwiZGVzQ2F0ZSI6IiIsImFwZVBhdGVybm8iOiIiLCJpZENlbHVsYXIiOm51bGwsIm1hcCI6eyJpc0Nsb24iOmZhbHNlLCJkZHBEYXRhIjp7ImRkcF9udW1ydWMiOiIyMDU2MzQ3MjY3OCIsImRkcF9udW1yZWciOiIwMDIzIiwiZGRwX2VzdGFkbyI6IjAwIiwiZGRwX2ZsYWcyMiI6IjAwIiwiZGRwX3ViaWdlbyI6IjE1MDEzMiIsImRkcF90YW1hbm8iOiIwMyIsImRkcF90cG9lbXAiOiIzOSIsImRkcF9jaWl1IjoiNTE5MDYifSwiaWRNZW51IjoiMTE4NTEwMzYyNDc3OCIsImpuZGlQb29sIjoicDAwMjMiLCJ0aXBVc3VhcmlvIjoiMCIsInRpcE9yaWdlbiI6IklUIiwicHJpbWVyQWNjZXNvIjpmYWxzZX19LCJuYmYiOjE2ODg0ODQ5OTIsImNsaWVudElkIjoiYzgxMmE2YmUtOWRiMS00Mjc5LWE2ZjUtZTYzZTgxZWM2NGExIiwiaXNzIjoiaHR0cHM6XC9cL2FwaS1zZWd1cmlkYWQuc3VuYXQuZ29iLnBlXC92MVwvY2xpZW50ZXNzb2xcL2M4MTJhNmJlLTlkYjEtNDI3OS1hNmY1LWU2M2U4MWVjNjRhMVwvb2F1dGgyXC90b2tlblwvIiwiZXhwIjoxNjg4NDg4NTkyLCJncmFudFR5cGUiOiJhdXRob3JpemF0aW9uX3Rva2VuIiwiaWF0IjoxNjg4NDg0OTkyfQ.YJOCKJXV84IYHvDsEWgP2AZ_fnbpj0AIubyri_tFxKu13Eko_W9rC6zvS3tTI3vkqiw2CZRQ9wZ6MEjN36ulUPvPAGgSNpz85yj3fTjuheZ0amAn5dKSE-8dGa9cwLxXPPNVOq-8hr71gNFt5ieoCxmGvifQO766EyLPuTr2_RsFTxwJKuSRycrjfv1S-ydPxl7NJyj3BKD8BNqMCblGyb2eJnsyZRqbrAgsy6pbXl_4mi6Bon71grFymFuwy_9pn17C_TzcisZ_Zp66ow6ou4uWJ7UH99dC7ZmwQTEMey3RNw8lpolDNYptaJ_DOFwD9PQuduWqNq7bIUL6NeGJjA";
            headers.put("authorization", "Bearer " + tokenSunat);

            client.setHeaders(headers);
            File archive = new File("D:\\LE2046753402620230600080500001112.zip");
            final TusUpload upload = new TusUpload(archive);
            String[] extension = archive.getName().split("\\.");
            Map<String, String> metaData = new HashMap<>();
            metaData.put("filename", archive.getName());
            metaData.put("filetype", extension[1]);
            metaData.put("numRuc","20467534026");
            metaData.put("perTributario", "202304");
            metaData.put("codOrigenEnvio", "2");
            metaData.put("codLibro", "140000");
            metaData.put("codProceso", "3");
            metaData.put("codTipoCorrelativo", "01");
            metaData.put("nomArchivoImportacion", archive.getName());
            upload.setMetadata(metaData);
            System.out.println("Starting upload...");
            TusExecutor executor = new TusExecutor() {
                @Override
                protected void makeAttempt() throws ProtocolException,
                        IOException {
                    TusUploader uploader = client.resumeOrCreateUpload(upload);
                    uploader.setChunkSize(1024);
                    do {
                        long totalBytes = upload.getSize();
                        long bytesUploaded = uploader.getOffset();
                        double progress = (double) bytesUploaded / totalBytes * 100;
                        System.out.printf("Upload at %06.2f%%.\n", progress);
                    } while(uploader.uploadChunk() > -1);
                    HttpURLConnection conection = uploader.getConnection();
                    System.out.println("code : " + conection.getResponseCode());
                    System.out.println("method : " + conection.getRequestMethod());
                    System.out.println("msg : " + conection.getResponseMessage());
                    String responseBody = "";
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = conection.getInputStream().read(buffer))
                            != -1) {
                        result.write(buffer, 0, length);
                    }
                    responseBody = result.toString("UTF-8");
                    System.out.println("response : " + responseBody);
                    uploader.finish();
                    System.out.println("Upload finished.");
                    System.out.format("Upload available at: %s", uploader.getUploadURL().toString());
                    System.out.println("responseBody:"+responseBody);
                }
            };
            executor.makeAttempts();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex){
            System.out.println("---------------------------");
            System.out.println("Response:");
            System.out.println("ex.getCause(): "+ex.getMessage());
            System.out.println("---------------------------");
            ex.printStackTrace();
        }
    }

}

```

## Installation

The JARs can be downloaded manually from our [Maven Central Repo](https://central.sonatype.com/namespace/io.tus.java.client).

**Gradle:**

```groovy
implementation 'io.tus.java.client:tus-java-client:0.5.0'
```

**Maven:**

```xml
<dependency>
  <groupId>io.tus.java.client</groupId>
  <artifactId>tus-java-client</artifactId>
  <version>0.5.0</version>
</dependency>
```

## Documentation

The documentation of the latest versions can be found online at [javadoc.io](https://javadoc.io/doc/io.tus.java.client/tus-java-client).

## FAQ

### Can I use my own custom SSLSocketFactory?

Yes, you can! Create a subclass of `TusClient` and override the `prepareConnection` method to attach your `SSLSocketFactory`. After this use your custom `TusClient` subclass as you would normally use it. Here is an example:

```java
@Override
public void prepareConnection(@NotNull HttpURLConnection connection) {
    super.prepareConnection(connection);
    
    if(connection instanceof HttpsURLConnection) {
        HttpsURLConnection secureConnection = (HttpsURLConnection) connection;
        secureConnection.setSSLSocketFactory(mySSLSocketFactory);
    }
}
```

### Can I use a proxy that will be used for uploading files?

Yes, just add a proxy to the TusClient as shown below (1 line added to the above [usage](#usage)):

```java
TusClient client = new TusClient();
Proxy myProxy = new Proxy(...);
client.setProxy(myProxy);
```

## License

MIT
