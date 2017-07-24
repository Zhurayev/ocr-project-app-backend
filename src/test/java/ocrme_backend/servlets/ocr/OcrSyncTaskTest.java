package ocrme_backend.servlets.ocr;

import ocrme_backend.datastore.utils.FileProvider;
import ocrme_backend.file_builder.pdfbuilder.PdfBuilderInputData;
import org.apache.commons.fileupload.FileItemIterator;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by iuliia on 7/3/17.
 */
public class OcrSyncTaskTest {
    @Test
    public void testCall() throws Exception {
        FileItemIterator mockFileItemIterator = mock(FileItemIterator.class);
        when(mockFileItemIterator.next()).thenReturn(FileProvider.getItemStreamImageFile());
        when(mockFileItemIterator.hasNext()).thenReturn(true).thenReturn(false);

        ExecutorService service = Executors.newFixedThreadPool(2);
        PdfBuilderInputData result = new OcrSyncTask(mockFileItemIterator, null).execute();
        Assert.assertTrue(result != null);
        Assert.assertTrue(result.getText().size() > 0);
        service.shutdown();
    }
}