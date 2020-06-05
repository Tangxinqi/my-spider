package org.github.txq.spider;


import org.github.txq.spider.exception.TemplateAccessException;

/**
 * @author tangxinqi
 * @date 2019/11/14 11:07
 */
public class DefaultMessageParserFactory implements MessageParserFactory {

    private final MessageParserManager messageParserManager;

    private final boolean lazy;

    public DefaultMessageParserFactory(MessageParserManager messageParserManager, boolean lazy) {
        this.messageParserManager = messageParserManager;
        this.lazy = lazy;
    }

    @Override
    public MessageParser produce(String tplName) {
        MessageParser parser = messageParserManager.request(tplName);
        if (parser != null) {
            return parser;
        }
        if (!lazy) {
            // 非懒加载情况下需要终止请求
            throw new TemplateAccessException("template[" + tplName + "] is not available, please you have register it!");
        }
        synchronized (this) {
            parser = messageParserManager.request(tplName);
            if (parser == null) {
                messageParserManager.refresh(tplName, null);
                parser = messageParserManager.request(tplName);
            }
        }
        if (parser == null) {
            throw new TemplateAccessException("template[" + tplName + "] is not available, please you have register it!");
        }
        return parser;
    }

    @Override
    public void replace(String tplName, String version) {
        messageParserManager.refresh(tplName, version);
    }
}
