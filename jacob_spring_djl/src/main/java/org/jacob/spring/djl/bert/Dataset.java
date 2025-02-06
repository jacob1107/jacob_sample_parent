package org.jacob.spring.djl.bert;

import ai.djl.basicdataset.tabular.CsvDataset;
import ai.djl.basicdataset.tabular.utils.Feature;
import ai.djl.modality.nlp.bert.BertFullTokenizer;
import ai.djl.ndarray.types.Shape;
import ai.djl.translate.PaddingStackBatchifier;
import org.apache.commons.csv.CSVFormat;

/**
 * @Author jacob
 * @Date 2025/1/24 10:41
 * @Version 1.0
 */
public class Dataset {
    public static CsvDataset getDataset(int batchSize, BertFullTokenizer tokenizer, int maxLength, int limit) {
        String amazonReview =
                "https://mlrepo.djl.ai/dataset/nlp/ai/djl/basicdataset/amazon_reviews/1.0/amazon_reviews_us_Digital_Software_v1_00.tsv.gz";
        float paddingToken = tokenizer.getVocabulary().getIndex("[PAD]");
        return CsvDataset.builder()
                .optCsvUrl(amazonReview) // load from Url
                .setCsvFormat(CSVFormat.TDF.withQuote(null).withHeader()) // Setting TSV loading format
                .setSampling(batchSize, true) // make sample size and random access
                .optLimit(limit)
                .addFeature(
                        new Feature(
                                "review_body", new BertFeaturizer(tokenizer, maxLength)))
                .addLabel(
                        new Feature(
                                "star_rating", (buf, data) -> buf.put(Float.parseFloat(data) - 1.0f)))
                .optDataBatchifier(
                        PaddingStackBatchifier.builder()
                                .optIncludeValidLengths(false)
                                .addPad(0, 0, (m) -> m.ones(new Shape(1)).mul(paddingToken))
                                .build()) // define how to pad dataset to a fix length
                .build();
    }
}
