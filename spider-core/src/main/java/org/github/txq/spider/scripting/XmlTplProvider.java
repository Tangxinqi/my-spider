package org.github.txq.spider.scripting;


import org.github.txq.spider.type.TypeHandler;

/**
 * @author tangxinqi
 * @date 2019/11/26 12:10
 */
public class XmlTplProvider extends TextTplProvider {

    public XmlTplProvider(String nodeId, TypeHandler<String> handler, Boolean ignore) {
        super(nodeId, handler, ignore);
    }
}
