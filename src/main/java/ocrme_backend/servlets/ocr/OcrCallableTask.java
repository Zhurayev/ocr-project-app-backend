package ocrme_backend.servlets.ocr;

import ocrme_backend.datastore.gcloud_datastore.daos.OcrRequestDaoImpl;
import ocrme_backend.ocr.OCRProcessor;
import ocrme_backend.ocr.OcrProcessorImpl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by iuliia on 6/29/17.
 */
public class OcrCallableTask implements Callable<String> {
    private final byte[] bytes;
    private final String[] languages;
    public String result;
    private static Logger logger;

    public OcrCallableTask(byte[] bytes, String[] languages) {
        this.bytes = bytes;
        this.languages = languages;
        logger = Logger.getLogger(OcrRequestDaoImpl.class.getName());

    }

    private String doStaff(byte[] bytes, String[] languages) throws IOException, GeneralSecurityException {
        String jsonResult = null;
        try {
            OCRProcessor processor = new OcrProcessorImpl();
            if (languages == null || languages.length <= 0) //run without languages - auto language will be used
            {
                jsonResult = processor.ocrForText(bytes);
            } else {
                jsonResult = processor.ocrForText(bytes, Arrays.asList(languages));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "ERROR! See log below.");
            e.printStackTrace();
        }

        return jsonResult;
    }

    @Override
    public String call() throws Exception {
        logger.log(Level.INFO, "run called");
        result = doStaff(bytes, languages);
        logger.log(Level.INFO, "text result:" + result);
        return result;
    }
}