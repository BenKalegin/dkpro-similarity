package de.tudarmstadt.ukp.similarity.experiments.wordchoice.io;

import static org.junit.Assert.assertEquals;
import static org.uimafit.factory.CollectionReaderFactory.createCollectionReader;
import static org.uimafit.util.JCasUtil.select;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.junit.Test;
import org.uimafit.pipeline.JCasIterable;
import org.uimafit.util.JCasUtil;

import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.similarity.type.WordChoiceProblem;

public class WordChoiceProblemReaderTest
{

    @Test
    public void wordchoiceTest()
        throws Exception
    {
        CollectionReader reader = createCollectionReader(
                WordChoiceProblemReader.class,
                WordChoiceProblemReader.PARAM_PATH, "src/test/resources/datasets/wordchoice/",
                WordChoiceProblemReader.PARAM_PATTERNS, new String[] {
                    ResourceCollectionReaderBase.INCLUDE_PREFIX + "test.wcp"
                }
        );

        for (JCas jcas : new JCasIterable(reader)) {
            DocumentMetaData md = DocumentMetaData.get(jcas);
            System.out.println(md.getDocumentUri());

            assertEquals(1, select(jcas, DocumentAnnotation.class).size());

            int i = 0;
            for (WordChoiceProblem wcp : JCasUtil.select(jcas, WordChoiceProblem.class)) {
                if (i == 0) {
                    assertEquals("Bijou",        wcp.getTarget());
                    assertEquals("Spitzbube",    wcp.getCandidate1());
                    assertEquals("Spielkarte",   wcp.getCandidate2());
                    assertEquals("Schmuckstück", wcp.getCandidate3());
                    assertEquals("Gaststätte",   wcp.getCandidate4());
                    assertEquals(3,              wcp.getCorrectAnswer());
                }
                i++;
            }
            assertEquals(3, i);
        }
    }
}