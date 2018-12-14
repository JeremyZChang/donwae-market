package com.donwae.market.lucene;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @auther Jeremy
 * 2018/12/13 上午11:13
 */
public class HtmlBeanUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void parseHtml() {
        String path = "xxx";

        try {
            // 创建文件source
            Source source = new Source(new File(path));

            Element firstElement = source.getFirstElement(HTMLElementName.TITLE);

            logger.info(firstElement.getTextExtractor().toString());
            logger.info(source.getTextExtractor().toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
