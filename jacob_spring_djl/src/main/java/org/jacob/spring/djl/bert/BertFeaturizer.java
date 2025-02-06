package org.jacob.spring.djl.bert;

import ai.djl.basicdataset.tabular.CsvDataset;
import ai.djl.basicdataset.tabular.utils.DynamicBuffer;
import ai.djl.basicdataset.tabular.utils.Feature;
import ai.djl.basicdataset.tabular.utils.Featurizer;
import ai.djl.modality.nlp.Vocabulary;
import ai.djl.modality.nlp.bert.BertFullTokenizer;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.PaddingStackBatchifier;
import org.apache.commons.csv.CSVFormat;

import java.util.List;

/**
 * @Author jacob
 * @Date 2025/1/24 10:36
 * @Version 1.0
 */
final class BertFeaturizer implements Featurizer {

    private final BertFullTokenizer tokenizer;
    private final int maxLength; // the cut-off length

    public BertFeaturizer(BertFullTokenizer tokenizer, int maxLength) {
        this.tokenizer = tokenizer;
        this.maxLength = maxLength;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void featurize(DynamicBuffer buf, String input) {
        Vocabulary vocab = tokenizer.getVocabulary();
        // convert sentence to tokens (toLowerCase for uncased model)
        List<String> tokens = tokenizer.tokenize(input.toLowerCase());
        // trim the tokens to maxLength
        tokens = tokens.size() > maxLength ? tokens.subList(0, maxLength) : tokens;
        // BERT embedding convention "[CLS] Your Sentence [SEP]"
        buf.put(vocab.getIndex("[CLS]"));
        tokens.forEach(token -> buf.put(vocab.getIndex(token)));
        buf.put(vocab.getIndex("[SEP]"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int dataRequired() {
        throw new IllegalStateException("BertFeaturizer only support featurize, not deFeaturize");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object deFeaturize(float[] data) {
        throw new IllegalStateException("BertFeaturizer only support featurize, not deFeaturize");
    }
}
