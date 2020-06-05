package org.github.txq.spider;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.github.txq.spider.jtbc.TemplateSource;
import org.github.txq.spider.log.LogDebugger;
import org.github.txq.spider.registry.ImmutableRegister;
import org.github.txq.spider.registry.MutableRegistry;
import org.github.txq.spider.registry.Register;
import org.github.txq.spider.type.TypeHandler;

import java.util.Objects;

/**
 * @author tangxinqi
 * @date 2019/11/13 15:42
 */
@Slf4j
public class PoolingMessageParserManager implements MessageParserManager {

    private final Register<MessageParser> messageParserRegister = new MutableRegistry<>(true);

    private final Register<TypeHandler> typeHandlerRegister;

    private final TemplateSource templateSource;


    public PoolingMessageParserManager(Register<TypeHandler> typeHandlerRegister, TemplateSource source) {
        Objects.requireNonNull(source, "TemplateSource can not be null!");
        this.typeHandlerRegister = ObjectUtils.defaultIfNull(typeHandlerRegister, ImmutableRegister.emptyRegister());
        this.templateSource = source;
    }

    @Override
    public MessageParser request(String tplName) {
        return messageParserRegister.lookup(tplName);
    }

    @Override
    public void refresh(String tplName, String version) {
        String template = templateSource.getTemplate(tplName, version);
        if (LogDebugger.isEnableDebug()) {
            log.info("begin to read template[{}], version is {}", tplName, version);
        }
        MessageParser messageParser = MessageParserBuilder.custom().setTypeHandlerRegister(typeHandlerRegister).build(template);
        messageParserRegister.register(tplName, messageParser);
    }
}
